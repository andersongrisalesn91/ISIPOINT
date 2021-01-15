package co.com.mirecarga.vendedor.api;

import java.util.List;
import java.util.Map;

import co.com.mirecarga.core.api.Departamento;
import co.com.mirecarga.core.api.EstadoCelda;
import co.com.mirecarga.core.api.Municipio;
import co.com.mirecarga.core.api.Pais;
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
     * Consulta los paises.
     * @return la respuesta del servidor
     */
    @GET("paises")
    Observable<List<Pais>> getPaises();

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

    /**
     * Consulta de estados de celdas.
     * @param idPais el id del vendedor
     * @param idDepartamento el id del departamento
     * @param idMunicipio el id del municipo
     * @param idZonasYParqueaderos el id de la zona
     * @param idCelda el id de la celda
     * @return la respuesta del servidor
     */
    @GET("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}"
            + "/zonasyparqueaderos/{idZonasYParqueaderos}/celdas/{idCelda}/transacciones")
    Observable<List<TransaccionCelda>> getTransaccionesPorCelda(
            @Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
            @Path("idMunicipio") int idMunicipio, @Path("idZonasYParqueaderos") int idZonasYParqueaderos,
            @Path("idCelda") int idCelda
    );

    /**
     * Registra la salida de la celda.
     * @param idPais el id del vendedor
     * @param idDepartamento el id del departamento
     * @param idMunicipio el id del municipo
     * @param idZonasYParqueaderos el id de la zona
     * @param idCelda el id de la celda
     * @param datos los datos del método POST
     * @return la respuesta del servidor
     */
    @POST("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}"
            + "/zonasyparqueaderos/{idZonasYParqueaderos}/celdas/{idCelda}/liberar")
    @FormUrlEncoded
    Observable<TransaccionCelda> liberarCelda(
            @Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
            @Path("idMunicipio") int idMunicipio, @Path("idZonasYParqueaderos") int idZonasYParqueaderos,
            @Path("idCelda") int idCelda, @FieldMap Map<String, String> datos
    );

    /**
     * Registra el postpago de la celda.
     * @param idPais el id del vendedor
     * @param idDepartamento el id del departamento
     * @param idMunicipio el id del municipo
     * @param idZonasYParqueaderos el id de la zona
     * @param idCelda el id de la celda
     * @param datos los datos del método POST
     * @return la respuesta del servidor
     */
    @POST("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}"
            + "/zonasyparqueaderos/{idZonasYParqueaderos}/celdas/{idCelda}/pagar")
    @FormUrlEncoded
    Observable<TransaccionCelda> pagarCelda(
            @Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
            @Path("idMunicipio") int idMunicipio, @Path("idZonasYParqueaderos") int idZonasYParqueaderos,
            @Path("idCelda") int idCelda, @FieldMap Map<String, String> datos
    );

    /**
     * Registra la salida de la celda.
     * @param idPais el id del vendedor
     * @param idDepartamento el id del departamento
     * @param idMunicipio el id del municipo
     * @param idZonasYParqueaderos el id de la zona
     * @param idCelda el id de la celda
     * @param datos los datos del método POST
     * @return la respuesta del servidor
     */
    @POST("paises/{idPais}/departamentos/{idDepartamento}/municipios/{idMunicipio}"
            + "/zonasyparqueaderos/{idZonasYParqueaderos}/celdas/{idCelda}/historico")
    @FormUrlEncoded
    Observable<List<TransaccionCelda>> historicoCelda(
            @Path("idPais") int idPais, @Path("idDepartamento") int idDepartamento,
            @Path("idMunicipio") int idMunicipio, @Path("idZonasYParqueaderos") int idZonasYParqueaderos,
            @Path("idCelda") int idCelda, @FieldMap Map<String, String> datos
    );

    /**
     * Consulta las tarjetas disponibles.
     * @param idVendedor el id del vendedor
     * @param idEc el id del establecimiento
     * @return la respuesta del servidor
     */
    @GET("vendedores/{idVendedor}/productos/tarjetaprepago/{idEc}")
    Observable<List<TarjetaPrepago>> getTarjetas(@Path("idVendedor") int idVendedor, @Path("idEc") int idEc);

    /**
     * Consulta el precio del producto.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @return la respuesta del servidor
     */
    @GET("vendedores/{idVendedor}/productos/{idProducto}/precio")
    Observable<PrecioProducto> getPrecioProducto(@Path("idVendedor") int idVendedor,
                                                 @Path("idProducto") int idProducto);

    /**
     * Registra la auditoría.
     * @param idVendedor el id del vendedor
     * @param idSerial el id de la tarjeta prepago
     * @param serial el serial
     * @param idEc el id del establecimiento
     * @param idCliente el id del cliente
     * @return la respuesta del servidor
     */
    @POST("vendedores/{idVendedor}/productos/tarjetaprepago/venta")
    @FormUrlEncoded
    Observable<VentaTarjetaPrepagoResponse> ventaTarjetaPrepago(
            @Path("idVendedor") int idVendedor, @Field("idSerial") int idSerial,
            @Field("serial") String serial, @Field("idEc") int idEc,
            @Field("idCliente") int idCliente
    );
}
