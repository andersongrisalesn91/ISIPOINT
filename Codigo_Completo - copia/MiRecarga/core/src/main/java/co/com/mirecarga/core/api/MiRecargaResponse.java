package co.com.mirecarga.core.api;

/**
 * Datos comunes de las respuestas de operación.
 */
public class MiRecargaResponse {
    /**
     * Flag de retorno.
     */
    private String ret;

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
}
