package co.com.mirecarga.vendedor.mapa;

import android.graphics.Color;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.core.mapa.InterpreteMapa;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.R;

/**
 * Clase para mostrar los infowindow.
 */
public class InfoWindowCeldaOcupada extends InfoWindow {

    /**
     * El tag del log.
     */
    private static final String TAG = "InfoCeldaOcupada";

    /**
     * La interfaz de los eventos del mapa del vendedor.
     */
    private EventosMapaVendedor eventosMapaVendedor;

    /**
     * El id de la zona.
     */
    private int idZona;

    /**
     * El id de la celda.
     */
    private int idCelda;

    /**
     * El id del estado actual.
     */
    private int idEstado;

    /**
     * El polígono.
     */
    private Polygon polygon;

    /**
     * Constructor.
     * @param mapView El mapa
     * @param eventosMapaVendedor la interfaz de los eventos
     * @param kmlPlacemark los datos de información
     * @param polygon el polígono asociado
     */
    public InfoWindowCeldaOcupada(final MapView mapView, final EventosMapaVendedor eventosMapaVendedor,
                                  final KmlPlacemark kmlPlacemark, final Polygon polygon) {
        super(R.layout.burbuja_celda_ocupada, mapView);
        this.eventosMapaVendedor = eventosMapaVendedor;
        this.polygon = polygon;
        parse(kmlPlacemark);
    }

    private void parse(final KmlPlacemark kmlPlacemark) {
        final String description = kmlPlacemark.mDescription;
        final InterpreteMapa parser = new InterpreteMapa();
        final String id = parser.getValor(description, "id");
        // AppLog.debug(TAG, "Parse %s id %s description %s", kmlPlacemark.mId, id, description);
        idCelda = Integer.parseInt(id);
        idZona = Integer.parseInt(parser.getValor(description, "id_zona"));
        idEstado = Integer.parseInt(parser.getValor(description, "id_estado"));
    }

    @Override
    public final void onOpen(final Object item) {
        AppLog.debug(TAG, "Selección idCelda %s idZona %s idEstado %s", idCelda, idZona, idEstado);
        InfoWindow.closeAllInfoWindowsOn(mMapView);
        switch (idEstado) {
            case 5:
                eventosMapaVendedor.llenarInformacionCeldaOcupada(idZona, idCelda, mView);
                break;
            case 4:
            case 6:
                eventosMapaVendedor.iniciarVenta(idZona, idCelda);
                break;
            default:
                AppLog.debug(TAG, "La celda %s se encuentra en estado %s", idCelda, idEstado);
        }
    }

    @Override
    public void onClose() {
    }

    /**
     * Regresa el campo idZona.
     *
     * @return el valor de idZona
     */
    public int getIdZona() {
        return idZona;
    }

    /**
     * Regresa el campo idCelda.
     *
     * @return el valor de idCelda
     */
    public int getIdCelda() {
        return idCelda;
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
        if (polygon == null) {
            AppLog.debug(TAG, "MQTT Sin poligono en idZona %s idCelda %s idEstado %s",
                    idZona, idCelda, idEstado);
        } else {
            int antes = polygon.getFillColor();
            switch (idEstado) {
                case 5:
                    polygon.setFillColor(Color.parseColor("red"));
                    break;
                case 4:
                case 6:
                    polygon.setFillColor(Color.parseColor("green"));
                    break;
                default:
                    AppLog.debug(TAG, "La celda %s se encuentra en estado %s", idCelda, idEstado);
            }
            AppLog.debug(TAG, "MQTT Actualizando estado idZona %s idCelda %s idEstado %s FillColor Antes %s Después %s",
                    idZona, idCelda, idEstado, antes, polygon.getFillColor());
        }
    }
}
