package co.com.mirecarga.vendedor.login;

import net.openid.appauth.AuthState;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.login.AuthStateManager;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.AppException;

/**
 * Componente que proporciona los datos del token desde el mecanismo del vendedor.
 */
@Singleton
public class TokenVendedorServiceBean implements TokenService {

    /**
     * El estado de la autenticación.
     */
    private final transient AuthStateManager authStateManager;

    /**
     * Constructor con las propiedades.
     * @param authStateManager el estado de la autenticación
     */
    @Inject
    public TokenVendedorServiceBean(final AuthStateManager authStateManager) {
        super();
        this.authStateManager = authStateManager;
    }

    @Override
    public final String getAccessToken() {
        return authStateManager.getCurrent().getAccessToken();
    }

    @Override
    public final void setAccessToken(final String accessToken) {
        throw new AppException("Operación no soportada");
    }

    @Override
    public final void cerrarSesion() {
        authStateManager.replace(new AuthState());
    }

    @Override
    public final boolean isSesionActiva() {
        final AuthState current = authStateManager.getCurrent();
        // TODO tendría que contar con criterio para renovar token automático hasta cierta holgura de tiempo
        return current.isAuthorized() && !current.getNeedsTokenRefresh();
    }


}
