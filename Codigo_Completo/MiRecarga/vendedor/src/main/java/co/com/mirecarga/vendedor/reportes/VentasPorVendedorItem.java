package co.com.mirecarga.vendedor.reportes;

/**
 * Datos mostrados en la consulta.
 */
public class VentasPorVendedorItem {
    /**
     * El nombre mostrado.
     */
    private String nombre;
    /**
     * El código mostrado.
     */
    private String codigo;
    /**
     * La fecha mostrada.
     */
    private String fecha;

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
     * Regresa el campo fecha.
     *
     * @return el valor de fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece el valor del campo fecha.
     *
     * @param fecha el valor a establecer
     */
    public void setFecha(final String fecha) {
        this.fecha = fecha;
    }
}
