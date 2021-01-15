package co.com.mirecarga.cliente.recarga;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import androidx.annotation.WorkerThread;
import androidx.browser.customtabs.CustomTabsIntent;
import android.widget.TextView;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.browser.AnyBrowserMatcher;
import net.openid.appauth.browser.BrowserDescriptor;
import net.openid.appauth.connectivity.DefaultConnectionBuilder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.api.ApiZERService;
import co.com.mirecarga.cliente.api.RespuestaGenerarPin;
import co.com.mirecarga.cliente.app.ClienteRouterActivity;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.cliente.app.InternetHelper;
import co.com.mirecarga.cliente.mapa.TokenZERServiceBean;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.login.AuthStateManager;
import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.login.ConnectionBuilderForTesting;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Fragmento con los datos de la página de recarga saldo.
 */
public class RecargaSaldoFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "RecargaSaldoFragment";
    /**
     * URL a utilizar en caso de pago correcto.
     */
    private static final String URL_OK = "co.com.mirecargacliente:/generarPinOkRedirect";
    /**
     * URL a utilizar en caso de pago incorrecto.
     */
    private static final String URL_ERROR = "co.com.mirecargacliente:/generarPinErrorRedirect";
    /**
     * Control de página.
     */
    @BindView(R.id.saldo)
    transient TextView saldo;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiClienteService api;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiZERService apiZERService;
    /**
     * El proveedor del token para el cliente.
     */
    @Inject
    transient TokenZERServiceBean tokenService;
    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigCliente> configService;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    @Inject
    transient ExecutorService executor;

    /**
     * Constante para recibir la respuesta de appauth.
     */
    private static final int RC_AUTH = 100;

    /**
     * Servicio de appauth.
     */
    private transient AuthorizationService authService;

    /**
     * El estado de la autenticación.
     */
    @Inject
    transient AuthStateManager authStateManager;

    /**
     * Manejador de customTabs de appauth.
     */
    private transient CustomTabsIntent authIntent;

    /**
     * Controla que se finalizó el cargue inicial.
     */
    private final transient CountDownLatch authIntentLatch = new CountDownLatch(1);

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_recarga_saldo;
    }

    @Override
    protected final void consultarModelo() {
        consultarSaldo();
        executor.submit(this::inicializarCustomTabs);
    }

    /**
     * Ejecuta la funcionalidad para consultar el saldo.
     */
    private void consultarSaldo() {
        if (isSesionActiva()) {
            final Integer idCliente = getIdUsuario();
            subscribe(api.consultaSaldo(idCliente,
                    configService.getConfig().getProductos().getIdPinTransito()),
                    resp -> saldo.setText(getString(R.string.tu_saldo, resp.getSaldo()))
            );
        }
    }

    /**
     * Crea el servicio de autorización.
     * @return el servicio de autorización
     */
    private AuthorizationService createAuthorizationService() {
        AppLog.debug(TAG, "Creando authorization service");
        final AppAuthConfiguration.Builder builder = new AppAuthConfiguration.Builder();
        builder.setBrowserMatcher(AnyBrowserMatcher.INSTANCE);
        final ConfigWso2 configWso2 = configService.getConfig().getWso2();
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
        authIntent = null;
    }

    /**
     * Inicializa la librería de appauth.
     */
    @WorkerThread
    private void inicializarCustomTabs() {
        AppLog.debug(TAG, "Inicializando CustomTabs");
        recreateAuthorizationService();
        warmUpBrowser();
    }

    /**
     * Inicializa los customTabs para mostrar las URL a procesar.
     */
    private void warmUpBrowser() {
        AppLog.debug(TAG, "Warming up browser instance for auth request");
        final CustomTabsIntent.Builder intentBuilder =
                authService.createCustomTabsIntentBuilder(Uri.parse(URL_OK), Uri.parse(URL_ERROR));
        intentBuilder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        authIntent = intentBuilder.build();
        authIntentLatch.countDown();
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.recargar)
    public void recargar() {
        if (isSesionActiva()) {
            final Integer idCliente = getIdUsuario();
            // TODO confirmar manejo desde servicio o pasar a config
            final String accessToken = "43b87584-06eb-38d0-b12f-c41760668fdd";
            final String ip = InternetHelper.getIPAddress();
            subscribe(apiZERService.generarPin(idCliente, accessToken, ip,
                    URL_OK, URL_ERROR),
                    this::iniciarPago);
        }
    }

    /**
     * Crea el Intent apuntando a la URL y utilizando customtabs si es posible.
     * @param resp la respuesta del servicio de pago
     * @return el intent a usar para abrir CustomTabs
     */
    private Intent prepareAuthorizationRequestIntent(final RespuestaGenerarPin resp) {
        final BrowserDescriptor browser = authService.getBrowserDescriptor();

        if (browser == null) {
            throw new ActivityNotFoundException();
        }

        final Uri requestUri = Uri.parse(resp.getRedirecturl()).buildUpon()
                .appendQueryParameter("pin", resp.getCodigo())
                .build();
        final Intent intent;
        if (browser.useCustomTab) {
            intent = authIntent.intent;
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
        }
        intent.setPackage(browser.packageName);
        intent.setData(requestUri);
        final Bundle headers = new Bundle();
        headers.putString("Authorization", "Bearer " + tokenService.getAccessToken());
        intent.putExtra(Browser.EXTRA_HEADERS, headers);
        AppLog.debug(TAG, "Using %s as browser for pago, custom tab = %s URL %s headers %s",
                intent.getPackage(), browser.useCustomTab.toString(), requestUri, headers);
        return intent;
    }


    /**
     * Procesa el pago en un hilo aparte.
     * @param resp la respuesta del servicio de pago
     */
    @WorkerThread
    private void iniciarPagoInterno(final RespuestaGenerarPin resp) {
        try {
            authIntentLatch.await();
        } catch (final InterruptedException ex) {
            AppLog.error(TAG, ex, "Interrupted while waiting for auth intent");
        }
        final Intent authIntent2 = prepareAuthorizationRequestIntent(resp);
        final Intent intent = new Intent(getContext(), ClienteRouterActivity.class);
        intent.putExtra(ClienteRouterActivity.EXTRA_CUSTOM_TABS_INTENT, authIntent2);
        startActivityForResult(intent, RC_AUTH);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        AppLog.debug(TAG, "onActivityResult %s %s %s", requestCode, resultCode, intent);
        finalizarProcesandoMainThread();
        if (requestCode == RC_AUTH) {
            if (resultCode == RESULT_CANCELED) {
                mostrarMensaje(getString(R.string.operacion_cancelada));
            } else if (intent == null || intent.getData() == null) {
                AppLog.warn(TAG, "No se recibieron datos de la URL: %s", intent);
                mostrarMensaje(getString(R.string.operacion_cancelada));
            } else {
                final String url = intent.getData().toString();
                if (url.startsWith(URL_OK)) {
                    consultarSaldo();
                    mostrarMensaje("El proceso de recarga fue exitoso");
                } else if (url.startsWith(URL_ERROR)) {
                    mostrarMensaje("Ocurrió un error en el proceso de recarga");
                } else {
                    mostrarMensaje("Respuesta errónea en el proceso de recarga: %s", url);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (authService != null) {
            authService.dispose();
        }
    }

    /**
     * Inicia el pago en un hilo aparte.
     * @param resp la respuesta para iniciar el pago
     */
    private void iniciarPago(final RespuestaGenerarPin resp) {
        mostrarMensaje("Se obtuvo respuesta %s %s -> %s",
                resp.getId(), resp.getCodigo(), resp.getRedirecturl());
        executor.submit(() -> iniciarPagoInterno(resp));
    }
}
