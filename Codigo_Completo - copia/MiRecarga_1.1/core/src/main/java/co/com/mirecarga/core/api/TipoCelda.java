package co.com.mirecarga.core.api;

import java.util.List;

/**
 * Datos de tipos de celda.
 */
public class TipoCelda {
    /**
     * El id del tipo.
     */
    private int idtipodecelda;

    /**
     * La cantidad de celdas.
     */
    private int cantidad;

    /**
     * Indica si es un tipo de celda múltiple.
     */
    private boolean contieneotrostipos;

    /**
     * Lista de tipos de vehículo que contiene.
     */
    private List<TipoVehiculo> tiposdevehiculos;

    /**
     * Regresa el campo idtipodecelda.
     *
     * @return el valor de idtipodecelda
     */
    public int getIdtipodecelda() {
        return idtipodecelda;
    }

    /**
     * Establece el valor del campo idtipodecelda.
     *
     * @param idtipodecelda el valor a establecer
     */
    public void setIdtipodecelda(final int idtipodecelda) {
        this.idtipodecelda = idtipodecelda;
    }

    /**
     * Regresa el campo cantidad.
     *
     * @return el valor de cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece el valor del campo cantidad.
     *
     * @param cantidad el valor a establecer
     */
    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Regresa el campo contieneotrostipos.
     *
     * @return el valor de contieneotrostipos
     */
    public boolean isContieneotrostipos() {
        return contieneotrostipos;
    }

    /**
     * Establece el valor del campo contieneotrostipos.
     *
     * @param contieneotrostipos el valor a establecer
     */
    public void setContieneotrostipos(final boolean contieneotrostipos) {
        this.contieneotrostipos = contieneotrostipos;
    }

    /**
     * Regresa el campo tiposdevehiculos.
     *
     * @return el valor de tiposdevehiculos
     */
    public List<TipoVehiculo> getTiposdevehiculos() {
        return tiposdevehiculos;
    }

    /**
     * Establece el valor del campo tiposdevehiculos.
     *
     * @param tiposdevehiculos el valor a establecer
     */
    public void setTiposdevehiculos(final List<TipoVehiculo> tiposdevehiculos) {
        this.tiposdevehiculos = tiposdevehiculos;
    }
}
