package co.com.mirecarga.cliente.mapa;

import net.openid.appauth.AuthState;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.core.login.AuthStateManager;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.AppException;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.mapa.ConfigMapa;

/**
 * Componente que proporciona los datos del token desde el mecanismo del ZER.
 */
@Singleton
public class TokenZERServiceBean  implements TokenService {
    /**
     * Servicio de configuración.
     */
    private final transient ConfigMapa configMapa;
    /**
     * El estado de la autenticación.
     */
    private final transient AuthStateManager authStateManager;

    /**
     * Constructor con las propiedades.
     * @param authStateManager el estado de la autenticación
     * @param  configService el Servicio de configuración
     */
    @Inject
    public TokenZERServiceBean(final AuthStateManager authStateManager, final ConfigService<ConfigCliente> configService) {
        super();
        this.authStateManager = authStateManager;
        this.configMapa = configService.getConfig().getMapa();
    }

    @Override
    public final String getAccessToken() {
        return configMapa.getTokenTemporalZER();
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
        return true;
    }
}
