package co.com.mirecarga.cliente.mapa;

import org.osmdroid.util.GeoPoint;

/**
 * Preferencias del usuario para el mapa.
 */
public class PreferenciasMapaVendedor {
    /**
     * Nivel de zoom del usuario.
     */
    private double zoomLevel;

    /**
     * El centro del mapa actual.
     */
    private GeoPoint center;

    /**
     * Constructor con las propiedades.
     * @param zoomLevel Nivel de zoom del usuario
     * @param center El centro del mapa actual
     */
    public PreferenciasMapaVendedor(final double zoomLevel, final GeoPoint center) {
        this.zoomLevel = zoomLevel;
        this.center = center;
    }

    /**
     * Regresa el campo zoomLevel.
     *
     * @return el valor de zoomLevel
     */
    public double getZoomLevel() {
        return zoomLevel;
    }

    /**
     * Establece el valor del campo zoomLevel.
     *
     * @param zoomLevel el valor a establecer
     */
    public void setZoomLevel(final double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    /**
     * Regresa el campo center.
     *
     * @return el valor de center
     */
    public GeoPoint getCenter() {
        return center;
    }

    /**
     * Establece el valor del campo center.
     *
     * @param center el valor a establecer
     */
    public void setCenter(final GeoPoint center) {
        this.center = center;
    }
}
