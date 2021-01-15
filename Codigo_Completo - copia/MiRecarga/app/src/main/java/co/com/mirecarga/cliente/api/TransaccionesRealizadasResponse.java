package co.com.mirecarga.cliente.api;

/**
 * Respuesta del método transaccionesRealizadas.
 */

public class TransaccionesRealizadasResponse {
    /**
     * La respuesta de la operación.
     */
    private String operacion;
    /**
     * La respuesta de el mensaje.
     */
    private String mensaje;
    /**
     * La respuesta del usuario creación.
     */
    private String usuarioCreacion;
    /**
     * La respuesta del origen.
     */
    private String origen;
    /**
     * La respuesta del dato1.
     */
    private String dato1;
    /**
     * La respuesta del dato2.
     */
    private String dato2;
    /**
     * La respuesta de la fecha de creación.
     */
    private Long fechaCreacion;

    /**
     * Regresa el campo operacion.
     *
     * @return el valor de operacion
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * Establece el valor del campo operacion.
     *
     * @param operacion el valor a establecer
     */
    public void setOperacion(final String operacion) {
        this.operacion = operacion;
    }

    /**
     * Regresa el campo mensaje.
     *
     * @return el valor de mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el valor del campo mensaje.
     *
     * @param mensaje el valor a establecer
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Regresa el campo usuarioCreacion.
     *
     * @return el valor de usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * Establece el valor del campo usuarioCreacion.
     *
     * @param usuarioCreacion el valor a establecer
     */
    public void setUsuarioCreacion(final String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * Regresa el campo origen.
     *
     * @return el valor de origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Establece el valor del campo origen.
     *
     * @param origen el valor a establecer
     */
    public void setOrigen(final String origen) {
        this.origen = origen;
    }

    /**
     * Regresa el campo dato1.
     *
     * @return el valor de dato1
     */
    public String getDato1() {
        return dato1;
    }

    /**
     * Establece el valor del campo dato1.
     *
     * @param dato1 el valor a establecer
     */
    public void setDato1(final String dato1) {
        this.dato1 = dato1;
    }

    /**
     * Regresa el campo dato2.
     *
     * @return el valor de dato2
     */
    public String getDato2() {
        return dato2;
    }

    /**
     * Establece el valor del campo dato2.
     *
     * @param dato2 el valor a establecer
     */
    public void setDato2(final String dato2) {
        this.dato2 = dato2;
    }

    /**
     * Regresa el campo fechaCreacion.
     *
     * @return el valor de fechaCreacion
     */
    public Long getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece el valor del campo fechaCreacion.
     *
     * @param fechaCreacion el valor a establecer
     */
    public void setFechaCreacion(final Long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
