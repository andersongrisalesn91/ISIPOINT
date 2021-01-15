package co.com.mirecarga.cliente.api;

/**
 * Respuesta del método finalziarUsoPin.
 */

public class FinalizarUsoPinResponse  extends MiRecargaResponse {
    /**
     * El saldo disponible.
     */
    private String saldoDisponible;
    /**
     * El valor cobrado.
     */
    private String valorCobrado;
    /**
     * El total de unidades.
     */
    private String totalUnidades;

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

    /**
     * Regresa el campo totalUnidades.
     *
     * @return el valor de totalUnidades
     */
    public String getTotalUnidades() {
        return totalUnidades;
    }

    /**
     * Establece el valor del campo totalUnidades.
     *
     * @param totalUnidades el valor a establecer
     */
    public void setTotalUnidades(final String totalUnidades) {
        this.totalUnidades = totalUnidades;
    }
}
