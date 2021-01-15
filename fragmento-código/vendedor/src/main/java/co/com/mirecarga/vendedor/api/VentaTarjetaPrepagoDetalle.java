package co.com.mirecarga.vendedor.api;

/**
 * Datos con el detalle de la venta.
 */
public class VentaTarjetaPrepagoDetalle {
    /**
     * El nombre del producto.
     */
    private String producto;
    /**
     * El valor unitario.
     */
    private int valorunitario;
    /**
     * El serial.
     */
    private String serial;

    /**
     * Regresa el campo producto.
     *
     * @return el valor de producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Establece el valor del campo producto.
     *
     * @param producto el valor a establecer
     */
    public void setProducto(final String producto) {
        this.producto = producto;
    }

    /**
     * Regresa el campo valorunitario.
     *
     * @return el valor de valorunitario
     */
    public int getValorunitario() {
        return valorunitario;
    }

    /**
     * Establece el valor del campo valorunitario.
     *
     * @param valorunitario el valor a establecer
     */
    public void setValorunitario(final int valorunitario) {
        this.valorunitario = valorunitario;
    }

    /**
     * Regresa el campo serial.
     *
     * @return el valor de serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Establece el valor del campo serial.
     *
     * @param serial el valor a establecer
     */
    public void setSerial(final String serial) {
        this.serial = serial;
    }
}
