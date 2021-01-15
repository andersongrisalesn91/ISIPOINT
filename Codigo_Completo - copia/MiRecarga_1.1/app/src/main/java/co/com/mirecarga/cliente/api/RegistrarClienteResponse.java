package co.com.mirecarga.cliente.api;

/**
 * Respuesta del método RegistrarCliente.
 */
public class RegistrarClienteResponse {
    /**
     * El id del cliente.
     */
    private int id;

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
}
