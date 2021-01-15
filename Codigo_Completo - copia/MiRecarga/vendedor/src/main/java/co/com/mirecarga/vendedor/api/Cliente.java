package co.com.mirecarga.vendedor.api;

import co.com.mirecarga.core.api.IdNombre;

/**
 * Datos del cliente.
 */
public class Cliente extends IdNombre {
    /**
     * Propiedad login del cliente.
     */
    private String login;
    /**
     * Propiedad externalLogin del cliente.
     */
    private String externalLogin;
    /**
     * Propiedad estado del cliente.
     */
    private String estado;

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

    /**
     * Regresa el campo externalLogin.
     *
     * @return el valor de externalLogin
     */
    public String getExternalLogin() {
        return externalLogin;
    }

    /**
     * Establece el valor del campo externalLogin.
     *
     * @param externalLogin el valor a establecer
     */
    public void setExternalLogin(final String externalLogin) {
        this.externalLogin = externalLogin;
    }

    /**
     * Regresa el campo estado.
     *
     * @return el valor de estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el valor del campo estado.
     *
     * @param estado el valor a establecer
     */
    public void setEstado(final String estado) {
        this.estado = estado;
    }
}
