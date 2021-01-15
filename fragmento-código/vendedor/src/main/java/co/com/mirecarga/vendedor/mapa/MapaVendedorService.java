package co.com.mirecarga.vendedor.mapa;

import org.osmdroid.views.MapView;

import co.com.mirecarga.core.mapa.ZonaMapa;

/**
 * Contrato para el servicio del mapa del vendedor.
 */
public interface MapaVendedorService {
    /**
     * Regresa el campo mapa.
     *
     * @return el valor de mapa
     */
    MapView getMapa();

    /**
     * Establece el valor del campo mapa.
     *
     * @param mapa el valor a establecer
     */
    void setMapa(MapView mapa);

    /**
     * Lee los datos de preferencias.
     * @return  las preferencias del usuario
     */
    PreferenciasMapaVendedor getPreferencias();

    /**
     * Guarda los datos de preferencias.
     * @param preferencias las preferencias del usuario
     */
    void guardarPreferencias(PreferenciasMapaVendedor preferencias);

    /**
     * Agrega al caché los datos de la zona para poder recuperarlos más adelante.
     * @param zonaMapa los datos de la zona
     */
    void agregarZona(ZonaMapa zonaMapa);

    /**
     * Busca los datos de la zona por el id.
     * @param idZona el id de la zona
     * @return los datos de la zona o null si no existe
     */
    ZonaMapa getZonaPorId(int idZona);

    /**
     * El objeto con la memoria de los datos seleccionados por el usuario.
     * @return el objeto
     */
    DatosMapaVendedor getDatosMapaVendedor();

    /**
     * Filtros aplicados al mapa.
     * @return el objeto con los filtros
     */
    FiltroMapa getFiltroMapa();
}
