package co.com.mirecarga.vendedor.api;

import co.com.mirecarga.core.api.MiRecargaResponse;

/**
 * Respuesta del método consultasaldo.
 */
public class ConsultaSaldoResponse extends MiRecargaResponse {
    /**
     * El saldo del vendedor en el producto.
     */
    private String saldo;

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
