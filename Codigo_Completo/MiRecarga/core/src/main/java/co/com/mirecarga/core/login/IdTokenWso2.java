package co.com.mirecarga.core.login;

/**
 * Datos contenidos dentro del idToken del WSO2.
 */
public class IdTokenWso2 {
    /**
     * Login del usuario.
     */
    private String sub;

    /**
     * Regresa el campo sub.
     *
     * @return el valor de sub
     */
    public String getSub() {
        return sub;
    }

    /**
     * Establece el valor del campo sub.
     *
     * @param sub el valor a establecer
     */
    public void setSub(final String sub) {
        this.sub = sub;
    }
}
