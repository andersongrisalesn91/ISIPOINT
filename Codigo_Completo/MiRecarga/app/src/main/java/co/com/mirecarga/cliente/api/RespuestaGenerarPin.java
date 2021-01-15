package co.com.mirecarga.cliente.api;

/**
 * La respuesta del método generarpin para el pago.
 */
public class RespuestaGenerarPin {
    /**
     * Id del item.
     */
    private int id;
    /**
     * Código del PIN.
     */
    private String codigo;
    /**
     * URL para redirigir.
     */
    private String redirecturl;

    /**
     * Regresa el campo id.
     *
     * @return el valor de id
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el valor del campo id.
     *
     * @param id el valor a establecer
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Regresa el campo codigo.
     *
     * @return el valor de codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el valor del campo codigo.
     *
     * @param codigo el valor a establecer
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * Regresa el campo redirecturl.
     *
     * @return el valor de redirecturl
     */
    public String getRedirecturl() {
        return redirecturl;
    }

    /**
     * Establece el valor del campo redirecturl.
     *
     * @param redirecturl el valor a establecer
     */
    public void setRedirecturl(final String redirecturl) {
        this.redirecturl = redirecturl;
    }
}
