package co.com.mirecarga.cliente.login;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.login.TokenService;

/**
 * Componente que proporciona los datos del token desde el mecanismo del cliente.
 */
@Singleton
public class TokenClienteServiceBean implements TokenService {
    /**
     * Datos del token para acceder al API.
     */
    private String accessToken;

    /**
     * Constructor vacío.
     */
    @Inject
    public TokenClienteServiceBean() {
        super();
    }

    @Override
    public final String getAccessToken() {
        return accessToken;
    }

    @Override
    public final void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public final void cerrarSesion() {
        setAccessToken(null);
    }

    @Override
    public final boolean isSesionActiva() {
        return true;
    }
}
