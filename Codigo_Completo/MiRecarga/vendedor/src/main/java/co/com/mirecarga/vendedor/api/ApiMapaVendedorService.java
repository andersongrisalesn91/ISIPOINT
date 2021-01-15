package co.com.mirecarga.vendedor.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Contrato del servicio de acceso al API del vendedor.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ApiMapaVendedorService {

    /**
     * Consulta kml zonas aledañas a la posición.
     * @param width el ancho de la imagen
     * @param height el alto de la imagen
     * @param viewparams el filtro por vendedor
     * @param bbox el bounding box
     * @return la respuesta del servidor
     */
    @Streaming
    @GET("wms?service=WMS&version=1.1.1&request=GetMap&layers=mr_test:vendedores_parqueaderos_y_zonas_poi"
            + "&styles=&format=application%2Fvnd.google-earth.kmz%2Bxml&srs=EPSG:3857")
    Observable<Response<ResponseBody>> consultarZonasCercanasV2(@Query("width") int width,
                                                                @Query("height") int height,
                                                                @Query("viewparams") String viewparams,
                                                                @Query("bbox") String bbox);

    /**
     * Consulta kml zonas aledañas a la posición.
     * @param width el ancho de la imagen
     * @param height el alto de la imagen
     * @param viewparams el filtro por vendedor
     * @param bbox el bounding box
     * @param idZona el identificador de la zona
     * @param filtro el filtro adicional para el mapa
     * @return la respuesta del servidor
     */
    @Streaming
    @GET("wms?service=WMS&version=1.1.1&request=GetMap&layers=mr_test:vendedores_celdas"
            + "&styles=&format=application%2Fvnd.google-earth.kmz%2Bxml&srs=EPSG:3857")
    Observable<Response<ResponseBody>> consultarCeldasPorIdZonaV2(@Query("width") int width,
                                                                  @Query("height") int height,
                                                                  @Query("viewparams") String viewparams,
                                                                  @Query("bbox") String bbox,
                                                                  @Query("idZona") int idZona,
                                                                  @Query("CQL_FILTER") String filtro);

    /**
     * Consulta kml zonas aledañas a la posición.
     * @param width el ancho de la imagen
     * @param height el alto de la imagen
     * @param viewparams el filtro por vendedor
     * @param bbox el bounding box
     * @param filtro el filtro adicional para el mapa
     * @return la respuesta del servidor
     */
    @Streaming
    @GET("wms?service=WMS&version=1.1.1&request=GetMap&layers=mr_test:vendedores_celdas"
            + "&styles=&format=application%2Fvnd.google-earth.kmz%2Bxml&srs=EPSG:3857")
    Observable<Response<ResponseBody>> consultarCeldasTodasV2(@Query("width") int width,
                                                              @Query("height") int height,
                                                              @Query("viewparams") String viewparams,
                                                                  @Query("bbox") String bbox,
                                                                  @Query("CQL_FILTER") String filtro);

    /**
     * Consulta geopackage zonas aledañas a la posición.
     * @param width el ancho de la imagen
     * @param height el alto de la imagen
     * @param viewparams el filtro por vendedor
     * @param bbox el bounding box
     * @param filtro el filtro adicional para el mapa
     * @return la respuesta del servidor
     */
    @Streaming
    @GET("ows?service=WFS&version=1.1.1&request=GetFeature&typeName=mr_test%3Avendedores_celdas"
            + "&maxFeatures=50&outputFormat=application%2Fx-gpkg")
    Observable<Response<ResponseBody>> consultarCeldasTodasV2Geopackage(@Query("width") int width,
                                                              @Query("height") int height,
                                                              @Query("viewparams") String viewparams,
                                                              @Query("bbox") String bbox,
                                                              @Query("CQL_FILTER") String filtro);
}
