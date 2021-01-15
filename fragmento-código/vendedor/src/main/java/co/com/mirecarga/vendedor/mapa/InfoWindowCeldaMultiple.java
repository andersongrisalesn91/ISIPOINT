package co.com.mirecarga.vendedor.mapa;

import android.widget.Button;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.core.mapa.InterpreteMapa;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.R;

/**
 * Clase para mostrar los infowindow.
 */
public class InfoWindowCeldaMultiple extends InfoWindow {

    /**
     * El tag del log.
     */
    private static final String TAG = "InfoCeldaMultiple";

    /**
     * Id de tipos celdas múltiples.
     */
    public static final int ID_TIPO_CELDA_MULTIPLE = 5;

    /**
     * La interfaz de los eventos del mapa del vendedor.
     */
    private EventosMapaVendedor eventosMapaVendedor;

    /**
     * Intérprete de datos contenidos en el mapa.
     */
    private final InterpreteMapa parser = new InterpreteMapa();

    /**
     * Id de la zona.
     */
    private int idZona;

    /**
     * Id de la celda.
     */
    private int idCelda;

    /**
     * Constructor.
     * @param mapView El mapa
     * @param eventosMapaVendedor la interfaz de los eventos
     */
    public InfoWindowCeldaMultiple(final MapView mapView, final EventosMapaVendedor eventosMapaVendedor) {
        super(R.layout.burbuja_celda_multiple, mapView);
        this.eventosMapaVendedor = eventosMapaVendedor;
        final Button vender = mView.findViewById(R.id.vender);
        final Button salir = mView.findViewById(R.id.salir);
        final Button historico = mView.findViewById(R.id.historico);
        vender.setOnClickListener(view -> venderCelda());
        salir.setOnClickListener(view ->
                eventosMapaVendedor.mostrarHistorial(idZona, idCelda, true));
        historico.setOnClickListener(view ->
                eventosMapaVendedor.mostrarHistorial(idZona, idCelda, false));
    }

    /**
     * Dirige a la funcionalidad de venta.
     */
    private void venderCelda() {
        eventosMapaVendedor.iniciarVentaMultiple(idZona, idCelda);
    }

    @Override
    public final void onOpen(final Object item) {
        final OverlayWithIW marker = (OverlayWithIW) item;
        final KmlPlacemark  kmlPlacemark = (KmlPlacemark) marker.getRelatedObject();
        final String description = kmlPlacemark.mDescription;
        final String id = parser.getValor(description, "id");
        AppLog.debug(TAG, "Selección %s id %s description %s", kmlPlacemark.mId, id, description);
        // Únicamente se ejecuta el código si previamente no era visible porque ejecutaba al repintar
        idCelda = Integer.parseInt(id);
        idZona = Integer.parseInt(parser.getValor(description, "id_zona"));
        final int idEstado = Integer.parseInt(parser.getValor(description, "id_estado"));
        AppLog.debug(TAG, "Selección idCelda %s idZona %s idEstado %s", idCelda, idZona, idEstado);
        InfoWindow.closeAllInfoWindowsOn(mMapView);
    }

    @Override
    public void onClose() {
    }
}
