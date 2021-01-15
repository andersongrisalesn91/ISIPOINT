package co.com.mirecarga.cliente.mapa;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.kml.KmlTrack;
import org.osmdroid.bonuspack.kml.Style;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.core.mapa.InterpreteMapa;
import co.com.mirecarga.core.mapa.ConfigMapa;

/**
 * Clase para administrar los poligonos.
 */
public class CustomTapPolygon implements KmlFeature.Styler {

    /**
     * El tag para el log.
     */
    private static final String TAG = "CustomTapPolygon";
    /**
     * El objeto que procesa los eventos.
     */
    private final transient EventosMapaCliente eventosMapaCliente;

    /**
     * Documento sobre el que se aplican los estilos.
     */
    private final transient  KmlDocument kmlDocument;

    /**
     * Mapa sobre el que se aplican los estilos.
     */
    private final transient MapView mapView;

    /**
     * Estilo por defecto.
     */
    private final Style defaultStyle;

    /**
     * Configuración del mapa.
     */
    private ConfigMapa configMapa;

    /**
     * Intérprete de datos contenidos en el mapa.
     */
    private final InterpreteMapa parser = new InterpreteMapa();

    /**
     * El constructor.
     * @param eventosMapaCliente el objeto que procesa los eventos
     * @param kmlDocument el Documento sobre el que se aplican los estilos
     * @param mapView el Mapa sobre el que se aplican los estilos
     * @param defaultStyle el estilo por defecto
     */
    public CustomTapPolygon(final EventosMapaCliente eventosMapaCliente, final KmlDocument kmlDocument,
                            final MapView mapView, final Style defaultStyle) {
        this.eventosMapaCliente = eventosMapaCliente;
        this.kmlDocument = kmlDocument;
        this.mapView = mapView;
        this.defaultStyle = defaultStyle;
        this.configMapa = eventosMapaCliente.getConfigService().getConfig().getMapa();
    }

    /**
     * El constructor.
     * @param eventosMapaCliente el objeto que procesa los eventos
     * @param kmlDocument el Documento sobre el que se aplican los estilos
     * @param mapView el Mapa sobre el que se aplican los estilos
     */
    public CustomTapPolygon(final EventosMapaCliente eventosMapaCliente, final KmlDocument kmlDocument,
                            final MapView mapView) {
        this(eventosMapaCliente, kmlDocument, mapView, null);
    }
    /**
     * Construye el manejador de diálogos.
     * @param kmlPlacemark el objeto analizado
     * @return el objeto para diálogos
     */
    private InfoWindow getInfoWindow(final KmlPlacemark kmlPlacemark) {
        final InfoWindow info;
        if (esZona(kmlPlacemark.mId)) {
            info = new InfoWindowZona(mapView, eventosMapaCliente);
        } else if (esCelda(kmlPlacemark.mId)) {
            final String description = kmlPlacemark.mDescription;
            final int idTipoCelda = Integer.parseInt(parser.getValor(description, "id_tipo_celda"));
            if (idTipoCelda == InfoWindowCeldaMultiple.ID_TIPO_CELDA_MULTIPLE) {
                // Celda con múltiples vehículos
                info = new InfoWindowCeldaMultiple(mapView, eventosMapaCliente);
            } else {
                info = new InfoWindowCeldaOcupada(mapView, eventosMapaCliente);
            }
        } else {
            info = null;
        }
        return info;
    }

    @Override
    public final void onFeature(final Overlay overlay, final KmlFeature kmlFeature) {
        // No se debe ejecutar código
    }

    @Override
    public final void onPoint(final Marker marker, final KmlPlacemark kmlPlacemark, final KmlPoint kmlPoint) {
        kmlPoint.applyDefaultStyling(marker, defaultStyle, kmlPlacemark, kmlDocument, mapView);
        marker.setRelatedObject(kmlPlacemark);
        marker.setInfoWindow(getInfoWindow(kmlPlacemark));
    }

    @Override
    public final void onLineString(final Polyline polyline, final KmlPlacemark kmlPlacemark, final KmlLineString kmlLineString) {
        kmlLineString.applyDefaultStyling(polyline, defaultStyle, kmlPlacemark, kmlDocument, mapView);
    }

    @Override
    public final void onPolygon(final Polygon polygon, final KmlPlacemark kmlPlacemark, final KmlPolygon kmlPolygon) {
        kmlPolygon.applyDefaultStyling(polygon, defaultStyle, kmlPlacemark, kmlDocument, mapView);
        polygon.setRelatedObject(kmlPlacemark);
        polygon.setInfoWindow(getInfoWindow(kmlPlacemark));
    }

    @Override
    public final void onTrack(final Polyline polyline, final KmlPlacemark kmlPlacemark, final KmlTrack kmlTrack) {
        kmlTrack.applyDefaultStyling(polyline, defaultStyle, kmlPlacemark, kmlDocument, mapView);
    }

    /**
     * Verifica si el poligono es una zona.
     * @param mId la cadena con el schema
     * @return verdadero si es zona
     */
    private boolean esZona(final String mId) {
        return mId != null && mId.startsWith(configMapa.getSchemaZonasKML());
    }

    /**
     * Verifica si el poligono es una celda.
     * @param mId la cadena con el schema
     * @return verdadero si es zona
     */
    private boolean esCelda(final String mId) {
        return mId != null && mId.startsWith(configMapa.getSchemaCeldasKML());
    }

}
