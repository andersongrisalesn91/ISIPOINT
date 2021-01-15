package co.com.mirecarga.cliente.mapa;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.OnFirstLayoutListener;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnItemSelected;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiMapaClienteService;
import co.com.mirecarga.cliente.api.ApiZERService;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.cliente.ventamapa.VentaMapaFragment;
import co.com.mirecarga.cliente.ventamapa.VentaMapaService;
import co.com.mirecarga.core.api.DatosBoundingBox;
import co.com.mirecarga.core.api.Departamento;
import co.com.mirecarga.core.api.Municipio;
import co.com.mirecarga.core.api.TipoCelda;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.api.ZonaYParqueadero;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.mapa.ConfigMapa;
import co.com.mirecarga.core.mapa.MapaUtil;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.IOUtil;
import co.com.mirecarga.core.util.ListaSeleccion;
import io.reactivex.functions.Consumer;
import mil.nga.geopackage.projection.ProjectionFactory;
import mil.nga.geopackage.projection.ProjectionTransform;

/**
 * Fragmento con los datos de la página mapa del vendedor.
 */
public class MapaClienteFragment extends AbstractAppFragment implements LocationListener,
        MapEventsReceiver, EventosMapaCliente, OnFirstLayoutListener, MapListener {
    /**
     * El tag para el log.
     */
    private static final String TAG = "MapaClienteFragment";

    /**
     * Sistema de coordenadas usado por la librería local.
     */
    private static final long EPSG_LOCAL = 4326;

    /**
     * Sistema de coordenadas usado por el servidor.
     */
    private static final long EPSG_SERVER = 3857;

    /**
     * Control de página.
     */
    @BindView(R.id.map)
    transient MapView mapa;

    /**
     * Control de página.
     */
    @BindView(R.id.zona)
    transient Spinner zona;
    /**
     * Control de página.
     */
    @BindView(R.id.departamento)
    transient Spinner departamento;
    /**
     * Control de página.
     */
    @BindView(R.id.municipio)
    transient Spinner municipio;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<ZonaYParqueadero> listaZona;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Departamento> listaDepartamento;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Municipio> listaMunicipio;

    /**
     * Transformador de coordenadas hacia el servidor.
     */
    private final transient ProjectionTransform coordHaciaServer
            = ProjectionFactory.getProjection(EPSG_LOCAL).getTransformation(EPSG_SERVER);

    /**
     * Transformador de coordenadas provenientes del servidor.
     */
    private final transient ProjectionTransform coordDesdeServer
            = ProjectionFactory.getProjection(EPSG_SERVER).getTransformation(EPSG_LOCAL);

    /**
     * Boundingbox utilizado para consultar zonas.
     */
    private transient BoundingBox bboxZonas;

    /**
     * Boundingbox utilizado para consultar celdas.
     */
    private transient BoundingBox bboxCeldas;

    /**
     * Indica que está cargando el mapa por primera vez.
     */
    private transient boolean inicializando;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient MapaClienteService service;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiMapaClienteService api;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiZERService apiZERService;
    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigCliente> configService;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient VentaMapaService ventaMapaService;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    @Inject
    transient ExecutorService executor;
    /**
     * Manejador de formato de la aplicación.
     */
    @Inject
    protected transient AppFormatterService format;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_mapa_cliente;
    }

    /**
     * Número de overlay utilizado por la capa de zonas.
     */
    private int overlayZonas;

    /**
     * Número de overlay utilizado por la capa de celdas.
     */
    private int overlayCeldas;

    /**
     * Inicializa la página.
     */
    public MapaClienteFragment() {
        super();
        setPermitirFiltrar(true);
    }

    @Override
    protected final void consultarModelo() {
        inicializando = true;
        final ConfigMapa config = configService.getConfig().getMapa();
        final int idPaisDefecto = config.getIdPaisDefecto();
        service.setMapa(mapa);
        final MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        mapa.getOverlays().add(0, mapEventsOverlay);

        // Tile Source de mirecarga en lugar de TileSourceFactory.MAPNIK
        final ITileSource tileSource = new XYTileSource("MiRecarga",
                1, 22, 256, ".png",
                new String[]{
                        "http://a.tiles.igg.com.co/osm_tiles/",
                        "http://b.tiles.igg.com.co/osm_tiles/",
                        "http://c.tiles.igg.com.co/osm_tiles/",
                        "http://d.tiles.igg.com.co/osm_tiles/"},
                "Mi Recarga (c)");

        mapa.setTileSource(tileSource);
        //mapa.setTileSource(TileSourceFactory.MAPNIK);
        mapa.setBuiltInZoomControls(true);
        mapa.setMultiTouchControls(true);

        final PreferenciasMapaVendedor preferencias = service.getPreferencias();
        final GeoPoint center = preferencias.getCenter();
        AppLog.debug(TAG, "preferenciasMapa %s %s", preferencias.getZoomLevel(), center);
        mapa.getController().setZoom(preferencias.getZoomLevel());
        if (center != null) {
            mapa.getController().setCenter(center);
        }
        mapa.setMaxZoomLevel(config.getMaxZoomLevel());
        /* Se solicitó desactivar GPS temporal
        final MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(
                new GpsMyLocationProvider(getActivity().getApplicationContext()), mapa);
        mapa.getOverlays().add(mLocationOverlay);
        mLocationOverlay.enableFollowLocation();
        mLocationOverlay.enableMyLocation();
        */
        mapa.setTilesScaledToDpi(true);
        mapa.addOnFirstLayoutListener(this);
        mapa.addMapListener(this);

        final DatosMapaVendedor datosMapaVendedor = service.getDatosMapaVendedor();
        subscribe(apiZERService.getDepartamentos(idPaisDefecto), list -> {
            listaDepartamento = ListaSeleccion.iniciar(departamento, list,
                    getString(R.string.seleccione_departamento), Departamento::getDepartamento);
            if (datosMapaVendedor.getDepartamento() == null) {
                // Busca el departamento actual a partir del bbox
                seleccionarDepartamentoBbox();
                inicializando = false;
            } else {
                listaDepartamento.setTextoSeleccionado(departamento,
                        datosMapaVendedor.getDepartamento().getDepartamento());
            }
        });
    }

    /**
     * Selecciona el departamento a partir del Boundingbox.
     */
    private void seleccionarDepartamentoBbox() {
        final BoundingBox bbox = mapa.getBoundingBox();
        for (Departamento depto : listaDepartamento.getItems()) {
            final BoundingBox bboxDepto = getBoundingBoxPorItem(depto);
            if (MapaUtil.bboxIntersects(bboxDepto, bbox)) {
                listaDepartamento.setTextoSeleccionado(departamento, depto.getDepartamento());
                break;
            }
        }
    }

    /**
     * Selecciona el municipio a partir del Boundingbox.
     */
    private boolean isSeleccionarMunicipioBBox() {
        final BoundingBox bbox = mapa.getBoundingBox();
        boolean seleccionado = false;
        for (Municipio muni : listaMunicipio.getItems()) {
            final BoundingBox bboxMuni = getBoundingBoxPorItem(muni);
            if (MapaUtil.bboxIntersects(bboxMuni, bbox)) {
                listaMunicipio.setTextoSeleccionado(municipio, muni.getMunicipio());
                seleccionado = true;
                break;
            }
        }
        return seleccionado;
    }

    /**
     * Almacena el zoom actual del usuario.
     */
    private void guardarZoomActual() {
        final IGeoPoint mapCenter = mapa.getMapCenter();
        final GeoPoint geoPoint = new GeoPoint(mapCenter.getLatitude(), mapCenter.getLongitude());
        service.guardarPreferencias(new PreferenciasMapaVendedor(mapa.getZoomLevelDouble(), geoPoint));
    }

    @Override
    public void onPause() {
        super.onPause();
        guardarZoomActual();
    }

    /**
     * Actualiza el control de municipios según el departamento seleccionado.
     */
    @OnItemSelected(R.id.departamento)
    public void onDepartamentoSeleccionado() {
        final ConfigMapa config = configService.getConfig().getMapa();
        final int idPais = config.getIdPaisDefecto();
        final Departamento depto = listaDepartamento.getItem(departamento);
        final DatosMapaVendedor datosMapaVendedor = service.getDatosMapaVendedor();
        datosMapaVendedor.setDepartamento(depto);
        if (depto != null) {
            final Integer idDepartamento = depto.getId();
            if (isSesionActiva()) {
                subscribe(apiZERService.getMunicipios(idPais, idDepartamento), list -> {
                    listaMunicipio = ListaSeleccion.iniciar(municipio, list,
                            getString(R.string.seleccione_municipio), Municipio::getMunicipio);
                    Municipio muni = datosMapaVendedor.getMunicipio();
                    if (muni == null || !listaMunicipio.contiene(muni.getMunicipio())) {
                        // Busca el municipio actual a partir del bbox
                        if (!isSeleccionarMunicipioBBox() && !inicializando) {
                            seleccionarBoundingBox(depto);
                        }
                        inicializando = false;
                    } else {
                        listaMunicipio.setTextoSeleccionado(this.municipio, muni.getMunicipio());
                    }
                });
            }
        } else {
            municipio.setAdapter(null);
            inicializando = false;
        }
    }

    /**
     * Actualiza el control de municipios según el departamento seleccionado.
     */
    @OnItemSelected(R.id.municipio)
    public void onMunicipioSeleccionado() {
        final ConfigMapa config = configService.getConfig().getMapa();
        final int idPais = config.getIdPaisDefecto();
        final Departamento depto = listaDepartamento.getItem(departamento);
        final Municipio muni = listaMunicipio.getItem(municipio);
        final DatosMapaVendedor datosMapaVendedor = service.getDatosMapaVendedor();
        datosMapaVendedor.setMunicipio(muni);
        if (depto != null && muni != null) {
            final Integer idDepartamento = depto.getId();
            final Integer idMunicipio = muni.getId();
            if (isSesionActiva()) {
                subscribe(apiZERService.getZonas(idPais, idDepartamento, idMunicipio), list -> {
                    agregarCacheZonas(list);
                    listaZona = ListaSeleccion.iniciar(zona, list,
                            getString(R.string.seleccione_zona), ZonaYParqueadero::getParqueaderoyzona);
                    final ZonaYParqueadero zonaYParqueadero = datosMapaVendedor.getZonaYParqueadero();
                    if (zonaYParqueadero == null
                            || !listaZona.contiene(zonaYParqueadero.getParqueaderoyzona())) {
                        if (!inicializando) {
                            seleccionarBoundingBox(muni);
                        }
                        inicializando = false;
                    } else {
                        listaZona.setTextoSeleccionado(zona,
                                zonaYParqueadero.getParqueaderoyzona());
                    }
                });
            }
        } else {
            inicializando = false;
            zona.setAdapter(null);
        }
    }

    /**
     * Actualiza el zoom a la zona.
     */
    @OnItemSelected(R.id.zona)
    public void onZonaSeleccionada() {
        final ZonaYParqueadero item = listaZona.getItem(zona);
        final DatosMapaVendedor datosMapaVendedor = service.getDatosMapaVendedor();
        datosMapaVendedor.setZonaYParqueadero(item);
        if (item != null) {
            if (!inicializando) {
                moverBboxZona(item.getId());
            }
            inicializando = false;
            //No llama a consultarZonasCercanas(); porque onScroll se genera
        } else {
            inicializando = false;
        }
    }

    /**
     * Calcula el boundingbox con los datos del item.
     *
     * @param item el item con los datos
     * @return el boundingbox calculado
     */
    private BoundingBox getBoundingBoxPorItem(final DatosBoundingBox item) {
        final double[] min = coordDesdeServer.transform(item.getBboxMinX(), item.getBboxMinY());
        final double[] max = coordDesdeServer.transform(item.getBboxMaxX(), item.getBboxMaxY());
        return new BoundingBox(max[1], max[0], min[1], min[0]);
    }

    /**
     * Selecciona el elemento con información de boundingbox.
     *
     * @param item el item con los datos
     * @return el bbox del item
     */
    private BoundingBox seleccionarBoundingBox(final DatosBoundingBox item) {
        final BoundingBox bbox = getBoundingBoxPorItem(item);
        AppLog.debug(TAG, "Seleccionar BBox2 %s min %s %s max %s %s", bbox,
                item.getBboxMinX(), item.getBboxMinY(), item.getBboxMaxX(), item.getBboxMaxY());
        runOnUiThread(() -> MapaUtil.seleccionarBBox(mapa, bbox));
        return bbox;
    }

    /**
     * Obtiene la cadena del bounding box.
     *
     * @param bBox el bounding box.
     * @return la cadena
     */
    private String obtenerCadenaBoundingBox(final BoundingBox bBox) {
        String cadena = "";
        final double[] min = coordHaciaServer.transform(bBox.getLonWest(), bBox.getLatSouth());
        final double[] max = coordHaciaServer.transform(bBox.getLonEast(), bBox.getLatNorth());
        cadena = String.format(Locale.ENGLISH, "%f,%f,%f,%f", min[0], min[1], max[0], max[1]);
        // TODO convertir BB
        AppLog.debug(TAG, "obtenerCadenaBoundingBox %s bBox %s", cadena, bBox);
        return cadena;
    }

    /**
     * Muestra el KML de las celdas.
     *
     * @param bytes la respuesta con el KML
     */
    private void mostrarKmlZonas(final byte[] bytes) {
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            final KmlDocument kmlDocument = new KmlDocument();
            kmlDocument.parseKMLStream(inputStream, null);
            final KmlFeature.Styler styler = new CustomTapPolygon(this, kmlDocument, mapa);
            final FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapa, null, styler, kmlDocument);
            final List<Overlay> overlays = mapa.getOverlays();
            if (overlayZonas == 0 || overlays.size() <= overlayZonas) {
                overlayZonas = overlays.size();
                overlays.add(kmlOverlay);
            } else {
                // Remplaza el overlay de zonas
                overlays.set(overlayZonas, kmlOverlay);
            }
            getActivity().runOnUiThread(() -> mapa.invalidate());
        } catch (final Exception ex) {
            AppLog.error(TAG, ex, "Error al obtener las zonas");
        } finally {
            finalizarProcesando();
        }
    }

    /**
     * Consulta las zonas cercanas.
     */
    private void consultarZonasCercanas() {
        final BoundingBox bbox = mapa.getProjection().getBoundingBox();
        // Únicamente consulta si el boundingbox no está contenido dentro del mapa actual
        if (bboxZonas == null || !MapaUtil.bboxContains(bboxZonas, bbox)) {
            AppLog.debug(TAG, "consultageo ZonasCercanas bbox %s antes %s zoom %s", bbox, bboxZonas, mapa.getZoomLevelDouble());
            bboxZonas = MapaUtil.bboxAmpliado(bbox);
            subscribe(api.consultarZonasCercanasV2(mapa.getMeasuredWidth(), mapa.getMeasuredHeight(),
                    obtenerCadenaBoundingBox(bboxZonas)),
                    resp -> {
                        mostrarProcesando();
                        try (InputStream inputStream = Objects.requireNonNull(resp.body()).byteStream()) {
                            final byte[] bytes = IOUtil.toByteArray(inputStream);
                            executor.submit(() -> mostrarKmlZonas(bytes));
                        }
                    }
            );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppLog.debug(TAG, "onResume");
        //consultarZonasCercanas();
    }

    @Override
    public void onLocationChanged(final Location location) {
        AppLog.debug(TAG, "onLocationChanged %s", location);
    }

    @Override
    public void onStatusChanged(final String s, final int i, final Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(final String s) {

    }

    @Override
    public void onProviderDisabled(final String s) {

    }

    @Override
    public boolean singleTapConfirmedHelper(final GeoPoint p) {
        InfoWindow.closeAllInfoWindowsOn(mapa);
        return true;
    }

    @Override
    public boolean longPressHelper(final GeoPoint p) {
        return false;
    }

    /**
     * Actualiza las celdas del boundingbox actual.
     */
    private void actualizarCeldasTodas() {
        final BoundingBox bbox = mapa.getProjection().getBoundingBox();
        // Únicamente consulta si el boundingbox no está contenido dentro del mapa actual
        if (bboxCeldas == null || !MapaUtil.bboxContains(bboxCeldas, bbox)) {
            bboxCeldas = MapaUtil.bboxAmpliado(bbox);
            AppLog.debug(TAG, "consultageo todas las celdas bboxCeldas %s", bboxCeldas);
            final String filtroCql = service.getFiltroMapa().getFiltroCql();
            subscribe(api.consultarCeldasTodasV2(mapa.getMeasuredWidth(), mapa.getMeasuredHeight(),
                    obtenerCadenaBoundingBox(bboxCeldas), filtroCql),
                    resp -> {
                        mostrarProcesando();
                        try (InputStream inputStream = Objects.requireNonNull(resp.body()).byteStream()) {
                            final byte[] bytes = IOUtil.toByteArray(inputStream);
                            executor.submit(() -> mostrarKmlCeldas(bytes));
                        }
                    }
            );
        } else {
            AppLog.debug(TAG, "consultageo No actualiza porque bboxCeldas %s contiene %s", bboxCeldas, bbox);
        }
    }

    /**
     * Muestra el KML de las celdas.
     *
     * @param bytes la respuesta con el KML
     */
    private void mostrarKmlCeldas(final byte[] bytes) {
        try (InputStream inputStream = new ByteArrayInputStream(MapaUtil.corregirUrl(bytes))) {
            final KmlDocument kmlDocument = new KmlDocument();
            kmlDocument.parseKMLStream(inputStream, null);
            final KmlFeature.Styler styler = new CustomTapPolygon(this, kmlDocument, mapa);
            final FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapa, null, styler, kmlDocument);
            final List<Overlay> overlays = mapa.getOverlays();
            if (overlayCeldas == 0 || overlays.size() <= overlayCeldas) {
                overlayCeldas = overlays.size();
                overlays.add(kmlOverlay);
            } else {
                // Remplaza el overlay de celdas
                overlays.set(overlayCeldas, kmlOverlay);
            }
            AppLog.debug(TAG, "Overlays actualizarCeldas %s overlayCeldas %s", overlays.size(), overlayCeldas);
            getActivity().runOnUiThread(() -> InfoWindow.closeAllInfoWindowsOn(mapa));
            getActivity().runOnUiThread(() -> mapa.invalidate());
        } catch (final Exception ex) {
            AppLog.error(TAG, ex, "Error inesperado al actualizar celdas");
        } finally {
            finalizarProcesando();
        }
    }

    /**
     * Actualiza las celdas de la zona indicada.
     *
     * @param idZona el id de la zona
     * @param action la acción a ejecutar una vez finalice la actualización
     */
    private void actualizarCeldasPorIdZona(final int idZona, final Runnable action) {
        if (action != null) {
            getActivity().runOnUiThread(action);
        }
        moverBboxZona(idZona);
    }

    /**
     * Mueve el enfoque a la zona.
     *
     * @param idZona el id de la zona
     */
    private void moverBboxZona(final int idZona) {
        final ZonaMapa zonaMapa = service.getZonaPorId(idZona);
        consultarDatosZonas(zonaMapa, zonaMapa2 -> {
            final ZonaYParqueadero zonaYParqueadero = zonaMapa2.getZonaYParqueadero();
            seleccionarBoundingBox(zonaYParqueadero);
        });
    }

    /**
     * Asegura que se hayan consultado los datos de la zona para llamar al método.
     *
     * @param zonaMapa los datos mínimos de la zona
     * @param consumer el método que requiere las zonas actualizadas
     */
    private void consultarDatosZonas(final ZonaMapa zonaMapa, final Consumer<ZonaMapa> consumer) {
        if (zonaMapa.getZonaYParqueadero() == null) {
            // Debe consultar la información de la zona
            if (isSesionActiva()) {
                subscribe(apiZERService.getZonas(zonaMapa.getIdPais(),
                        zonaMapa.getIdDepartamento(),
                        zonaMapa.getIdMunicipio()), list -> {
                    agregarCacheZonas(list);
                    final ZonaMapa zonaMapa2 = service.getZonaPorId(zonaMapa.getId());
                    consumer.accept(zonaMapa2);
                });
            }
        } else {
            try {
                consumer.accept(zonaMapa);
            } catch (final Exception ex) {
                AppLog.error(TAG, ex, "Error inesperado al consultarDatosZonas");
            }
        }
    }

    @Override
    public void actualizarCeldas(final ZonaMapa zonaMapa, final InfoWindow infoWindow, final KmlPlacemark kmlPlacemark) {
        service.agregarZona(zonaMapa);
        // Obliga a la actualización
        bboxCeldas = null;
        actualizarCeldasPorIdZona(zonaMapa.getId(), infoWindow::close);
    }

    @Override
    public void iniciarVenta(final int idZona, final int idCelda) {
        final ZonaMapa zonaMapa = service.getZonaPorId(idZona);
        if (zonaMapa == null) {
            mostrarMensaje("No existe la zona %s", idZona);
        } else {
            abrirVenta(zonaMapa, idCelda, false);
        }
    }

    /**
     * Abre la página para la venta.
     *
     * @param zonaMapa      la zona actual
     * @param idCelda       el id de la celda
     * @param celdaMultiple indica si se trata de una celda múltiple
     */
    private void abrirVenta(final ZonaMapa zonaMapa, final int idCelda,
                            final boolean celdaMultiple) {
        ventaMapaService.setZonaSeleccionada(zonaMapa);
        ventaMapaService.setIdCeldaSeleccionada(idCelda);
        ventaMapaService.setCeldaMultiple(celdaMultiple);
        guardarZoomActual();
        // Obliga a repintar al regresar
        bboxCeldas = null;
        getNavegador().navegar(new VentaMapaFragment());
    }

    @Override
    public void iniciarVentaMultiple(final int idZona, final int idCelda) {
        final ZonaMapa zonaMapa = service.getZonaPorId(idZona);
        if (zonaMapa == null) {
            mostrarMensaje("No existe la zona %s", idZona);
        } else {
            final List<TipoVehiculo> tiposVehiculo = zonaMapa.getTiposVehiculoCeldaMultiple();
            consultarDatosZonas(zonaMapa, zonaMapa2 -> abrirVenta(zonaMapa2, idCelda, true));
        }
    }

    /**
     * Actualiza la información de zonas en caché.
     *
     * @param list la lista de datos que regresó el servicio
     */
    private void agregarCacheZonas(final List<ZonaYParqueadero> list) {
        for (final ZonaYParqueadero parq : list) {
            final ZonaMapa cache = service.getZonaPorId(parq.getId());
            final ZonaMapa reg;
            if (cache == null) {
                reg = new ZonaMapa();
                reg.setIdPais(parq.getId());
                reg.setIdDepartamento(parq.getIddepartamento());
                reg.setIdMunicipio(parq.getIdmunicipio());
                reg.setId(parq.getId());
                reg.setNombre(parq.getParqueaderoyzona());
                reg.setIdEstado(parq.getIdestado());
            } else {
                reg = cache;
            }
            reg.setZonaYParqueadero(parq);
            reg.setPrepago(parq.isEsprepago());
            if (parq.getTiposdeceldas() != null) {
                for (final TipoCelda tipoCelda : parq.getTiposdeceldas()) {
                    if (tipoCelda.getIdtipodecelda() == InfoWindowCeldaMultiple.ID_TIPO_CELDA_MULTIPLE) {
                        reg.setTiposVehiculoCeldaMultiple(tipoCelda.getTiposdevehiculos());
                    }
                }
            }
            if (cache == null) {
                service.agregarZona(reg);
            }
        }
    }

    @Override
    public ConfigService<ConfigCliente> getConfigService() {
        return configService;
    }

    @Override
    public void setConfigService(final ConfigService<ConfigCliente> configService) {
        this.configService = configService;
    }

    @Override
    public void runOnUiThread(final Runnable action) {
        getActivity().runOnUiThread(action);
    }

    @Override
    public void onFirstLayout(final View v, final int left, final int top, final int right, final int bottom) {
        AppLog.debug(TAG, "onFirstLayout actualizarMapa");
        bboxCeldas = null;
        bboxZonas = null;
        actualizarMapa();
    }

    @Override
    public boolean onScroll(final ScrollEvent event) {
        if (!mapa.isAnimating() && (bboxZonas != null || bboxCeldas != null)) {
            AppLog.debug(TAG, "onScroll consultarZonasCercanas %s", event);
            actualizarMapa();
        }
        return true;
    }

    @Override
    public boolean onZoom(final ZoomEvent event) {
        final double zoomLevel = event.getZoomLevel();
        AppLog.debug(TAG, "onZoom animando %s event %s mapa %s", mapa.isAnimating(), zoomLevel, mapa.getZoomLevelDouble());
        if (zoomLevel == mapa.getZoomLevelDouble()) {
            actualizarMapa();
        }
        return true;
    }

    /**
     * Actualiza el mapa de acuerdo al zoom actual.
     */
    private void actualizarMapa() {
        final double zoomLevel = mapa.getZoomLevelDouble();
        AppLog.debug(TAG, "onZoom procesando %s", zoomLevel);
        final ConfigMapa config = configService.getConfig().getMapa();
        final List<Overlay> overlays = mapa.getOverlays();
        if (zoomLevel < config.getMinZoomLevelCeldas()) {
            if (overlayCeldas > 0 && overlays.size() > overlayCeldas) {
                AppLog.debug(TAG, "onZoom Eliminando layer de celdas en zoom %s min %s overlay %s",
                        zoomLevel, config.getMinZoomLevelCeldas(), overlayCeldas);
                overlays.remove(overlayCeldas);
                overlayCeldas = 0;
                bboxCeldas = null;
                final DatosMapaVendedor datosMapaVendedor = service.getDatosMapaVendedor();
                datosMapaVendedor.setIdZonaMapa(null);
            }
            consultarZonasCercanas();
        } else {
            if (overlayZonas > 0 && overlays.size() > overlayZonas) {
                AppLog.debug(TAG, "onZoom Eliminando layer de zonas en zoom %s min %s overlay %s",
                        zoomLevel, config.getMinZoomLevelCeldas(), overlayZonas);
                overlays.remove(overlayZonas);
                overlayZonas = 0;
                bboxZonas = null;
                final DatosMapaVendedor datosMapaVendedor = service.getDatosMapaVendedor();
                datosMapaVendedor.setIdZonaMapa(null);
            }
            actualizarCeldasTodas();
        }
    }
}
