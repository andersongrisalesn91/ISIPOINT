package co.com.mirecarga.cliente.mapa;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.cliente.R;
import co.com.mirecarga.core.mapa.InterpreteMapa;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.AppLog;

/**
 * Clase para mostrar los infowindow.
 */
public class InfoWindowZona extends InfoWindow {

    /**
     * El tag del log.
     */
    private static final String TAG = "InfoWindowZona";

    /**
     * La interfaz de los eventos del mapa del vendedor.
     */
    private EventosMapaCliente eventosMapaCliente;

    /**
     * Intérprete de datos contenidos en el mapa.
     */
    private InterpreteMapa parser = new InterpreteMapa();

    /**
     * Constructor.
     * @param mapView El mapa
     * @param eventosMapaCliente la interfaz de los eventos
     */
    public InfoWindowZona(final MapView mapView, final EventosMapaCliente eventosMapaCliente) {
        super(R.layout.burbuja_zona, mapView);
        this.eventosMapaCliente = eventosMapaCliente;
    }

    @Override
    public final void onOpen(final Object item) {
        final OverlayWithIW marker = (OverlayWithIW) item;
        final KmlPlacemark  kmlPlacemark = (KmlPlacemark) marker.getRelatedObject();
        final String description = kmlPlacemark.mDescription;
        final String id = parser.getValor(description, "id");
        AppLog.debug(TAG, "Selección %s id %s description %s", kmlPlacemark.mId, id, description);

        final ZonaMapa zona = new ZonaMapa();
        zona.setIdPais(Integer.parseInt(parser.getValor(description, "id_pais")));
        zona.setIdDepartamento(Integer.parseInt(parser.getValor(description, "id_departamento")));
        zona.setIdMunicipio(Integer.parseInt(parser.getValor(description, "id_municipio")));
        zona.setId(Integer.parseInt(id));
        zona.setNombre(parser.getValor(description, "zonas"));
        zona.setIdEstado(Integer.parseInt(parser.getValor(description, "id_estado")));
        zona.setPrepago(true);
        eventosMapaCliente.actualizarCeldas(zona, this, kmlPlacemark);
        //kmlPlacemark.mVisibility = false;
        //marker.setEnabled(false);
    }

    @Override
    public void onClose() {
    }
}
