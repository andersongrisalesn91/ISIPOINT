package co.com.mirecarga.vendedor.ventapaquetetransito;

import co.com.mirecarga.core.api.Paquete;
import co.com.mirecarga.core.api.Zona;
import co.com.mirecarga.core.util.IdDescripcion;
import co.com.mirecarga.vendedor.api.Cliente;

/**
 * Datos para la funcionalidad de confirmar venta paquete tránsito.
 */
public class ConfirmarVentaPaqueteTransitoDatos {

    /**
     * El saldo actual disponible para ventas.
     */
    private String saldo;

    /**
     * El cliente seleccionado.
     */
    private Cliente cliente;

    /**
     * La placa seleccionada.
     */
    private String placa;

    /**
     * El pin seleccionado.
     */
    private String pin;

    /**
     * El paquete seleccionado.
     */
    private Paquete paquete;

    /**
     * El método de pago seleccionado.
     */
    private IdDescripcion metodoPago;

    /**
     * La zona seleccionada.
     */
    private Zona zona;

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

    /**
     * Regresa el campo cliente.
     *
     * @return el valor de cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el valor del campo cliente.
     *
     * @param cliente el valor a establecer
     */
    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Regresa el campo placa.
     *
     * @return el valor de placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece el valor del campo placa.
     *
     * @param placa el valor a establecer
     */
    public void setPlaca(final String placa) {
        this.placa = placa;
    }

    /**
     * Regresa el campo pin.
     *
     * @return el valor de pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * Establece el valor del campo pin.
     *
     * @param pin el valor a establecer
     */
    public void setPin(final String pin) {
        this.pin = pin;
    }

    /**
     * Regresa el campo paquete.
     *
     * @return el valor de paquete
     */
    public Paquete getPaquete() {
        return paquete;
    }

    /**
     * Establece el valor del campo paquete.
     *
     * @param paquete el valor a establecer
     */
    public void setPaquete(final Paquete paquete) {
        this.paquete = paquete;
    }

    /**
     * Regresa el campo metodoPago.
     *
     * @return el valor de metodoPago
     */
    public IdDescripcion getMetodoPago() {
        return metodoPago;
    }

    /**
     * Establece el valor del campo metodoPago.
     *
     * @param metodoPago el valor a establecer
     */
    public void setMetodoPago(final IdDescripcion metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * Regresa el campo zona.
     *
     * @return el valor de zona
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Establece el valor del campo zona.
     *
     * @param zona el valor a establecer
     */
    public void setZona(final Zona zona) {
        this.zona = zona;
    }
}
