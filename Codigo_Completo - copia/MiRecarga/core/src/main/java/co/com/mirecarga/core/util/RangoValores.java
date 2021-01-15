package co.com.mirecarga.core.util;

/**
 * Valores mínimos y máximos configurable.
 */
public class RangoValores {
    /**
     * Valor mínimo.
     */
    private int minimo;
    /**
     * Valos máximo.
     */
    private  int maximo;

    /**
     * Regresa el campo minimo.
     *
     * @return el valor de minimo
     */
    public int getMinimo() {
        return minimo;
    }

    /**
     * Establece el valor del campo minimo.
     *
     * @param minimo el valor a establecer
     */
    public void setMinimo(final int minimo) {
        this.minimo = minimo;
    }

    /**
     * Regresa el campo maximo.
     *
     * @return el valor de maximo
     */
    public int getMaximo() {
        return maximo;
    }

    /**
     * Establece el valor del campo maximo.
     *
     * @param maximo el valor a establecer
     */
    public void setMaximo(final int maximo) {
        this.maximo = maximo;
    }
}
