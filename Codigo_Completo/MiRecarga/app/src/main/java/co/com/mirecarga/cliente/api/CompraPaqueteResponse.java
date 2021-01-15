package co.com.mirecarga.cliente.api;

/**
 * Respuesta del método compraPaquete.
 */

public class CompraPaqueteResponse  extends MiRecargaResponse {
    /**
     * El saldo disponible.
     */
    private String saldoDisponible;
    /**
     * El valor cobrado.
     */
    private String valorCobrado;

    /**
     * Regresa el campo saldoDisponible.
     *
     * @return el valor de saldoDisponible
     */
    public String getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * Establece el valor del campo saldoDisponible.
     *
     * @param saldoDisponible el valor a establecer
     */
    public void setSaldoDisponible(final String saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * Regresa el campo valorCobrado.
     *
     * @return el valor de valorCobrado
     */
    public String getValorCobrado() {
        return valorCobrado;
    }

    /**
     * Establece el valor del campo valorCobrado.
     *
     * @param valorCobrado el valor a establecer
     */
    public void setValorCobrado(final String valorCobrado) {
        this.valorCobrado = valorCobrado;
    }
}
