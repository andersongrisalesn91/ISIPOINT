package co.com.mirecarga.vendedor.api;

/**
 * Datos de la consulta de ventas vendedor.
 */
@SuppressWarnings("PMD.TooManyFields")
public class ReporteItem {
    /**
     * Valor de la propiedad.
     */
    private int id;
    /**
     * Valor de la propiedad.
     */
    private String entidadNombre;
    /**
     * Valor de la propiedad.
     */
    private int idEntidad;
    /**
     * Valor de la propiedad.
     */
    private String idCliente;
    /**
     * Valor de la propiedad.
     */
    private String clienteNombre;
    /**
     * Valor de la propiedad.
     */
    private int idProducto;
    /**
     * Valor de la propiedad.
     */
    private String productoNombre;
    /**
     * Valor de la propiedad.
     */
    private double valorTotal;
    /**
     * Valor de la propiedad.
     */
    private double valorSaldoBalance;
    /**
     * Valor de la propiedad.
     */
    private double valorNuevoSaldoBalance;
    /**
     * Valor de la propiedad.
     */
    private String estado;
    /**
     * Valor de la propiedad.
     */
    private long fechaCreacion;
    /**
     * Valor de la propiedad.
     */
    private long fechaFin;
    /**
     * Valor de la propiedad.
     */
    private String establecimientoNombre;
    /**
     * Valor de la propiedad.
     */
    private String keyOne;
    /**
     * Valor de la propiedad.
     */
    private String keyTwo;
    /**
     * Valor de la propiedad.
     */
    private String keyThree;
    /**
     * Valor de la propiedad.
     */
    private String keyFour;

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
     * Regresa el campo entidadNombre.
     *
     * @return el valor de entidadNombre
     */
    public String getEntidadNombre() {
        return entidadNombre;
    }

    /**
     * Establece el valor del campo entidadNombre.
     *
     * @param entidadNombre el valor a establecer
     */
    public void setEntidadNombre(final String entidadNombre) {
        this.entidadNombre = entidadNombre;
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
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el valor del campo idCliente.
     *
     * @param idCliente el valor a establecer
     */
    public void setIdCliente(final String idCliente) {
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
     * Regresa el campo idProducto.
     *
     * @return el valor de idProducto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el valor del campo idProducto.
     *
     * @param idProducto el valor a establecer
     */
    public void setIdProducto(final int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Regresa el campo productoNombre.
     *
     * @return el valor de productoNombre
     */
    public String getProductoNombre() {
        return productoNombre;
    }

    /**
     * Establece el valor del campo productoNombre.
     *
     * @param productoNombre el valor a establecer
     */
    public void setProductoNombre(final String productoNombre) {
        this.productoNombre = productoNombre;
    }

    /**
     * Regresa el campo valorTotal.
     *
     * @return el valor de valorTotal
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Establece el valor del campo valorTotal.
     *
     * @param valorTotal el valor a establecer
     */
    public void setValorTotal(final double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Regresa el campo valorSaldoBalance.
     *
     * @return el valor de valorSaldoBalance
     */
    public double getValorSaldoBalance() {
        return valorSaldoBalance;
    }

    /**
     * Establece el valor del campo valorSaldoBalance.
     *
     * @param valorSaldoBalance el valor a establecer
     */
    public void setValorSaldoBalance(final double valorSaldoBalance) {
        this.valorSaldoBalance = valorSaldoBalance;
    }

    /**
     * Regresa el campo valorNuevoSaldoBalance.
     *
     * @return el valor de valorNuevoSaldoBalance
     */
    public double getValorNuevoSaldoBalance() {
        return valorNuevoSaldoBalance;
    }

    /**
     * Establece el valor del campo valorNuevoSaldoBalance.
     *
     * @param valorNuevoSaldoBalance el valor a establecer
     */
    public void setValorNuevoSaldoBalance(final double valorNuevoSaldoBalance) {
        this.valorNuevoSaldoBalance = valorNuevoSaldoBalance;
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
    public long getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece el valor del campo fechaFin.
     *
     * @param fechaFin el valor a establecer
     */
    public void setFechaFin(final long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Regresa el campo establecimientoNombre.
     *
     * @return el valor de establecimientoNombre
     */
    public String getEstablecimientoNombre() {
        return establecimientoNombre;
    }

    /**
     * Establece el valor del campo establecimientoNombre.
     *
     * @param establecimientoNombre el valor a establecer
     */
    public void setEstablecimientoNombre(final String establecimientoNombre) {
        this.establecimientoNombre = establecimientoNombre;
    }

    /**
     * Regresa el campo keyOne.
     *
     * @return el valor de keyOne
     */
    public String getKeyOne() {
        return keyOne;
    }

    /**
     * Establece el valor del campo keyOne.
     *
     * @param keyOne el valor a establecer
     */
    public void setKeyOne(final String keyOne) {
        this.keyOne = keyOne;
    }

    /**
     * Regresa el campo keyTwo.
     *
     * @return el valor de keyTwo
     */
    public String getKeyTwo() {
        return keyTwo;
    }

    /**
     * Establece el valor del campo keyTwo.
     *
     * @param keyTwo el valor a establecer
     */
    public void setKeyTwo(final String keyTwo) {
        this.keyTwo = keyTwo;
    }

    /**
     * Regresa el campo keyThree.
     *
     * @return el valor de keyThree
     */
    public String getKeyThree() {
        return keyThree;
    }

    /**
     * Establece el valor del campo keyThree.
     *
     * @param keyThree el valor a establecer
     */
    public void setKeyThree(final String keyThree) {
        this.keyThree = keyThree;
    }

    /**
     * Regresa el campo keyFour.
     *
     * @return el valor de keyFour
     */
    public String getKeyFour() {
        return keyFour;
    }

    /**
     * Establece el valor del campo keyFour.
     *
     * @param keyFour el valor a establecer
     */
    public void setKeyFour(final String keyFour) {
        this.keyFour = keyFour;
    }
}
