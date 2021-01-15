package co.com.mirecarga.vendedor.api;

import java.util.List;

/**
 * Datos de la venta.
 */
public class VentaTarjetaPrepagoResponse {
    /**
     * El id de la venta.
     */
    private int id;
    /**
     * El  nombre del cliente.
     */
    private String cliente;
    /**
     * El valor de la transacción.
     */
    private int valortotal;
    /**
     * El valor de la transacción.
     */
    private int nuevosaldo;
    /**
     * Detalles de la respuesta.
     */
    private List<VentaTarjetaPrepagoDetalle> detalles;

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
     * Regresa el campo cliente.
     *
     * @return el valor de cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Establece el valor del campo cliente.
     *
     * @param cliente el valor a establecer
     */
    public void setCliente(final String cliente) {
        this.cliente = cliente;
    }

    /**
     * Regresa el campo valortotal.
     *
     * @return el valor de valortotal
     */
    public int getValortotal() {
        return valortotal;
    }

    /**
     * Establece el valor del campo valortotal.
     *
     * @param valortotal el valor a establecer
     */
    public void setValortotal(final int valortotal) {
        this.valortotal = valortotal;
    }

    /**
     * Regresa el campo nuevosaldo.
     *
     * @return el valor de nuevosaldo
     */
    public int getNuevosaldo() {
        return nuevosaldo;
    }

    /**
     * Establece el valor del campo nuevosaldo.
     *
     * @param nuevosaldo el valor a establecer
     */
    public void setNuevosaldo(final int nuevosaldo) {
        this.nuevosaldo = nuevosaldo;
    }

    /**
     * Regresa el campo detalles.
     *
     * @return el valor de detalles
     */
    public List<VentaTarjetaPrepagoDetalle> getDetalles() {
        return detalles;
    }

    /**
     * Establece el valor del campo detalles.
     *
     * @param detalles el valor a establecer
     */
    public void setDetalles(final List<VentaTarjetaPrepagoDetalle> detalles) {
        this.detalles = detalles;
    }
}
