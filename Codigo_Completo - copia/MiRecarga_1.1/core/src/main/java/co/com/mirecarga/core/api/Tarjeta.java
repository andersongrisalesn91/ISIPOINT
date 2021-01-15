package co.com.mirecarga.core.api;

/**
 * Datos de la tarjeta.
 */
public class Tarjeta {
    /**
     * Id del item.
     */
    private int id;
    /**
     * Valor de la propiedad.
     */
    private String codigo;
    /**
     * Valor de la propiedad.
     */
    private long fechaCreacion;
    /**
     * Valor de la propiedad.
     */
    private Long fechaFin;
    /**
     * Valor de la propiedad.
     */
    private double valorSaldo;
    /**
     * Valor de la propiedad.
     */
    private int idEntidad;
    /**
     * Valor de la propiedad.
     */
    private int idCliente;
    /**
     * Valor de la propiedad.
     */
    private String clienteNombre;
    /**
     * Valor de la propiedad.
     */
    private String estado;

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
     * Regresa el campo fechaCreacion.
     *
     * @return el valor de fechaCreacion
     */
    public long getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece el valor del campo fechaCreacion.
     *
     * @param fechaCreacion el valor a establecer
     */
    public void setFechaCreacion(final long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Regresa el campo fechaFin.
     *
     * @return el valor de fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece el valor del campo fechaFin.
     *
     * @param fechaFin el valor a establecer
     */
    public void setFechaFin(final Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Regresa el campo valorSaldo.
     *
     * @return el valor de valorSaldo
     */
    public double getValorSaldo() {
        return valorSaldo;
    }

    /**
     * Establece el valor del campo valorSaldo.
     *
     * @param valorSaldo el valor a establecer
     */
    public void setValorSaldo(final double valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    /**
     * Regresa el campo idEntidad.
     *
     * @return el valor de idEntidad
     */
    public int getIdEntidad() {
        return idEntidad;
    }

    /**
     * Establece el valor del campo idEntidad.
     *
     * @param idEntidad el valor a establecer
     */
    public void setIdEntidad(final int idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * Regresa el campo idCliente.
     *
     * @return el valor de idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el valor del campo idCliente.
     *
     * @param idCliente el valor a establecer
     */
    public void setIdCliente(final int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Regresa el campo clienteNombre.
     *
     * @return el valor de clienteNombre
     */
    public String getClienteNombre() {
        return clienteNombre;
    }

    /**
     * Establece el valor del campo clienteNombre.
     *
     * @param clienteNombre el valor a establecer
     */
    public void setClienteNombre(final String clienteNombre) {
        this.clienteNombre = clienteNombre;
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
