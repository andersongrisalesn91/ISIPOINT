package co.com.mirecarga.core.home;

import co.com.mirecarga.core.login.DatosIniciarSesion;

/**
 * Contrato para el servicio de inicio.
 */
public interface HomeService {
    /**
     * Consulta los datos para la página de inicio.
     * @return los datos para la página de inicio
     */
    RespuestaInicio consultarModelo();

    /**
     * El usuario inició sesión.
     * @param datos los datos de inicio de sesión
     */
    void iniciarSesion(DatosIniciarSesion datos);

    /**
     * El usuario cerró sesión.
     */
    void cerrarSesion();

    /**
     * Indica si la sesión está activa con una validación de la vigencia del token.
     * @return true si el token está vigente
     */
    boolean isSesionActiva();
}
