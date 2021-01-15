package co.com.mirecarga.core.api;

/**
 * Datos de tipos de vehículo.
 */
public class TipoVehiculo {
    /**
     * Id del item.
     */
    private int id;
    /**
     * Nombre del operador.
     */
    private String tipovehiculo;

    /**
     * Regresa el campo id.
     *
     * @return el valor de id
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el valor del campo id.
     *
     * @param id el valor a establecer
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Regresa el campo tipovehiculo.
     *
     * @return el valor de tipovehiculo
     */
    public String getTipovehiculo() {
        return tipovehiculo;
    }

    /**
     * Establece el valor del campo tipovehiculo.
     *
     * @param tipovehiculo el valor a establecer
     */
    public void setTipovehiculo(final String tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }
}
