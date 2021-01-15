package co.com.mirecarga.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import co.com.mirecarga.core.BuildConfig;
import co.com.mirecarga.core.api.ProductoResponse;
import co.com.mirecarga.core.api.ProductoResponseTypeAdapter;
import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.login.TokenService;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Utilidades para la generación del servicio con Retrofit.
 */
public final class ServiceGenerator {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ServiceGenerator";

    /**
     * Constante recomendada.
     */
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";

    /**
     * Constante recomendada.
     */
    private static final String HEADER_PRAGMA = "Pragma";

    /**
     * Constructor privado para evitar generar instancias.
     */
    private ServiceGenerator() {
        super();
    }

    /**
     * Crea el proveedor de retrofit para acceso al API.
     * @param context el contexto de la aplicación
     * @param baseUrl la URL base del servicio
     * @param wso2 las configuraciones de conexión a WSO2
     * @param tokenProvider el almacén del token oauth2
     * @return el proveedor de retrofit
     */
    public static Retrofit getRetrofit(final Context context, final String baseUrl,
                                       final ConfigWso2 wso2, final TokenService tokenProvider) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ProductoResponse.class, new ProductoResponseTypeAdapter());
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        final Gson gson = gsonBuilder.create();
        final OkHttpClient client = getClient(context, wso2, tokenProvider);
        assert baseUrl != null;
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Genera el cliente http con token oauth2, cache, logging y manejo de https de pruebas.
     * @param context el contexto de la aplicación
     * @param wso2 las configuraciones de conexión a WSO2
     * @param tokenProvider el almacén del token oauth2
     * @return el cliente generado
     */
    private static OkHttpClient getClient(final Context context, final ConfigWso2 wso2, final TokenService tokenProvider) {
        final OkHttpClient.Builder client;
        if (wso2.isHttpsRequired()) {
            client = new OkHttpClient.Builder();
        } else {
            client = getUnsafeOkHttpClientBuilder();
        }
        client.addInterceptor(provideOfflineCacheInterceptor(context, wso2))
                .addNetworkInterceptor(provideCacheInterceptor(context, wso2));
        if (tokenProvider != null) {
            // Enviar Token OAuth2
            client.addInterceptor(chain -> {
                final Request original = chain.request();

                final String accessToken = tokenProvider.getAccessToken();

                final Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-type", "application/json")
                        .header("Authorization", "Bearer " + accessToken)
                        .method(original.method(), original.body());

                final Request request = requestBuilder.build();
                return chain.proceed(request);
            });
        }

        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(Level.BODY);
            if (!client.interceptors().contains(logging)) {
                client.addInterceptor(logging);
            }
        }

        final Cache cache = new Cache(new File(context.getCacheDir(), "http-cache"), wso2.getMaxCacheSize());
        client.cache(cache);
        AppLog.debug(TAG, "Cliente con caché '%s' en línea %s seg fuera de línea %s horas",
                context.getCacheDir(),
                wso2.getSegundosCacheEnLinea(),
                wso2.getHorasCacheFueraDeLinea());
        return client.build();
    }

    /**
     * Genera un manejador de conexiones para ambiente de pruebas en que el certificado https no es correcto.
     * @return el manejador de conexiones
     */
    private static OkHttpClient.Builder getUnsafeOkHttpClientBuilder() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                            // Confía en todos
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
                            // Confía en todos
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new AppException(e, "No se pudo construir el UnsafeOkHttpClient");
        }
    }

    /**
     * Indica si se encuentra conectado a internet.
     * @param context el contexto de la app
     * @return true si está conectado
     */
    public static boolean isConnected(final Context context) {
        boolean ok = false;
        try {
            ConnectivityManager e = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            ok = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception ex) {
            AppLog.error(TAG, ex, "Error inesperado al verificar conexión");
        }
        return ok;
    }

    /**
     * Crea el interceptor de caché en línea.
     * @param context el contexto de la aplicación
     * @param wso2 las configuraciones de conexión a WSO2
     * @return el interceptor de caché
     */
    private static Interceptor provideCacheInterceptor(final Context context, final ConfigWso2 wso2) {
        return chain -> {
            Response response = chain.proceed(chain.request());

            CacheControl cacheControl;

            if (isConnected(context)) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(wso2.getSegundosCacheEnLinea(), TimeUnit.SECONDS)
                        .build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(wso2.getHorasCacheFueraDeLinea(), TimeUnit.HOURS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    /**
     * Crea el interceptor de fuera en línea.
     * @param context el contexto de la aplicación
     * @param wso2 las configuraciones de conexión a WSO2
     * @return el interceptor de caché
     */
    private static Interceptor provideOfflineCacheInterceptor(final Context context, final ConfigWso2 wso2) {
        return chain -> {
            Request request = chain.request();

            if (!isConnected(context)) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(wso2.getHorasCacheFueraDeLinea(), TimeUnit.HOURS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

}
