package co.com.mirecarga.cliente.app;

/**
 * Configuración general de los productos.
 */
public class ConfigProducto {
    /**
     * Id del producto recarga en línea.
     */
    private int idRecargaEnLinea;
    /**
     * Id del producto PIN de tránsito.
     */
    private int idPinTransito;

    /**
     * Regresa el campo idRecargaEnLinea.
     *
     * @return el valor de idRecargaEnLinea
     */
    public int getIdRecargaEnLinea() {
        return idRecargaEnLinea;
    }

    /**
     * Establece el valor del campo idRecargaEnLinea.
     *
     * @param idRecargaEnLinea el valor a establecer
     */
    public void setIdRecargaEnLinea(final int idRecargaEnLinea) {
        this.idRecargaEnLinea = idRecargaEnLinea;
    }

    /**
     * Regresa el campo idPinTransito.
     *
     * @return el valor de idPinTransito
     */
    public int getIdPinTransito() {
        return idPinTransito;
    }

    /**
     * Establece el valor del campo idPinTransito.
     *
     * @param idPinTransito el valor a establecer
     */
    public void setIdPinTransito(final int idPinTransito) {
        this.idPinTransito = idPinTransito;
    }
}
