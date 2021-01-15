package co.com.mirecarga.vendedor.api;

import java.math.BigDecimal;

/**
 * Datos de precio para el producto.
 */
public class PrecioProducto {
    /**
     * Valor del producto.
     */
    private int valorasint;

    /**
     * Valor del producto.
     */
    private BigDecimal valorasbigdecimal;

    /**
     * Regresa el campo valorasint.
     *
     * @return el valor de valorasint
     */
    public int getValorasint() {
        return valorasint;
    }

    /**
     * Establece el valor del campo valorasint.
     *
     * @param valorasint el valor a establecer
     */
    public void setValorasint(final int valorasint) {
        this.valorasint = valorasint;
    }

    /**
     * Regresa el campo valorasbigdecimal.
     *
     * @return el valor de valorasbigdecimal
     */
    public BigDecimal getValorasbigdecimal() {
        return valorasbigdecimal;
    }

    /**
     * Establece el valor del campo valorasbigdecimal.
     *
     * @param valorasbigdecimal el valor a establecer
     */
    public void setValorasbigdecimal(final BigDecimal valorasbigdecimal) {
        this.valorasbigdecimal = valorasbigdecimal;
    }
}
