package co.com.mirecarga.core.mapa;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.nio.charset.StandardCharsets;

import co.com.mirecarga.core.util.AppLog;

/**
 * Utilidades para mapas.
 */
public final class MapaUtil {
    /**
     * El tag para el log.
     */
    private static final String TAG = "MapaUtil";

    /**
     * Diferencia permitida para bboxContains.
     */
    private static final double DIFF_BBOX = 0.0002;

    /**
     * Factor a aplicar al ampliar un bbox.
     */
    private static final double FACTOR_BBOX_AMPLIADO = 0.1;

    /**
     * Constructor privado para evitar instancias.
     */
    private MapaUtil() {
        // Constructor privado para evitar instancias
    }

    /**
     * Indica si el primer boundingbox contiene completamente al segundo.  Se crea el
     * método porque el contains del objeto no contempla los valores iguales.
     * @param bbox1 el primer boundingbox
     * @param bbox2 el boundingbox cotenido
     * @return true si lo contiene
     */
    public static boolean bboxContains(final BoundingBox bbox1, final BoundingBox bbox2) {
        if (!(bbox1.getLatNorth() + DIFF_BBOX >= bbox2.getLatNorth())) {
            AppLog.debug(TAG, "No contains getLatNorth");
        }
        if (!(bbox1.getLonWest() - DIFF_BBOX <= bbox2.getLonWest())) {
            AppLog.debug(TAG, "No contains getLonWest");
        }
        if (!(bbox1.getLatSouth() - DIFF_BBOX <= bbox2.getLatSouth())) {
            AppLog.debug(TAG, "No contains getLatSouth");
        }
        if (!(bbox1.getLonEast() + DIFF_BBOX >= bbox2.getLonEast())) {
            AppLog.debug(TAG, "No contains getLonEast");
        }
        return bbox1.getLatNorth() + DIFF_BBOX >= bbox2.getLatNorth()
                && bbox1.getLonWest() - DIFF_BBOX <= bbox2.getLonWest()
                && bbox1.getLatSouth() - DIFF_BBOX <= bbox2.getLatSouth()
                && bbox1.getLonEast() + DIFF_BBOX >= bbox2.getLonEast();
    }

    /**
     * Indica si los boundingbox se intersectan.
     * @param bbox1 el primer boundingbox
     * @param bbox2 el boundingbox cotenido
     * @return true si se intersectan
     */
    public static boolean bboxIntersects(final BoundingBox bbox1, final BoundingBox bbox2) {
        return bbox1.getLatNorth() >= bbox2.getLatSouth()
                && bbox1.getLonWest() <= bbox2.getLonEast()
                && bbox1.getLatSouth() <= bbox2.getLatNorth()
                && bbox1.getLonEast() >= bbox2.getLonWest();
    }

    /**
     * Copia el bbox ampliándolo de acuerdo a la política.
     * @param bbox el boundingbox
     * @return el BoundingBox ampliado
     */
    public static BoundingBox bboxAmpliado(final BoundingBox bbox) {
        double deltaLat = (bbox.getLatNorth() - bbox.getLatSouth()) * FACTOR_BBOX_AMPLIADO;
        double deltaLong = (bbox.getLonEast() - bbox.getLonWest()) * FACTOR_BBOX_AMPLIADO;
        BoundingBox ampliado = new BoundingBox(bbox.getLatNorth() + deltaLat,
                bbox.getLonEast() + deltaLong, bbox.getLatSouth() - deltaLat,
                bbox.getLonWest() - deltaLong);
        AppLog.debug(TAG, "BboxAmpliado Antes %s Ampliado %s", bbox, ampliado);
        return ampliado;
    }

    /**
     * Selecciona el boundingbox. Por problemas de animación de la librería, primero se establece
     * el centro y luego sí el bbox.
     * @param mapa el mapa
     * @param bbox el boundingbox
     */
    public static void seleccionarBBox(final MapView mapa, final BoundingBox bbox) {
        final GeoPoint center = new GeoPoint(bbox.getCenterLatitude(), bbox.getCenterLongitude());
        mapa.getController().setCenter(center);
        mapa.zoomToBoundingBox(bbox, false);
    }

    /**
     * Corrige URLS en el KML.
     * @param bytes la respuesta con el KML
     */
    public static byte[] corregirUrl(final byte[] bytes) {
        final String texto = new String(bytes, StandardCharsets.UTF_8);
        final String texto2 = texto.replace("http://icons.opengeo.org/markers/icon-poly.1.png",
                "http://mapas.mirecarga.co:8088/geoserver/www/images/m_ip-am_36x50.png");
        return texto2.getBytes(StandardCharsets.UTF_8);
    }
}
