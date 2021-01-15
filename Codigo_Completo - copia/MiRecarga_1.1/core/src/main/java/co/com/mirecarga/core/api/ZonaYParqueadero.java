package co.com.mirecarga.core.api;

import java.util.List;

/**
 * Pojo para la lista de zonas y parqueaderos.
 */

public class ZonaYParqueadero extends DatosBoundingBox {
    /**
     * El identificador de la zona y parqueadero.
     */
    private int id;
    /**
     * El identificador del municipio.
     */
    private int idmunicipio;
    /**
     * El identificador del departamento.
     */
    private int iddepartamento;
    /**
     * El identificador del país.
     */
    private int idpais;
    /**
     * El nombre de la zona y parqueadero.
     */
    private String parqueaderoyzona;
    /**
     * La dirección de la zona y parqueadero.
     */
    private String direccion;
    /**
     * Tiene convenios.
     */
    private boolean tieneconvenios;
    /**
     * El identificador del estado.
     */
    private int idestado;
    /**
     * El identificador del tipo de demarcación.
     */
    private int idtipodemarcacion;
    /**
     * Es prepago.
     */
    private boolean esprepago;
    /**
     * Es reservable.
     */
    private boolean esreservable;

    /**
     * Tipos de celdas.
     */
    private List<TipoCelda> tiposdeceldas;

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
     * Regresa el campo idmunicipio.
     *
     * @return el valor de idmunicipio
     */
    public int getIdmunicipio() {
        return idmunicipio;
    }

    /**
     * Establece el valor del campo idmunicipio.
     *
     * @param idmunicipio el valor a establecer
     */
    public void setIdmunicipio(final int idmunicipio) {
        this.idmunicipio = idmunicipio;
    }

    /**
     * Regresa el campo iddepartamento.
     *
     * @return el valor de iddepartamento
     */
    public int getIddepartamento() {
        return iddepartamento;
    }

    /**
     * Establece el valor del campo iddepartamento.
     *
     * @param iddepartamento el valor a establecer
     */
    public void setIddepartamento(final int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    /**
     * Regresa el campo idpais.
     *
     * @return el valor de idpais
     */
    public int getIdpais() {
        return idpais;
    }

    /**
     * Establece el valor del campo idpais.
     *
     * @param idpais el valor a establecer
     */
    public void setIdpais(final int idpais) {
        this.idpais = idpais;
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
     * Regresa el campo direccion.
     *
     * @return el valor de direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece el valor del campo direccion.
     *
     * @param direccion el valor a establecer
     */
    public void setDireccion(final String direccion) {
        this.direccion = direccion;
    }

    /**
     * Regresa el campo tieneconvenios.
     *
     * @return el valor de tieneconvenios
     */
    public boolean isTieneconvenios() {
        return tieneconvenios;
    }

    /**
     * Establece el valor del campo tieneconvenios.
     *
     * @param tieneconvenios el valor a establecer
     */
    public void setTieneconvenios(final boolean tieneconvenios) {
        this.tieneconvenios = tieneconvenios;
    }

    /**
     * Regresa el campo idestado.
     *
     * @return el valor de idestado
     */
    public int getIdestado() {
        return idestado;
    }

    /**
     * Establece el valor del campo idestado.
     *
     * @param idestado el valor a establecer
     */
    public void setIdestado(final int idestado) {
        this.idestado = idestado;
    }

    /**
     * Regresa el campo idtipodemarcacion.
     *
     * @return el valor de idtipodemarcacion
     */
    public int getIdtipodemarcacion() {
        return idtipodemarcacion;
    }

    /**
     * Establece el valor del campo idtipodemarcacion.
     *
     * @param idtipodemarcacion el valor a establecer
     */
    public void setIdtipodemarcacion(final int idtipodemarcacion) {
        this.idtipodemarcacion = idtipodemarcacion;
    }

    /**
     * Regresa el campo esprepago.
     *
     * @return el valor de esprepago
     */
    public boolean isEsprepago() {
        return esprepago;
    }

    /**
     * Establece el valor del campo esprepago.
     *
     * @param esprepago el valor a establecer
     */
    public void setEsprepago(final boolean esprepago) {
        this.esprepago = esprepago;
    }

    /**
     * Regresa el campo esreservable.
     *
     * @return el valor de esreservable
     */
    public boolean isEsreservable() {
        return esreservable;
    }

    /**
     * Establece el valor del campo esreservable.
     *
     * @param esreservable el valor a establecer
     */
    public void setEsreservable(final boolean esreservable) {
        this.esreservable = esreservable;
    }

    /**
     * Regresa el campo tiposdeceldas.
     *
     * @return el valor de tiposdeceldas
     */
    public List<TipoCelda> getTiposdeceldas() {
        return tiposdeceldas;
    }

    /**
     * Establece el valor del campo tiposdeceldas.
     *
     * @param tiposdeceldas el valor a establecer
     */
    public void setTiposdeceldas(final List<TipoCelda> tiposdeceldas) {
        this.tiposdeceldas = tiposdeceldas;
    }
}
