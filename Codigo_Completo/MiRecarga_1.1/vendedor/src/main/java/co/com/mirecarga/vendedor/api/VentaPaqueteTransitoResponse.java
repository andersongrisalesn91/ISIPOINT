package co.com.mirecarga.vendedor.api;

import co.com.mirecarga.core.api.MiRecargaResponse;

/**
 * Respuesta del método ventaPaqueteTransito.
 */
public class VentaPaqueteTransitoResponse extends MiRecargaResponse {
    /**
     * El pin generado.
     */
    private String pin;
    /**
     * El valor recargado.
     */
    private String valor;

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
     * Regresa el campo valor.
     *
     * @return el valor de valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor del campo valor.
     *
     * @param valor el valor a establecer
     */
    public void setValor(final String valor) {
        this.valor = valor;
    }
}
