package co.com.mirecarga.core.api;

import java.util.Date;

/**
 * Datos del detalle de la respuesta de la venta.
 */
public class TransaccionCeldaDetalle {
    /**
     * Id del item.
     */
    private int id;

    /**
     * Id de la tarifa.
     */
    private int idtarifa;

    /**
     * Valor de costo total.
     */
    private double costoxminuto;

    /**
     * Valor de costo total.
     */
    private double costototal;

    /**
     * La fecha y hora de entrada.
     */
    private Date fechahoraentrada;
    /**
     * La fecha y hora de entrada.
     */
    private Date fechahorasalida;

    /**
     * Indica si el pago fue completo.
     */
    private boolean pagado;

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
     * Regresa el campo idtarifa.
     *
     * @return el valor de idtarifa
     */
    public int getIdtarifa() {
        return idtarifa;
    }

    /**
     * Establece el valor del campo idtarifa.
     *
     * @param idtarifa el valor a establecer
     */
    public void setIdtarifa(final int idtarifa) {
        this.idtarifa = idtarifa;
    }

    /**
     * Regresa el campo costoxminuto.
     *
     * @return el valor de costoxminuto
     */
    public double getCostoxminuto() {
        return costoxminuto;
    }

    /**
     * Establece el valor del campo costoxminuto.
     *
     * @param costoxminuto el valor a establecer
     */
    public void setCostoxminuto(final double costoxminuto) {
        this.costoxminuto = costoxminuto;
    }

    /**
     * Regresa el campo costototal.
     *
     * @return el valor de costototal
     */
    public double getCostototal() {
        return costototal;
    }

    /**
     * Establece el valor del campo costototal.
     *
     * @param costototal el valor a establecer
     */
    public void setCostototal(final double costototal) {
        this.costototal = costototal;
    }

    /**
     * Regresa el campo fechahoraentrada.
     *
     * @return el valor de fechahoraentrada
     */
    public Date getFechahoraentrada() {
        return fechahoraentrada;
    }

    /**
     * Establece el valor del campo fechahoraentrada.
     *
     * @param fechahoraentrada el valor a establecer
     */
    public void setFechahoraentrada(final Date fechahoraentrada) {
        this.fechahoraentrada = fechahoraentrada;
    }

    /**
     * Regresa el campo fechahorasalida.
     *
     * @return el valor de fechahorasalida
     */
    public Date getFechahorasalida() {
        return fechahorasalida;
    }

    /**
     * Establece el valor del campo fechahorasalida.
     *
     * @param fechahorasalida el valor a establecer
     */
    public void setFechahorasalida(final Date fechahorasalida) {
        this.fechahorasalida = fechahorasalida;
    }

    /**
     * Regresa el campo pagado.
     *
     * @return el valor de pagado
     */
    public boolean isPagado() {
        return pagado;
    }

    /**
     * Establece el valor del campo pagado.
     *
     * @param pagado el valor a establecer
     */
    public void setPagado(final boolean pagado) {
        this.pagado = pagado;
    }
}
