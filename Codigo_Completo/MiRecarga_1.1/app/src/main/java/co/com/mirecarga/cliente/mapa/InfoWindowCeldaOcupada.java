package co.com.mirecarga.cliente.mapa;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.cliente.R;
import co.com.mirecarga.core.mapa.InterpreteMapa;
import co.com.mirecarga.core.util.AppLog;

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
    private EventosMapaCliente eventosMapaCliente;

    /**
     * Intérprete de datos contenidos en el mapa.
     */
    private final InterpreteMapa parser = new InterpreteMapa();

    /**
     * Constructor.
     * @param mapView El mapa
     * @param eventosMapaCliente la interfaz de los eventos
     */
    public InfoWindowCeldaOcupada(final MapView mapView, final EventosMapaCliente eventosMapaCliente) {
        super(R.layout.burbuja_celda_ocupada, mapView);
        this.eventosMapaCliente = eventosMapaCliente;
    }

    @Override
    public final void onOpen(final Object item) {
        final OverlayWithIW marker = (OverlayWithIW) item;
        final KmlPlacemark  kmlPlacemark = (KmlPlacemark) marker.getRelatedObject();
        final String description = kmlPlacemark.mDescription;
        final String id = parser.getValor(description, "id");
        AppLog.debug(TAG, "Selección %s id %s description %s", kmlPlacemark.mId, id, description);
        // Únicamente se ejecuta el código si previamente no era visible porque ejecutaba al repintar
        final int idCelda = Integer.parseInt(id);
        final int idZona = Integer.parseInt(parser.getValor(description, "id_zona"));
        final int idEstado = Integer.parseInt(parser.getValor(description, "id_estado"));
        AppLog.debug(TAG, "Selección idCelda %s idZona %s idEstado %s", idCelda, idZona, idEstado);
        InfoWindow.closeAllInfoWindowsOn(mMapView);
        switch (idEstado) {
            case 5:
                AppLog.debug(TAG, "La celda %s se encuentra ocupada", idCelda);
                break;
            case 4:
            case 6:
                eventosMapaCliente.iniciarVenta(idZona, idCelda);
                break;
            default:
                AppLog.debug(TAG, "La celda %s se encuentra en estado %s", idCelda, idEstado);
        }
    }

    @Override
    public void onClose() {
    }
}
