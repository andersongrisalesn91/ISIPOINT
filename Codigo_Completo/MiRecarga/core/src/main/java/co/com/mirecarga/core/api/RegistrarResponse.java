package co.com.mirecarga.core.api;

import java.util.List;

/**
 * Datos de respuesta del método registrar.
 */
public class RegistrarResponse {
    /**
     * El id del registro.
     */
    private int id;

    /**
     * Detalles de la respuesta.
     */
    private List<RegistrarResponseDetalle> detalles;

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
     * Regresa el campo detalles.
     *
     * @return el valor de detalles
     */
    public List<RegistrarResponseDetalle> getDetalles() {
        return detalles;
    }

    /**
     * Establece el valor del campo detalles.
     *
     * @param detalles el valor a establecer
     */
    public void setDetalles(final List<RegistrarResponseDetalle> detalles) {
        this.detalles = detalles;
    }
}
