package co.com.mirecarga.core.login;

/**
 * Datos requeridos para iniciar sesión.
 */
public class DatosIniciarSesion extends DatosIniciarSesionExterno {
    /**
     * Id del usuario actual.
     */
    private Integer idUsuario;

    /**
     * Login del usuario actual.
     */
    private String login;

    /**
     * Regresa el campo idUsuario.
     *
     * @return el valor de idUsuario
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el valor del campo idUsuario.
     *
     * @param idUsuario el valor a establecer
     */
    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Regresa el campo login.
     *
     * @return el valor de login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Establece el valor del campo login.
     *
     * @param login el valor a establecer
     */
    public void setLogin(final String login) {
        this.login = login;
    }
}
