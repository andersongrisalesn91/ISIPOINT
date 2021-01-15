package co.com.mirecarga.core.home;

/**
 * Datos para mostrar el menú.
 */
public class MenuOpcion {
    /**
     * Id del item.
     */
    private int id;

    /**
     * Nombre del menú.
     */
    private String nombre;

    /**
     * Texto descriptivo para el menú.
     */
    private String descripcion;

    /**
     * Nombre del icono a usar.
     */
    private String icono;

    /**
     * Nombre completo de la clase a instanciar.
     */
    private String clase;

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

    /**
     * Regresa el campo icono.
     *
     * @return el valor de icono
     */
    public String getIcono() {
        return icono;
    }

    /**
     * Establece el valor del campo icono.
     *
     * @param icono el valor a establecer
     */
    public void setIcono(final String icono) {
        this.icono = icono;
    }

    /**
     * Regresa el campo clase.
     *
     * @return el valor de clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * Establece el valor del campo clase.
     *
     * @param clase el valor a establecer
     */
    public void setClase(final String clase) {
        this.clase = clase;
    }

    /**
     * Constructor vacío.
     */
    public MenuOpcion() {
        super();
    }
}
