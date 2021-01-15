package co.com.mirecarga.cliente.api;

/**
 * Datos comunes de las respuestas de operación.
 */
public class MiRecargaResponse {
    /**
     * Flag de retorno.
     */
    private String ret;
    /**
     * El mensaje de error.
     */
    private String error;

    /**
     * Regresa el campo ret.
     *
     * @return el valor de ret
     */
    public String getRet() {
        return ret;
    }

    /**
     * Establece el valor del campo ret.
     *
     * @param ret el valor a establecer
     */
    public void setRet(final String ret) {
        this.ret = ret;
    }

    /**
     * Regresa el campo error.
     *
     * @return el valor de error
     */
    public String getError() {
        return error;
    }

    /**
     * Establece el valor del campo error.
     *
     * @param error el valor a establecer
     */
    public void setError(final String error) {
        this.error = error;
    }
}
