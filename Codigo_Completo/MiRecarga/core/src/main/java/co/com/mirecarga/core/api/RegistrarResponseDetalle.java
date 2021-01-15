package co.com.mirecarga.core.api;

/**
 * Datos del detalle de la respuesta de la venta.
 */
public class RegistrarResponseDetalle {
    /**
     * Valor de costo total.
     */
    private double costototal;

    /**
     * Regresa el campo costototal.
     *
     * @return el valor de costototal
     */
    public double getCostototal() {
        return costototal;
    }

    /**
     * Establece el valor del campo costototal.
     *
     * @param costototal el valor a establecer
     */
    public void setCostototal(final double costototal) {
        this.costototal = costototal;
    }
}
