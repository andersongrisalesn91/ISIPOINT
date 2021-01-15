package co.com.mirecarga.core.api;

/**
 * Datos para el POST a registrar.
 */
public class RegistrarDatos {
    /**
     * Id del vendedor.
     */
    private int idVendedor;
    /**
     * Id del cliente.
     */
    private int idCliente;
    /**
     * Placa del vehículo.
     */
    private String placa;
    /**
     * Id de la tarifa.
     */
    private int idTarifa;
    /**
     * Id de la unidad de tiempo.
     */
    private int idUnidadDeTiempo;
    /**
     * Id del tipo de vehículo.
     */
    private int idTipoVehiculo;

    /**
     * Regresa el campo idVendedor.
     *
     * @return el valor de idVendedor
     */
    public int getIdVendedor() {
        return idVendedor;
    }

    /**
     * Establece el valor del campo idVendedor.
     *
     * @param idVendedor el valor a establecer
     */
    public void setIdVendedor(final int idVendedor) {
        this.idVendedor = idVendedor;
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
     * Regresa el campo placa.
     *
     * @return el valor de placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece el valor del campo placa.
     *
     * @param placa el valor a establecer
     */
    public void setPlaca(final String placa) {
        this.placa = placa;
    }

    /**
     * Regresa el campo idTarifa.
     *
     * @return el valor de idTarifa
     */
    public int getIdTarifa() {
        return idTarifa;
    }

    /**
     * Establece el valor del campo idTarifa.
     *
     * @param idTarifa el valor a establecer
     */
    public void setIdTarifa(final int idTarifa) {
        this.idTarifa = idTarifa;
    }

    /**
     * Regresa el campo idUnidadDeTiempo.
     *
     * @return el valor de idUnidadDeTiempo
     */
    public int getIdUnidadDeTiempo() {
        return idUnidadDeTiempo;
    }

    /**
     * Establece el valor del campo idUnidadDeTiempo.
     *
     * @param idUnidadDeTiempo el valor a establecer
     */
    public void setIdUnidadDeTiempo(final int idUnidadDeTiempo) {
        this.idUnidadDeTiempo = idUnidadDeTiempo;
    }

    /**
     * Regresa el campo idTipoVehiculo.
     *
     * @return el valor de idTipoVehiculo
     */
    public int getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    /**
     * Establece el valor del campo idTipoVehiculo.
     *
     * @param idTipoVehiculo el valor a establecer
     */
    public void setIdTipoVehiculo(final int idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }
}
