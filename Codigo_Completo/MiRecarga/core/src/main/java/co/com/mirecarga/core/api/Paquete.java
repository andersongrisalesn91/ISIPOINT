package co.com.mirecarga.core.api;

/**
 * Datos del paquete.
 */
public class Paquete extends IdNombre {
    /**
     * El valor del paquete.
     */
    private double valor;

    /**
     * Regresa el campo valor.
     *
     * @return el valor de valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * Establece el valor del campo valor.
     *
     * @param valor el valor a establecer
     */
    public void setValor(final double valor) {
        this.valor = valor;
    }
}
