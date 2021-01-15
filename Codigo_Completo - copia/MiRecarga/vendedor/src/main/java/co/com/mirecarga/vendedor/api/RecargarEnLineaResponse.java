package co.com.mirecarga.vendedor.api;

import co.com.mirecarga.core.api.MiRecargaResponse;

/**
 * Respuesta del método recargaEnLinea.
 */
public class RecargarEnLineaResponse extends MiRecargaResponse {
    /**
     * El número de celular.
     */
    private String celular;
    /**
     * El nombre del operador.
     */
    private String operador;
    /**
     * El valor recargado.
     */
    private String valor;

    /**
     * Regresa el campo celular.
     *
     * @return el valor de celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Establece el valor del campo celular.
     *
     * @param celular el valor a establecer
     */
    public void setCelular(final String celular) {
        this.celular = celular;
    }

    /**
     * Regresa el campo operador.
     *
     * @return el valor de operador
     */
    public String getOperador() {
        return operador;
    }

    /**
     * Establece el valor del campo operador.
     *
     * @param operador el valor a establecer
     */
    public void setOperador(final String operador) {
        this.operador = operador;
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
