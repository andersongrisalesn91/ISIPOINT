package co.com.mirecarga.vendedor.api;

/**
 * Datos del operador para la funcionalidad de recarga en línea.
 */
public class Operador {
    /**
     * Id del item.
     */
    private int id;
    /**
     * Nombre del operador.
     */
    private String nombre;
    /**
     * Valor del operador.
     */
    private String valor;

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
     * Regresa el campo valor.
     *
     * @return el valor de valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor del campo valor.
     *
     * @param valor el valor a establecer
     */
    public void setValor(final String valor) {
        this.valor = valor;
    }
}
