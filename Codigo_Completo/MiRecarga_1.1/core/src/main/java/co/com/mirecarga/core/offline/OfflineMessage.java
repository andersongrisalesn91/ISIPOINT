package co.com.mirecarga.core.offline;

/**
 * Datos para almacenamiento fuera de línea.
 */
public class OfflineMessage {
    /**
     * El id del registro.
     */
    private long id;

    /**
     * Tipo de mensaje.
     */
    private String tipo;

    /**
     * Texto del mensaje como json.
     */
    private String texto;

    /**
     * Regresa el campo id.
     *
     * @return el valor de id
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el valor del campo id.
     *
     * @param id el valor a establecer
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Regresa el campo tipo.
     *
     * @return el valor de tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el valor del campo tipo.
     *
     * @param tipo el valor a establecer
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * Regresa el campo texto.
     *
     * @return el valor de texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el valor del campo texto.
     *
     * @param texto el valor a establecer
     */
    public void setTexto(final String texto) {
        this.texto = texto;
    }
}
