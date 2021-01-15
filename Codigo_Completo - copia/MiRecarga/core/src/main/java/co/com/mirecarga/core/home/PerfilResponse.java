package co.com.mirecarga.core.home;

/**
 * Datos del usuario.
 */
public class PerfilResponse {
    /**
     * El id del usuario.
     */
    private int id;

    /**
     * El nombre del usuario.
     */
    private String nombre;

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
     * Regresa el campo nombre.
     *
     * @return el valor de nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor del campo nombre.
     *
     * @param nombre el valor a establecer
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
}
