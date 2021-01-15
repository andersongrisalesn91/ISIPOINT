package co.com.mirecarga.vendedor.mapa;

import java.util.List;

import co.com.mirecarga.core.api.EstadoCelda;
import co.com.mirecarga.core.api.TipoVehiculo;

/**
 * Opciones de filtro para el mapa del vendedor.
 */
public class FiltroMapa {
    /**
     * Estado a filtrar.
     */
    private EstadoCelda estadoCeldaFiltrar;

    /**
     * Tipo de vehículo a filtrar.
     */
    private TipoVehiculo tipoVehiculoFiltrar;

    /**
     * Caché de estados de celdas.
     */
    private List<EstadoCelda> cacheEstado;

    /**
     * Caché de tipos de vehículo.
     */
    private List<TipoVehiculo> cacheTipoVehiculo;

    /**
     * Filtro precalculado para las consultas.
     */
    private String filtroCql;

    /**
     * Regresa el campo estadoCeldaFiltrar.
     *
     * @return el valor de estadoCeldaFiltrar
     */
    public EstadoCelda getEstadoCeldaFiltrar() {
        return estadoCeldaFiltrar;
    }

    /**
     * Establece el valor del campo estadoCeldaFiltrar.
     *
     * @param estadoCeldaFiltrar el valor a establecer
     */
    public void setEstadoCeldaFiltrar(final EstadoCelda estadoCeldaFiltrar) {
        this.estadoCeldaFiltrar = estadoCeldaFiltrar;
    }

    /**
     * Regresa el campo tipoVehiculoFiltrar.
     *
     * @return el valor de tipoVehiculoFiltrar
     */
    public TipoVehiculo getTipoVehiculoFiltrar() {
        return tipoVehiculoFiltrar;
    }

    /**
     * Establece el valor del campo tipoVehiculoFiltrar.
     *
     * @param tipoVehiculoFiltrar el valor a establecer
     */
    public void setTipoVehiculoFiltrar(final TipoVehiculo tipoVehiculoFiltrar) {
        this.tipoVehiculoFiltrar = tipoVehiculoFiltrar;
    }

    /**
     * Regresa el campo cacheEstado.
     *
     * @return el valor de cacheEstado
     */
    public List<EstadoCelda> getCacheEstado() {
        return cacheEstado;
    }

    /**
     * Establece el valor del campo cacheEstado.
     *
     * @param cacheEstado el valor a establecer
     */
    public void setCacheEstado(final List<EstadoCelda> cacheEstado) {
        this.cacheEstado = cacheEstado;
    }

    /**
     * Regresa el campo cacheTipoVehiculo.
     *
     * @return el valor de cacheTipoVehiculo
     */
    public List<TipoVehiculo> getCacheTipoVehiculo() {
        return cacheTipoVehiculo;
    }

    /**
     * Establece el valor del campo cacheTipoVehiculo.
     *
     * @param cacheTipoVehiculo el valor a establecer
     */
    public void setCacheTipoVehiculo(final List<TipoVehiculo> cacheTipoVehiculo) {
        this.cacheTipoVehiculo = cacheTipoVehiculo;
    }

    /**
     * Regresa el campo filtroCql.
     *
     * @return el valor de filtroCql
     */
    public String getFiltroCql() {
        return filtroCql;
    }

    /**
     * Establece el valor del campo filtroCql.
     *
     * @param filtroCql el valor a establecer
     */
    public void setFiltroCql(final String filtroCql) {
        this.filtroCql = filtroCql;
    }
}
