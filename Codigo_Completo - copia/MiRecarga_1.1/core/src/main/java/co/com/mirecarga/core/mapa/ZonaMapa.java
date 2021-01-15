package co.com.mirecarga.core.mapa;

import java.util.List;

import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.api.ZonaYParqueadero;

/**
 * Datos de la zona en el mapa.
 */
public class ZonaMapa extends ZonaPk {
    /**
     * Nombre de la Zona.
     */
    private String nombre;
    /**
     * Estado de la Zona.
     */
    private int idEstado;

    /**
     * Indica si maneja prepago.
     */
    private boolean prepago;

    /**
     * Lista de tipos de vehículo para celdas múltiples.
     */
    private List<TipoVehiculo> tiposVehiculoCeldaMultiple;

    /**
     * Datos completos de la zona regresados por el servicio.
     */
    private ZonaYParqueadero zonaYParqueadero;

    /**
     * Regresa el campo nombre.
     *
     * @return el valor de nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor del campo nombre.
     *
     * @param nombre el valor a establecer
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el campo idEstado.
     *
     * @return el valor de idEstado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el valor del campo idEstado.
     *
     * @param idEstado el valor a establecer
     */
    public void setIdEstado(final int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * Regresa el campo prepago.
     *
     * @return el valor de prepago
     */
    public boolean isPrepago() {
        return prepago;
    }

    /**
     * Establece el valor del campo prepago.
     *
     * @param prepago el valor a establecer
     */
    public void setPrepago(final boolean prepago) {
        this.prepago = prepago;
    }

    /**
     * Regresa el campo tiposVehiculoCeldaMultiple.
     *
     * @return el valor de tiposVehiculoCeldaMultiple
     */
    public List<TipoVehiculo> getTiposVehiculoCeldaMultiple() {
        return tiposVehiculoCeldaMultiple;
    }

    /**
     * Establece el valor del campo tiposVehiculoCeldaMultiple.
     *
     * @param tiposVehiculoCeldaMultiple el valor a establecer
     */
    public void setTiposVehiculoCeldaMultiple(final List<TipoVehiculo> tiposVehiculoCeldaMultiple) {
        this.tiposVehiculoCeldaMultiple = tiposVehiculoCeldaMultiple;
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
}
