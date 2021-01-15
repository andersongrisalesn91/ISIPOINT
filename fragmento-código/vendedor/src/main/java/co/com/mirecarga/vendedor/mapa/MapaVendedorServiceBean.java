package co.com.mirecarga.vendedor.mapa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.SparseArray;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.mapa.ConfigMapa;
import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Servicio para el contrato del mapa del vendedor.
 */
@Singleton
public class MapaVendedorServiceBean implements MapaVendedorService {
    /**
     * El tag para el log.
     */
    private static final String TAG = "MapaVendedorServiceBean";

    /**
     * Los últimos datos de confirmación.
     */
    private MapView mapa;

    /**
     * El contexto de la aplicación.
     */
    private Context context;

    /**
     * El índice interno de las zonas identificadas.
     */
    private SparseArray<ZonaMapa> indiceZonas = new SparseArray<>();

    /**
     * Configuración del mapa.
     */
    private ConfigMapa configMapa;

    /**
     * El objeto con la memoria de los datos seleccionados por el usuario.
     */
    private final DatosMapaVendedor datosMapaVendedor = new DatosMapaVendedor();

    /**
     * Las opciones de filtro del mapa.
     */
    private final transient FiltroMapa filtroMapa = new FiltroMapa();

    /**
     * Constructor con las propiedades.
     * @param context El contexto de la aplicación
     * @param  configService el Servicio de configuración
     */
    @Inject
    public MapaVendedorServiceBean(final Context context, final ConfigService<ConfigVendedor> configService) {
        this.context = context;
        this.configMapa = configService.getConfig().getMapa();
    }

    @Override
    public final MapView getMapa() {
        return mapa;
    }

    @Override
    public final void setMapa(final MapView mapa) {
        this.mapa = mapa;
    }

    @Override
    public final PreferenciasMapaVendedor getPreferencias() {
        final SharedPreferences store = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE);
        double zoomLevel;
        try {
            final String zoomLevelTexto = store.getString("zoomLevel", String.valueOf(configMapa.getDefaultZoomLevel()));
            zoomLevel = Double.valueOf(zoomLevelTexto);
        } catch (NumberFormatException | ClassCastException e) {
            AppLog.error(TAG, e, "Error al leer zoomLevel");
            zoomLevel = configMapa.getDefaultZoomLevel();
        }
        final String centerTexto = store.getString("center", "");
        final GeoPoint center;
        if (TextUtils.isEmpty(centerTexto)) {
            center = null;
        } else {
            center = GeoPoint.fromDoubleString(centerTexto, ',');
        }
        return new PreferenciasMapaVendedor(zoomLevel, center);
    }

    @Override
    public final void guardarPreferencias(final PreferenciasMapaVendedor preferencias) {
        final SharedPreferences store = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE);
        final Editor edit = store.edit();
        edit.putString("zoomLevel", String.valueOf(preferencias.getZoomLevel()));
        if (preferencias.getCenter() != null) {
            edit.putString("center", preferencias.getCenter().toDoubleString());
        }
        edit.apply();
    }

    @Override
    public final void agregarZona(final ZonaMapa zonaMapa) {
        indiceZonas.put(zonaMapa.getId(), zonaMapa);
    }

    @Override
    public final ZonaMapa getZonaPorId(final int idZona) {
        return indiceZonas.get(idZona);
    }

    @Override
    public final DatosMapaVendedor getDatosMapaVendedor() {
        return datosMapaVendedor;
    }

    @Override
    public final FiltroMapa getFiltroMapa() {
        return filtroMapa;
    }
}
