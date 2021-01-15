package co.com.mirecarga.cliente.login;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiBaseClienteService;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.api.MiRecargaTokenResponse;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.NavegadorListener;
import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.login.DatosIniciarSesion;
import co.com.mirecarga.core.login.DatosIniciarSesionExterno;
import co.com.mirecarga.core.login.LoginExternoService;
import co.com.mirecarga.core.login.ProveedorAutenticacion;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.AppException;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import okhttp3.Credentials;

/**
 * Fragmento con la funcionalidad de Login..
 */
public class LoginClienteFragment extends AbstractAppFragment implements LoginExternoService {
    /**
     * El tag para el log.
     */
    protected static final String TAG = "LoginCliente";

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
     * El proveedor del token para el cliente.
     */
    @Inject
    transient TokenService tokenService;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiClienteService api;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiBaseClienteService tokenApi;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_login_cliente;
    }

    @Override
    protected final void consultarModelo() {
        setTitulo(R.string.titulo_principal);
    }

    /**
     * Obtiene el token de la aplicación.
     * @param datos los datos del usuario
     * @param proveedor el proveedor de autenticación que se usó
     */
    protected final void iniciarSesion(@NonNull final SocialUser datos, final ProveedorAutenticacion proveedor) {
        final DatosIniciarSesionExterno datosExterno = new DatosIniciarSesionExterno();
        datosExterno.setNombreCompleto(datos.fullName);
        datosExterno.setCorreoElectronico(datos.email);
        datosExterno.setProveedorAutenticacion(proveedor);
        final FragmentActivity activity = getActivity();
        if (activity == null) {
            AppLog.warn(TAG, "activity es null");
            iniciarSesion(datosExterno);
        } else {
            activity.runOnUiThread(() -> iniciarSesion(datosExterno));
        }
    }

    @Override
    public final void iniciarSesion(@NonNull final DatosIniciarSesionExterno datosExterno) {
        final ConfigWso2 configWso2 = configService.getConfig().getWso2();
        final String authorization = Credentials.basic(configWso2.getClientId(), configWso2.getClientSecret());
        subscribe(tokenApi.token(authorization, "client_credentials"),
                resp -> recibirToken(datosExterno, resp));
        finalizarProcesandoMainThread();
    }

    /**
     * Una vez se recibe el token, solicita el id del cliente e inicia la sesión.
     * @param datos los datos recibidos del autenticador externo
     * @param tokenResponse la respuesta con la generación de token
     */
    private void recibirToken(@NonNull final DatosIniciarSesionExterno datos,
                              final MiRecargaTokenResponse tokenResponse) {
        tokenService.setAccessToken(tokenResponse.getAccessToken());
        final String email;
        try {
            email = URLEncoder.encode(datos.getCorreoElectronico(), "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new AppException(e, "No fue posible codificar %s", datos.getCorreoElectronico());
        }
        subscribe(api.registrarCliente(email, datos.getNombreCompleto()), resp -> {
            final HomeService homeService = getHomeService();
            final NavegadorListener navegador = getNavegador();
            final DatosIniciarSesion datos2 = new DatosIniciarSesion();
            datos2.setLogin(datos.getCorreoElectronico());
            datos2.setIdUsuario(resp.getId());
            datos2.setNombreCompleto(datos.getNombreCompleto());
            datos2.setCorreoElectronico(datos.getCorreoElectronico());
            datos2.setProveedorAutenticacion(datos.getProveedorAutenticacion());
            homeService.iniciarSesion(datos2);
            navegador.actualizarModelo();
        });
    }

    /**
     * Procesa el click del botón.
     */
    @OnClick(R.id.iniciar_sesion_gmail)
    public final void connectGoogle() {
        mostrarProcesando();
        final List<String> scopes = Arrays.asList("profile", "email");
        executor.submit(() ->
                com.jaychang.sa.google.SimpleAuth.connectGoogle(scopes,
                        new SimpleAuthCallback(ProveedorAutenticacion.Google)));
    }

    /**
     * Procesa el click del botón.
     */
    @OnClick(R.id.iniciar_sesion_facebook)
    public final void connectFacebook() {
        mostrarProcesando();
        final List<String> scopes = Arrays.asList("public_profile", "email");
        executor.submit(() ->
                com.jaychang.sa.facebook.SimpleAuth.connectFacebook(scopes,
                        new SimpleAuthCallback(ProveedorAutenticacion.Facebook)));
    }

    /**
     * Procesa el click del botón.
     */
    @OnClick(R.id.iniciar_sesion_twitter)
    public final void connectTwitter() {
        mostrarProcesando();
        executor.submit(() ->
                com.jaychang.sa.twitter.SimpleAuth.connectTwitter(
                    new SimpleAuthCallback(ProveedorAutenticacion.Twitter)));
    }

    /**
     * Implementa los callback de autenticación.
     */
    private class SimpleAuthCallback implements AuthCallback {
        /**
         * El proveedor que está autenticando.
         */
        private final transient ProveedorAutenticacion proveedor;

        /**
         * Constructor con las propiedades.
         * @param proveedor el proveedor que está autenticando
         */
        SimpleAuthCallback(final ProveedorAutenticacion proveedor) {
            this.proveedor = proveedor;
        }

        @Override
        public void onSuccess(final SocialUser socialUser) {
            iniciarSesion(socialUser, proveedor);
        }

        @Override
        public void onError(final Throwable throwable) {
            finalizarProcesandoMainThread();
            AppLog.error(TAG, throwable, "Error al autenticar con %s", proveedor);
            mostrarMensaje("No fue posible autenticar, intente nuevamente");
        }

        @Override
        public void onCancel() {
            finalizarProcesandoMainThread();
            mostrarMensaje("Operación cancelada por el usuario");
        }
    }
}
