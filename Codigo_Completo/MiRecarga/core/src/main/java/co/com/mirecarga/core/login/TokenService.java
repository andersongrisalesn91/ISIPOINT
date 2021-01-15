package co.com.mirecarga.core.login;

/**
 * Contrato para el componente que proporciona los datos del token.
 */
public interface TokenService {
    /**
     * Datos del token para acceder al API.
     * @return el token
     */
    String getAccessToken();

    /**
     * Establece el valor del campo accessToken.
     *
     * @param accessToken el valor a establecer
     */
    void setAccessToken(String accessToken);

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
