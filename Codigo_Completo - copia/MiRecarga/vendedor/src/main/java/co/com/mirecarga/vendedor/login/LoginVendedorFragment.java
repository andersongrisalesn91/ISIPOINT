package co.com.mirecarga.vendedor.login;


import android.content.Intent;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.browser.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;
import net.openid.appauth.browser.AnyBrowserMatcher;
import net.openid.appauth.connectivity.DefaultConnectionBuilder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.OnClick;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.NavegadorListener;
import co.com.mirecarga.core.login.AuthStateManager;
import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.login.ConnectionBuilderForTesting;
import co.com.mirecarga.core.login.DatosIniciarSesion;
import co.com.mirecarga.core.login.IdTokenWso2;
import co.com.mirecarga.core.login.ProveedorAutenticacion;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Fragmento con la funcionalidad de Login..
 */
public class LoginVendedorFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "LoginVendedor";

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    @Inject
    transient ExecutorService executor;

    /**
     * Los datos de conexión.
     */
    private transient ConfigWso2 configWso2;

    /**
     * Constante para recibir la respuesta de appauth.
     */
    private static final int RC_AUTH = 100;

    /**
     * Servicio de appauth.
     */
    private transient AuthorizationService authService;

    /**
     * Solicitud de autorización de appauth.
     */
    private transient AuthorizationRequest authRequest;

    /**
     * El estado de la autenticación.
     */
    @Inject
    transient AuthStateManager authStateManager;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiVendedorService api;

    /**
     * Manejador de customTabs de appauth.
     */
    private transient CustomTabsIntent authIntent;

    /**
     * Controla que se finalizó el cargue inicial.
     */
    private final transient CountDownLatch authIntentLatch = new CountDownLatch(1);

    /**
     * El proveedor del token para el cliente.
     */
    @Inject
    transient TokenService tokenProvider;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_login_vendedor;
    }

    @Override
    protected final void consultarModelo() {
        setTitulo(R.string.titulo_principal);
        executor.submit(this::initializeAppAuth);
    }

    /**
     * Crea el servicio de autorización.
     * @return el servicio de autorización
     */
    private AuthorizationService createAuthorizationService() {
        AppLog.debug(TAG, "Creando authorization service");
        final AppAuthConfiguration.Builder builder = new AppAuthConfiguration.Builder();
        builder.setBrowserMatcher(AnyBrowserMatcher.INSTANCE);
        if (configWso2.isHttpsRequired()) {
            builder.setConnectionBuilder(DefaultConnectionBuilder.INSTANCE);
        } else {
            builder.setConnectionBuilder(ConnectionBuilderForTesting.INSTANCE);
        }
        return new AuthorizationService(this.getContext(), builder.build());
    }

    /**
     * Inicializa el servicio de autorización asegurándose de liberar la instancia anterior.
     */
    private void recreateAuthorizationService() {
        if (authService != null) {
            AppLog.debug(TAG, "Descarta la instancia de authService");
            authService.dispose();
        }
        authService = createAuthorizationService();
        authRequest = null;
        authIntent = null;
    }

    /**
     * Inicializa la librería de appauth.
     */
    @WorkerThread
    private void initializeAppAuth() {
        AppLog.debug(TAG, "Inicializando AppAuth");
        configWso2 = configService.getConfig().getWso2();
        recreateAuthorizationService();

        AppLog.debug(TAG, "Creando auth config");
        final AuthorizationServiceConfiguration authConfig = new AuthorizationServiceConfiguration(
                configWso2.getAuthorizationEndpointUri(),
                configWso2.getTokenEndpointUri(),
                null);
        authStateManager.replace(new AuthState(authConfig));

        AppLog.debug(TAG, "Conectando WSO2 %s %s", configWso2.getRedirectUri(), configWso2.getClientId());

        // initializeClient();
        authRequest = new AuthorizationRequest.Builder(
                authConfig,
                configWso2.getClientId(),
                ResponseTypeValues.CODE,
                configWso2.getRedirectUri())
                .setScope(configWso2.getAuthorizationScope())
                .build();

        warmUpBrowser();
    }

    /**
     * Inicializa los customTabs para mostrar el login.
     */
    private void warmUpBrowser() {
        AppLog.debug(TAG, "Warming up browser instance for auth request");
        final CustomTabsIntent.Builder intentBuilder =
                authService.createCustomTabsIntentBuilder(authRequest.toUri());
        intentBuilder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        authIntent = intentBuilder.build();
        authIntentLatch.countDown();
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.iniciar_sesion)
    public void iniciarSesion() {
        mostrarProcesando();
        executor.submit(this::doAuth);
    }

    /**
     * Performs the authorization request, using the browser selected in the spinner,
     * and a user-provided `login_hint` if available.
     */
    @WorkerThread
    private void doAuth() {
        try {
            authIntentLatch.await();
        } catch (final InterruptedException ex) {
            AppLog.error(TAG, ex, "Interrupted while waiting for auth intent");
        }
        final Intent intent = authService.getAuthorizationRequestIntent(
                authRequest,
                authIntent);
        startActivityForResult(intent, RC_AUTH);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLog.debug(TAG, "onActivityResult %s %s %s", requestCode, resultCode, data);
        finalizarProcesandoMainThread();
        if (requestCode == RC_AUTH) {
            if (resultCode == RESULT_CANCELED) {
                mostrarMensaje(getString(R.string.operacion_cancelada));
            } else {
                mostrarProcesando();
                final AuthorizationResponse response = AuthorizationResponse.fromIntent(data);
                final AuthorizationException ex = AuthorizationException.fromIntent(data);

                final boolean solicitarToken = response != null && response.authorizationCode != null;
                if (solicitarToken) {
                    // authorization code exchange is required
                    authStateManager.updateAfterAuthorization(response, ex);
                    exchangeAuthorizationCode(response);
                } else if (ex == null) {
                    mostrarMensaje("No se obtuvo respuesta. Intente iniciar sesión de nuevo.");
                } else {
                    AppLog.error(TAG, ex, "Error de autenticación");
                    mostrarMensaje("Autenticación fallida: %s", ex.getMessage());
                    authStateManager.updateAfterAuthorization(response, ex);
                }
            }
        }
    }

    /**
     * Envía la respuesta al performTokenRequest.
     * @param authorizationResponse la respuesta
     */
    @MainThread
    private void exchangeAuthorizationCode(final AuthorizationResponse authorizationResponse) {
        performTokenRequest(
                authorizationResponse.createTokenExchangeRequest(),
                this::handleCodeExchangeResponse);
    }

    /**
     * Obtiene el token de autorización.
     * @param request la solicitud original
     * @param callback el método para llamar al finalizar
     */
    @MainThread
    private void performTokenRequest(
            final TokenRequest request,
            final AuthorizationService.TokenResponseCallback callback) {
        final ClientAuthentication clientAuthentication = new ClientSecretPost(configWso2.getClientSecret());
        authService.performTokenRequest(
                request,
                clientAuthentication,
                callback);
    }

    /**
     * Procesa el resultado de la solicitud del token.
     * @param tokenResponse la respuesta del servicio
     * @param authException la excepción si ocurrió
     */
    @MainThread
    private void handleCodeExchangeResponse(
            @Nullable final TokenResponse tokenResponse,
            @Nullable final AuthorizationException authException) {

        authStateManager.updateAfterTokenResponse(tokenResponse, authException);
        if (authStateManager.getCurrent().isAuthorized()) {
            AppLog.debug(TAG, "Usuario autorizado");
            autorizado();
        } else {
            if (authException == null) {
                AppLog.error(TAG, "Authorization Code exchange failed No Exception");
            } else {
                AppLog.error(TAG, authException, "Authorization Code exchange failed: %s - %s",
                        authException.error,
                        authException.errorDescription);
            }
            mostrarMensaje(getString(R.string.ingreso_no_autorizado));
        }
    }

    /**
     * Lógica cuando el usuario fue autorizado.
     */
    @MainThread
    private void autorizado() {
        final AuthState state = authStateManager.getCurrent();
        AppLog.debug(TAG, "getRefreshToken %s", state.getRefreshToken());
        AppLog.debug(TAG, "getIdToken %s", state.getIdToken());
        AppLog.info(TAG, "getAccessToken %s", state.getAccessToken());
        AppLog.debug(TAG, "getAccessTokenExpirationTime %s", state.getAccessTokenExpirationTime());
        final AuthorizationServiceConfiguration authorizationServiceConfiguration = state.getAuthorizationServiceConfiguration();
        AppLog.debug(TAG, "getAuthorizationServiceConfiguration %s", authorizationServiceConfiguration);
        // Decodifica el idToken https://pubci.com/2016/06/19/id-token-in-openid-in-wso2-identity-server-5-1-0/
        final String idToken = state.getIdToken();
        final int posini = TextUtils.indexOf(idToken, '.');
        final int posfin = TextUtils.indexOf(idToken, '.', posini + 1);
        final String idTokenDatos = TextUtils.substring(idToken, posini + 1, posfin);
        final byte[] decode = Base64.decode(idTokenDatos, Base64.DEFAULT);
        final String data = new String(decode, StandardCharsets.UTF_8);
        AppLog.info(TAG, "data %s", data);
        final Gson gson = new Gson();
        final IdTokenWso2 idTokenWso2 = gson.fromJson(data, IdTokenWso2.class);
        final String login = idTokenWso2.getSub();
        AppLog.info(TAG, "login %s", idTokenWso2.getSub());
        final HomeService homeService = getHomeService();
        final NavegadorListener navegador = getNavegador();
        subscribe(api.getPerfil(login), resp -> {
            final DatosIniciarSesion datos = new DatosIniciarSesion();
            datos.setLogin(login);
            datos.setIdUsuario(resp.getId());
            datos.setNombreCompleto(resp.getNombre());
            datos.setCorreoElectronico(login);
            datos.setProveedorAutenticacion(ProveedorAutenticacion.MiRecarga);
            homeService.iniciarSesion(datos);
            navegador.actualizarModelo();
        });
        finalizarProcesando();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (authService != null) {
            authService.dispose();
        }
    }

}
