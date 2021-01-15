package co.com.mirecarga.vendedor.ventatarjeta;

import java.math.BigDecimal;

import co.com.mirecarga.vendedor.api.Cliente;
import co.com.mirecarga.vendedor.api.TarjetaPrepago;

/**
 * Datos para la funcionalidad de confirmar venta tarjetaPrepago tránsito.
 */
public class ConfirmarVentaTarjetaDatos {

    /**
     * El saldo actual disponible para ventas.
     */
    private String saldo;

    /**
     * El cliente seleccionado.
     */
    private Cliente cliente;

    /**
     * El precio del producto.
     */
    private BigDecimal precio;

    /**
     * El tarjetaPrepago seleccionado.
     */
    private TarjetaPrepago tarjetaPrepago;

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
     * Regresa el campo precio.
     *
     * @return el valor de precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Establece el valor del campo precio.
     *
     * @param precio el valor a establecer
     */
    public void setPrecio(final BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * Regresa el campo tarjetaPrepago.
     *
     * @return el valor de tarjetaPrepago
     */
    public TarjetaPrepago getTarjetaPrepago() {
        return tarjetaPrepago;
    }

    /**
     * Establece el valor del campo tarjetaPrepago.
     *
     * @param tarjetaPrepago el valor a establecer
     */
    public void setTarjetaPrepago(final TarjetaPrepago tarjetaPrepago) {
        this.tarjetaPrepago = tarjetaPrepago;
    }
}
