package co.com.mirecarga.core.mapa;

/**
 * Parámetros de configuración del mapa del vendedor.
 */
public class ConfigMapa {

    /**
     * La url del api de cartografía.
     */
    private String urlCartografia;

    /**
     * La url del api de ZER.
     */
    private String urlZER;

    /**
     * Token temoral para consumir el api de ZER.
     */
    private String tokenTemporalZER;

    /**
     * Identificador del país por defecto.
     */
    private int idPaisDefecto;
    /**
     * Esquema del archivo KML para identificar las zonas.
     */
    private String schemaZonasKML;
    /**
     * Esquema del archivo KML para identificar las celdas.
     */
    private String schemaCeldasKML;

    /**
     * Máximo nivel de zoom permitido.
     */
    private double maxZoomLevel;

    /**
     * Zoom mínimo para mostrar el overlay de celdas.
     */
    private double minZoomLevelCeldas;

    /**
     * Zoom inicial a manejar si no se encuentra configuración.
     */
    private double defaultZoomLevel;

    /**
     * Regresa el campo urlCartografia.
     *
     * @return el valor de urlCartografia
     */
    public String getUrlCartografia() {
        return urlCartografia;
    }

    /**
     * Establece el valor del campo urlCartografia.
     *
     * @param urlCartografia el valor a establecer
     */
    public void setUrlCartografia(final String urlCartografia) {
        this.urlCartografia = urlCartografia;
    }

    /**
     * Regresa el campo urlZER.
     *
     * @return el valor de urlZER
     */
    public String getUrlZER() {
        return urlZER;
    }

    /**
     * Establece el valor del campo urlZER.
     *
     * @param urlZER el valor a establecer
     */
    public void setUrlZER(final String urlZER) {
        this.urlZER = urlZER;
    }

    /**
     * Regresa el campo tokenTemporalZER.
     *
     * @return el valor de tokenTemporalZER
     */
    public String getTokenTemporalZER() {
        return tokenTemporalZER;
    }

    /**
     * Establece el valor del campo tokenTemporalZER.
     *
     * @param tokenTemporalZER el valor a establecer
     */
    public void setTokenTemporalZER(final String tokenTemporalZER) {
        this.tokenTemporalZER = tokenTemporalZER;
    }

    /**
     * Regresa el campo idPaisDefecto.
     *
     * @return el valor de idPaisDefecto
     */
    public int getIdPaisDefecto() {
        return idPaisDefecto;
    }

    /**
     * Establece el valor del campo idPaisDefecto.
     *
     * @param idPaisDefecto el valor a establecer
     */
    public void setIdPaisDefecto(final int idPaisDefecto) {
        this.idPaisDefecto = idPaisDefecto;
    }

    /**
     * Regresa el campo schemaZonasKML.
     *
     * @return el valor de schemaZonasKML
     */
    public String getSchemaZonasKML() {
        return schemaZonasKML;
    }

    /**
     * Establece el valor del campo schemaZonasKML.
     *
     * @param schemaZonasKML el valor a establecer
     */
    public void setSchemaZonasKML(final String schemaZonasKML) {
        this.schemaZonasKML = schemaZonasKML;
    }

    /**
     * Regresa el campo schemaCeldasKML.
     *
     * @return el valor de schemaCeldasKML
     */
    public String getSchemaCeldasKML() {
        return schemaCeldasKML;
    }

    /**
     * Establece el valor del campo schemaCeldasKML.
     *
     * @param schemaCeldasKML el valor a establecer
     */
    public void setSchemaCeldasKML(final String schemaCeldasKML) {
        this.schemaCeldasKML = schemaCeldasKML;
    }

    /**
     * Regresa el campo maxZoomLevel.
     *
     * @return el valor de maxZoomLevel
     */
    public double getMaxZoomLevel() {
        return maxZoomLevel;
    }

    /**
     * Establece el valor del campo maxZoomLevel.
     *
     * @param maxZoomLevel el valor a establecer
     */
    public void setMaxZoomLevel(final double maxZoomLevel) {
        this.maxZoomLevel = maxZoomLevel;
    }

    /**
     * Regresa el campo minZoomLevelCeldas.
     *
     * @return el valor de minZoomLevelCeldas
     */
    public double getMinZoomLevelCeldas() {
        return minZoomLevelCeldas;
    }

    /**
     * Establece el valor del campo minZoomLevelCeldas.
     *
     * @param minZoomLevelCeldas el valor a establecer
     */
    public void setMinZoomLevelCeldas(final double minZoomLevelCeldas) {
        this.minZoomLevelCeldas = minZoomLevelCeldas;
    }

    /**
     * Regresa el campo defaultZoomLevel.
     *
     * @return el valor de defaultZoomLevel
     */
    public double getDefaultZoomLevel() {
        return defaultZoomLevel;
    }

    /**
     * Establece el valor del campo defaultZoomLevel.
     *
     * @param defaultZoomLevel el valor a establecer
     */
    public void setDefaultZoomLevel(final double defaultZoomLevel) {
        this.defaultZoomLevel = defaultZoomLevel;
    }
}
