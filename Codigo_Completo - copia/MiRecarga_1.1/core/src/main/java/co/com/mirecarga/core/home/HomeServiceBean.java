package co.com.mirecarga.core.home;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.login.DatosIniciarSesion;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;

/**
 * Servicio de manejo de sesión.
 */
@Singleton
public class HomeServiceBean implements HomeService {
    /**
     * El tag para el log.
     */
    private static final String TAG = "HomeService";

    /**
     * Cache con los datos de la respuesta.
     */
    private transient RespuestaInicio resp;

    /**
     * El servicio para leer el archivo de configuración del menú.
     */
    private final transient ConfigMenu configMenu;

    /**
     * El proveedor del token.
     */
    private final transient TokenService tokenService;

    /**
     * Constructor con las propiedades.
     * @param configMenuService el servicio para leer el archivo de configuración del menú
     * @param tokenService el proveedor del token
     */
    @Inject
    public HomeServiceBean(final ConfigService<ConfigMenu> configMenuService,
                           final TokenService tokenService) {
        this.configMenu = configMenuService.getConfig();
        this.tokenService = tokenService;
        resp = new RespuestaInicio();
    }

    @Override
    public final RespuestaInicio consultarModelo() {
        return resp;
    }

    @Override
    public final void iniciarSesion(final DatosIniciarSesion datos) {
        AppLog.debug(TAG, "Calculando menú");
        resp = new RespuestaInicio();
        resp.setIdUsuario(datos.getIdUsuario());
        resp.setLogin(datos.getLogin());
        resp.setNombreCompleto(datos.getNombreCompleto());
        resp.setCorreoElectronico(datos.getCorreoElectronico());
        resp.setProveedorAutenticacion(datos.getProveedorAutenticacion());
        resp.setAutenticado(true);
        resp.setMenu(configMenu.getMenu());
        resp.indexar();
    }

    @Override
    public final void cerrarSesion() {
        tokenService.cerrarSesion();

        if (resp != null) {
            resp.setAutenticado(false);
        }
    }

    @Override
    public final boolean isSesionActiva() {
        return tokenService.isSesionActiva();
    }
}
