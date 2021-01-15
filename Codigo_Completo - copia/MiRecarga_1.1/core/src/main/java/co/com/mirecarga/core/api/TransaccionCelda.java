package co.com.mirecarga.core.api;

import java.util.Date;
import java.util.List;

/**
 * Datos de la tarifa.
 */
public class TransaccionCelda {
    /**
     * Id del item.
     */
    private int id;
    /**
     * El nombre de la zona.
     */
    private String parqueaderoyzona;
    /**
     * El nombre del vendedor.
     */
    private String vendedor;
    /**
     * El nombre del cliente.
     */
    private String cliente;
    /**
     * El id del tipo de vehículo.
     */
    private int idtipodevehiculo;
    /**
     * La placa.
     */
    private String placa;
    /**
     * El id de la celda.
     */
    private int idcelda;
    /**
     * La fecha y hora de entrada.
     */
    private Date fechahoraentrada;
    /**
     * La fecha y hora de vigencia.
     */
    private Date fechahoravigencia;
    /**
     * El texto hms restante.
     */
    private String hms;
    /**
     * Indica si el pago fue completo.
     */
    private boolean pagado;
    /**
     * Indica si hay pendiente de pago.
     */
    private boolean pagopendiente;

    /**
     * Detalles de la respuesta.
     */
    private List<TransaccionCeldaDetalle> detalles;

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
     * Regresa el campo parqueaderoyzona.
     *
     * @return el valor de parqueaderoyzona
     */
    public String getParqueaderoyzona() {
        return parqueaderoyzona;
    }

    /**
     * Establece el valor del campo parqueaderoyzona.
     *
     * @param parqueaderoyzona el valor a establecer
     */
    public void setParqueaderoyzona(final String parqueaderoyzona) {
        this.parqueaderoyzona = parqueaderoyzona;
    }

    /**
     * Regresa el campo vendedor.
     *
     * @return el valor de vendedor
     */
    public String getVendedor() {
        return vendedor;
    }

    /**
     * Establece el valor del campo vendedor.
     *
     * @param vendedor el valor a establecer
     */
    public void setVendedor(final String vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * Regresa el campo cliente.
     *
     * @return el valor de cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Establece el valor del campo cliente.
     *
     * @param cliente el valor a establecer
     */
    public void setCliente(final String cliente) {
        this.cliente = cliente;
    }

    /**
     * Regresa el campo idtipodevehiculo.
     *
     * @return el valor de idtipodevehiculo
     */
    public int getIdtipodevehiculo() {
        return idtipodevehiculo;
    }

    /**
     * Establece el valor del campo idtipodevehiculo.
     *
     * @param idtipodevehiculo el valor a establecer
     */
    public void setIdtipodevehiculo(final int idtipodevehiculo) {
        this.idtipodevehiculo = idtipodevehiculo;
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
     * Regresa el campo idcelda.
     *
     * @return el valor de idcelda
     */
    public int getIdcelda() {
        return idcelda;
    }

    /**
     * Establece el valor del campo idcelda.
     *
     * @param idcelda el valor a establecer
     */
    public void setIdcelda(final int idcelda) {
        this.idcelda = idcelda;
    }

    /**
     * Regresa el campo fechahoraentrada.
     *
     * @return el valor de fechahoraentrada
     */
    public Date getFechahoraentrada() {
        return fechahoraentrada;
    }

    /**
     * Establece el valor del campo fechahoraentrada.
     *
     * @param fechahoraentrada el valor a establecer
     */
    public void setFechahoraentrada(final Date fechahoraentrada) {
        this.fechahoraentrada = fechahoraentrada;
    }

    /**
     * Regresa el campo fechahoravigencia.
     *
     * @return el valor de fechahoravigencia
     */
    public Date getFechahoravigencia() {
        return fechahoravigencia;
    }

    /**
     * Establece el valor del campo fechahoravigencia.
     *
     * @param fechahoravigencia el valor a establecer
     */
    public void setFechahoravigencia(final Date fechahoravigencia) {
        this.fechahoravigencia = fechahoravigencia;
    }

    /**
     * Regresa el campo hms.
     *
     * @return el valor de hms
     */
    public String getHms() {
        return hms;
    }

    /**
     * Establece el valor del campo hms.
     *
     * @param hms el valor a establecer
     */
    public void setHms(final String hms) {
        this.hms = hms;
    }

    /**
     * Regresa el campo detalles.
     *
     * @return el valor de detalles
     */
    public List<TransaccionCeldaDetalle> getDetalles() {
        return detalles;
    }

    /**
     * Establece el valor del campo detalles.
     *
     * @param detalles el valor a establecer
     */
    public void setDetalles(final List<TransaccionCeldaDetalle> detalles) {
        this.detalles = detalles;
    }

    /**
     * Regresa el campo pagado.
     *
     * @return el valor de pagado
     */
    public boolean isPagado() {
        return pagado;
    }

    /**
     * Establece el valor del campo pagado.
     *
     * @param pagado el valor a establecer
     */
    public void setPagado(final boolean pagado) {
        this.pagado = pagado;
    }

    /**
     * Regresa el campo pagopendiente.
     *
     * @return el valor de pagopendiente
     */
    public boolean isPagopendiente() {
        return pagopendiente;
    }

    /**
     * Establece el valor del campo pagopendiente.
     *
     * @param pagopendiente el valor a establecer
     */
    public void setPagopendiente(final boolean pagopendiente) {
        this.pagopendiente = pagopendiente;
    }
}
