package co.com.mirecarga.core.api;

/**
 * Datos de la placa.
 */
public class Placa extends IdNombre {
    /**
     * Propiedad codigo de la placa.
     */
    private String codigo;
    /**
     * Propiedad estado de la placa.
     */
    private String estado;

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
}
