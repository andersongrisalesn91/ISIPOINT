package co.com.mirecarga.cliente.api;

import java.util.List;
import java.util.Map;

import co.com.mirecarga.core.api.Departamento;
import co.com.mirecarga.core.api.EstadoCelda;
import co.com.mirecarga.core.api.Municipio;
import co.com.mirecarga.core.api.Tarifa;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.api.ZonaYParqueadero;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Contrato del servicio de acceso al API del ZER.
 */

public interface ApiZERService {
    /**
     * Consulta las tarifas por celda.
     * @param idEntidad el id del cliente
     * @param oauth2Token el token oauth2
     * @param ip la ip del cliente
     * @param urlOK la URL al finalizar
     * @param urlError la URL en caso de error
     * @return la respuesta del servidor
     */
    @FormUrlEncoded
    @POST("pagos/cash/generarpin")
    Observable<RespuestaGenerarPin> generarPin(
            @Field("idEntidad") int idEntidad,
            @Field("oauth2Token") String oauth2Token,
            @Field("ip") String ip,
            @Field("urlOK") String urlOK,
            @Field("urlError") String urlError
    );

    /**
     * Consulta los estado.
     * @return la respuesta del servidor
     */
    @GET("estados")
    Observable<List<EstadoCelda>> getEstadosCelda();

    /**
     * Consulta los estado.
     * @return la respuesta del servidor
     */
    @GET("tiposdeceldas")
    Observable<List<TipoVehiculo>> getTiposCelda();

    /**
     * Consulta los departamentos.
     * @param idPais el identificador del país
     * @return la respuesta del servidor
     */
    @GET("paises/{idPais}/departamentos")
    Observable<List<Departamento>> getDepartamentos(@Path("idPais") int idPais);
    /**
     * Consulta los departamentos.
     * @param idPais el identificador del país
     * @param idDepartamento el identificador del departamento
     * @return la respuesta del servidor
     */
    @GET("paises/{idPais}/departamentos/{idDepartamento}/municipios")
    Observable<List<Municipio>> getMunicipios(@Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento);
    /**
     * Consulta las zonas.
     * @param idPais el identificador del país
     * @param idDepartamento el identificador del departamento
     * @param idMunicipio el identificador del municipio
     * @return la respuesta del servidor
     */
    @GET("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}/zonasyparqueaderos")
    Observable<List<ZonaYParqueadero>> getZonas(@Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
                                                @Path("idMunicipio") int idMunicipio);

    /**
     * Consulta las tarifas por celda.
     * @param idPais el id del vendedor
     * @param idDepartamento el id del departamento
     * @param idMunicipio el id del municipo
     * @param idZonasYParqueaderos el id de la zona
     * @param idCelda el id de la celda
     * @param prepago indica si la zona es prepago
     * @return la respuesta del servidor
     */
    @FormUrlEncoded
    @POST("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}"
            + "/zonasyparqueaderos/{idZonasYParqueaderos}/celdas/{idCelda}/tarifas/activo")
    Observable<List<Tarifa>> getTarifas(
            @Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
            @Path("idMunicipio") int idMunicipio, @Path("idZonasYParqueaderos") int idZonasYParqueaderos,
            @Path("idCelda") int idCelda, @Field("prepago") boolean prepago
    );

    /**
     * Registra la venta por celda.
     * @param idPais el id del vendedor
     * @param idDepartamento el id del departamento
     * @param idMunicipio el id del municipo
     * @param idZonasYParqueaderos el id de la zona
     * @param idCelda el id de la celda
     * @param datos los datos de la venta
     * @return la respuesta del servidor
     */
    @POST("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}"
            + "/zonasyparqueaderos/{idZonasYParqueaderos}/celdas/{idCelda}/registrar")
    @FormUrlEncoded
    Observable<TransaccionCelda> registrar(
            @Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
            @Path("idMunicipio") int idMunicipio, @Path("idZonasYParqueaderos") int idZonasYParqueaderos,
            @Path("idCelda") int idCelda, @FieldMap Map<String, String> datos
    );

}
