package co.com.mirecarga.core.api;

/**
 * Datos de la zona.
 */
@SuppressWarnings("PMD.ShortClassName")
public class Zona extends IdNombre {
    /**
     * Id del item.
     */
    private String codigo;
    /**
     * Id del item.
     */
    private String estado;
    /**
     * Id del item.
     */
    private String coordenadas;
    /**
     * Id del item.
     */
    private int capacidad;

    /**
     * Regresa el campo codigo.
     *
     * @return el valor de codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el valor del campo codigo.
     *
     * @param codigo el valor a establecer
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * Regresa el campo estado.
     *
     * @return el valor de estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el valor del campo estado.
     *
     * @param estado el valor a establecer
     */
    public void setEstado(final String estado) {
        this.estado = estado;
    }

    /**
     * Regresa el campo coordenadas.
     *
     * @return el valor de coordenadas
     */
    public String getCoordenadas() {
        return coordenadas;
    }

    /**
     * Establece el valor del campo coordenadas.
     *
     * @param coordenadas el valor a establecer
     */
    public void setCoordenadas(final String coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * Regresa el campo capacidad.
     *
     * @return el valor de capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece el valor del campo capacidad.
     *
     * @param capacidad el valor a establecer
     */
    public void setCapacidad(final int capacidad) {
        this.capacidad = capacidad;
    }
}
