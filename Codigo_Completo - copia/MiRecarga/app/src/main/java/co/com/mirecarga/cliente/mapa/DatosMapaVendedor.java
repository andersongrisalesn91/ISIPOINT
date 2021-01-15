package co.com.mirecarga.cliente.mapa;

import co.com.mirecarga.core.api.Departamento;
import co.com.mirecarga.core.api.Municipio;
import co.com.mirecarga.core.api.ZonaYParqueadero;

/**
 * Datos seleccionados en el mapa.
 */
public class DatosMapaVendedor {
    /**
     * El departamento seleccionado.
     */
    private Departamento departamento;

    /**
     * El Municipio seleccionado.
     */
    private Municipio municipio;

    /**
     * La zona de búsqueda seleccionada.
     */
    private ZonaYParqueadero zonaYParqueadero;

    /**
     * La zona en el mapa seleccionada.
     */
    private Integer idZonaMapa;

    /**
     * Regresa el campo departamento.
     *
     * @return el valor de departamento
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Establece el valor del campo departamento.
     *
     * @param departamento el valor a establecer
     */
    public void setDepartamento(final Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * Regresa el campo municipio.
     *
     * @return el valor de municipio
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * Establece el valor del campo municipio.
     *
     * @param municipio el valor a establecer
     */
    public void setMunicipio(final Municipio municipio) {
        this.municipio = municipio;
    }

    /**
     * Regresa el campo zonaYParqueadero.
     *
     * @return el valor de zonaYParqueadero
     */
    public ZonaYParqueadero getZonaYParqueadero() {
        return zonaYParqueadero;
    }

    /**
     * Establece el valor del campo zonaYParqueadero.
     *
     * @param zonaYParqueadero el valor a establecer
     */
    public void setZonaYParqueadero(final ZonaYParqueadero zonaYParqueadero) {
        this.zonaYParqueadero = zonaYParqueadero;
    }

    /**
     * Regresa el campo idZonaMapa.
     *
     * @return el valor de idZonaMapa
     */
    public Integer getIdZonaMapa() {
        return idZonaMapa;
    }

    /**
     * Establece el valor del campo idZonaMapa.
     *
     * @param idZonaMapa el valor a establecer
     */
    public void setIdZonaMapa(final Integer idZonaMapa) {
        this.idZonaMapa = idZonaMapa;
    }
}
