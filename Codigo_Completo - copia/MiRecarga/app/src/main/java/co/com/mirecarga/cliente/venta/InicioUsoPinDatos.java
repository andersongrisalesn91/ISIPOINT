package co.com.mirecarga.cliente.venta;

/**
 * Datos para la funcionalidad de inicio uso de pin.
 */
public class InicioUsoPinDatos {
    /**
     * El identificador del método de pago.
     */
    private Integer idMetodoPago;
    /**
     * El código del método de pago.
     */
    private String codigoMetodoPago;
    /**
     * El identificador de la zona.
     */
    private Integer idCelda;
    /**
     * La descripción la zona.
     */
    private String descripcionCelda;
    /**
     * El saldo actual disponible para recargas.
     */
    private String saldo;

    /**
     * Regresa el campo idMetodoPago.
     *
     * @return el valor de idMetodoPago
     */
    public Integer getIdMetodoPago() {
        return idMetodoPago;
    }

    /**
     * Establece el valor del campo idMetodoPago.
     *
     * @param idMetodoPago el valor a establecer
     */
    public void setIdMetodoPago(final Integer idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    /**
     * Regresa el campo codigoMetodoPago.
     *
     * @return el valor de codigoMetodoPago
     */
    public String getCodigoMetodoPago() {
        return codigoMetodoPago;
    }

    /**
     * Establece el valor del campo codigoMetodoPago.
     *
     * @param codigoMetodoPago el valor a establecer
     */
    public void setCodigoMetodoPago(final String codigoMetodoPago) {
        this.codigoMetodoPago = codigoMetodoPago;
    }

    /**
     * Regresa el campo idCelda.
     *
     * @return el valor de idCelda
     */
    public Integer getIdCelda() {
        return idCelda;
    }

    /**
     * Establece el valor del campo idCelda.
     *
     * @param idCelda el valor a establecer
     */
    public void setIdCelda(final Integer idCelda) {
        this.idCelda = idCelda;
    }

    /**
     * Regresa el campo descripcionCelda.
     *
     * @return el valor de descripcionCelda
     */
    public String getDescripcionCelda() {
        return descripcionCelda;
    }

    /**
     * Establece el valor del campo descripcionCelda.
     *
     * @param descripcionCelda el valor a establecer
     */
    public void setDescripcionCelda(final String descripcionCelda) {
        this.descripcionCelda = descripcionCelda;
    }

    /**
     * Regresa el campo saldo.
     *
     * @return el valor de saldo
     */
    public String getSaldo() {
        return saldo;
    }

    /**
     * Establece el valor del campo saldo.
     *
     * @param saldo el valor a establecer
     */
    public void setSaldo(final String saldo) {
        this.saldo = saldo;
    }
}
