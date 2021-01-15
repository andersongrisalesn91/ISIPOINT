package co.com.mirecarga.core.api;

/**
 * Datos de la tarifa.
 */
public class Tarifa extends IdNombre {
    /**
     * El id de la unidad de tiempo.
     */
    private int idunidaddetiempo;

    /**
     * El id del tipo de vehículo.
     */
    private int idtipovehiculo;

    /**
     * Regresa el campo idunidaddetiempo.
     *
     * @return el valor de idunidaddetiempo
     */
    public int getIdunidaddetiempo() {
        return idunidaddetiempo;
    }

    /**
     * Establece el valor del campo idunidaddetiempo.
     *
     * @param idunidaddetiempo el valor a establecer
     */
    public void setIdunidaddetiempo(final int idunidaddetiempo) {
        this.idunidaddetiempo = idunidaddetiempo;
    }

    /**
     * Regresa el campo idtipovehiculo.
     *
     * @return el valor de idtipovehiculo
     */
    public int getIdtipovehiculo() {
        return idtipovehiculo;
    }

    /**
     * Establece el valor del campo idtipovehiculo.
     *
     * @param idtipovehiculo el valor a establecer
     */
    public void setIdtipovehiculo(final int idtipovehiculo) {
        this.idtipovehiculo = idtipovehiculo;
    }
}
