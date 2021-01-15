package co.com.mirecarga.core.util;

/**
 * POJO del estilo id numérico y texto.
 */

public class IdDescripcion {
    /**
     * Id del registro.
     */
    private Integer id;

    /**
     * Texto asociado al id.
     */
    private String descripcion;

    /**
     * Regresa el campo id.
     *
     * @return el valor de id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el valor del campo id.
     *
     * @param id el valor a establecer
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Regresa el campo descripcion.
     *
     * @return el valor de descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece el valor del campo descripcion.
     *
     * @param descripcion el valor a establecer
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
}
