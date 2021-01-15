package co.com.mirecarga.cliente.api;


import java.util.List;

import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.api.Paquete;
import co.com.mirecarga.core.api.Placa;
import co.com.mirecarga.core.api.Tarjeta;
import co.com.mirecarga.core.api.Zona;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Contrato del servicio de acceso al API.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ApiClienteService {
    /**
     * Obtiene datos del perfil del usuario.
     * @param correoElectronico el email del usuario
     * @param nombreCompleto el nombre completo del usuario
     * @return la respuesta del servicio
     */
    @GET("registrarcliente/{correoElectronico}/{nombreCompleto}")
    Observable<RegistrarClienteResponse> registrarCliente(
            @Path(value = "correoElectronico", encoded = true) String correoElectronico,
                                                @Path("nombreCompleto") String nombreCompleto);

    /**
     * Obtiene el token para el cliente.
     * @param authorization el secreto para la autorización
     * @param grantType el tipo de permiso
     * @return los datos de token a usar
     */
    @FormUrlEncoded
    @POST("tasks")
    Observable<MiRecargaTokenResponse> token(@Header("Authorization") String authorization, @Field("grant_type") String grantType);


    /**
     * Consulta de saldo por vendedor y producto.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/consultasaldo/{idVendedor}/producto/{idProducto}")
    Observable<ConsultaSaldoResponse> consultaSaldo(
            @Path("idVendedor") int idVendedor, @Path("idProducto") int idProducto
    );

    /**
     * Consulta los paquetes disponibles.
     * @return la respuesta del servidor
     */
    @GET("paquetes/pin")
    Observable<List<Paquete>> getPaquetes();

    /**
     * Consulta las placas disponibles para el cliente.
     * @param idCliente el id del vendedor
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/recursoscliente/{idCliente}/1")
    Observable<List<Placa>> getPlacas(@Path("idCliente") int idCliente);

    /**
     * Edita la placa de un cliente.
     * @param idCliente el id del cliente
     * @param idPlaca el id de la placa
     * @param placa la placa
     * @return true si fue correcto
     */
    @GET("{idCliente}/recursos/editar/{idPlaca}/{placa}")
    Observable<Boolean> editarPlaca(
            @Path("idCliente") int idCliente, @Path("idPlaca") int idPlaca, @Path("placa") String placa
    );

    /**
     * Agrega placa a un cliente.
     * @param idCliente el id del cliente
     * @param placa la placa
     * @return true si fue correcto
     */
    @GET("{idCliente}/recursos/crear/{idCliente}/{placa}")
    Observable<AgregarPlacaResponse> agregarPlaca(
            @Path("idCliente") int idCliente, @Path("placa") String placa
    );

    /**
     * Elimina la placa de un cliente.
     * @param idCliente el id del cliente
     * @param idPlaca el id de la placa
     * @return true si fue correcto
     */
    @GET("{idCliente}/recursos/remover/{idPlaca}")
    Observable<Boolean> eliminarPlaca(
            @Path("idCliente") int idCliente, @Path("idPlaca") int idPlaca
    );

    /**
     * Consulta las ventas por cliente.
     * @param idCliente el id del cliente
     * @param fechaInicial la fecha inicial para la búsqueda
     * @param fechaFinal la fecha final para la búsqueda
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/auditorias/null/entidad/null/codentidad/null/fechaini/{fechaInicial}/fechafin/{fechaFinal}/dato1/null/dato2/null/dato3/null")
    Observable<List<TransaccionesRealizadasResponse>> transaccionesRealizadas(
            @Path("idCliente") int idCliente,
            @Path("fechaInicial") String fechaInicial,
            @Path("fechaFinal") String fechaFinal
    );

    /**
     * Obtiene las tarjetas de un cliente.
     * @param idCliente el id del cliente
     * @return true si fue correcto
     */
    @GET("{idCliente}/consultaPines/{idCliente}")
    Observable<List<Tarjeta>> getTarjetas(
            @Path("idCliente") int idCliente);

    /**
     * Consulta las zonas disponibles para el cliente.
     * @param idCliente el id del cliente
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/zonas")
    Observable<List<Zona>> getZonas(@Path("idCliente") int idCliente);

    /**
     * Inicio uso de PIN.
     * @param idCliente el id del vendedor
     * @param metodoPago el método de pago
     * @param placa la placa
     * @param idZona el id de la zona
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/iniciarUsoPin/{metodoPago}/placa/{placa}/zona/{idZona}")
    Observable<InicioUsoPinResponse> iniciarUsoPin(
            @Path("idCliente") int idCliente, @Path("metodoPago") String metodoPago,
            @Path("placa") String placa,
            @Path("idZona") int idZona
    );

    /**
     * Finalizar uso de PIN.
     * @param idCliente el id del vendedor
     * @param metodoPago el método de pago
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/finalizarUsoPin/{metodoPago}/placa/null/zona/1/minutos/1")
    Observable<FinalizarUsoPinResponse> finalizarUsoPin(
            @Path("idCliente") int idCliente, @Path("metodoPago") String metodoPago
    );

    /**
     * Compra de paquetes de PIN.
     * @param idCliente el id del vendedor
     * @param metodoPago el método de pago
     * @param placa la placa
     * @param idZona el id de la zona
     * @param idPaquete el identificador del paquete
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/usarPin/{metodoPago}/placa/{placa}/zona/{idZona}/paquete/{idPaquete}")
    Observable<CompraPaqueteResponse> compraPaquete(
            @Path("idCliente") int idCliente, @Path("metodoPago") String metodoPago, @Path("placa") String placa,
            @Path("idZona") int idZona, @Path("idPaquete") int idPaquete
    );

    /**
     * Consulta las transacciones por un PIN.
     * @param idCliente el id del cliente
     * @param metodoPago el método de pago
     * @param fechaInicial la fecha inicial para la búsqueda
     * @param fechaFinal la fecha final para la búsqueda
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/consultaUsoPin/{metodoPago}/{fechaInicial}/{fechaFinal}")
    Observable<List<ReporteItem>> transaccionesPin(
            @Path("idCliente") int idCliente, @Path("metodoPago") String metodoPago,
            @Path("fechaInicial") String fechaInicial, @Path("fechaFinal") String fechaFinal
    );

    /**
     * Registra la auditoría.
     * @param idVendedor el id del vendedor
     * @param operacion el dato de auditoría
     * @param entidad el dato de auditoría
     * @param codEntidad el dato de auditoría
     * @param origen el dato de auditoría
     * @param mensaje el dato de auditoría
     * @param dato1 el dato de auditoría
     * @param dato2 el dato de auditoría
     * @param dato3 el dato de auditoría
     * @param detalles el dato de auditoría
     * @return la respuesta del servidor
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    @POST("{idCliente}/auditorias/registrar")
    @FormUrlEncoded
    // SUPPRESS CHECKSTYLE: ParameterNumber
    Observable<MiRecargaResponse> auditoria(
            @Path("idCliente") int idVendedor, @Field("operacion") String operacion,
            @Field("entidad") String entidad, @Field("codEntidad") String codEntidad,
            @Field("origen") String origen, @Field(value = "mensaje") String mensaje,
            @Field("dato1") String dato1, @Field("dato2") String dato2,
            @Field("dato3") String dato3, @Field("detalles") String detalles
    );
}
