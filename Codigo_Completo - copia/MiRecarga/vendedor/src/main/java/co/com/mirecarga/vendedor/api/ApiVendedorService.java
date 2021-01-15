package co.com.mirecarga.vendedor.api;


import java.util.List;

import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.api.Paquete;
import co.com.mirecarga.core.api.Placa;
import co.com.mirecarga.core.api.ProductoResponse;
import co.com.mirecarga.core.api.Zona;
import co.com.mirecarga.core.home.PerfilResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Contrato del servicio de acceso al API.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ApiVendedorService {
    /**
     * Obtiene datos del perfil del usuario.
     * @param login el login del usuario
     * @return los items de la lista
     */
    @GET("{login}/perfil")
    Observable<PerfilResponse> getPerfil(@Path("login") String login);

    /**
     * Lista de operadores.
     * @return los items de la lista
     */
    @GET("recarga/operadores")
    Observable<List<Operador>> getOperadores();

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
     * Consulta de saldo por vendedor y producto.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @param celular el número de celular
     * @param operadorId el id del operador
     * @param operadorNombre el nombre del operador
     * @param valor el valor a recargar
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/ventas/productos/{idProducto}/celular/{celular}/operadorId/{operadorId}/operadorNombre/{operadorNombre}/valor/{valor}")
    Observable<RecargarEnLineaResponse> recargaEnLinea(
            @Path("idVendedor") int idVendedor, @Path("idProducto") int idProducto,
            @Path("celular") String celular, @Path("operadorId") int operadorId,
            @Path("operadorNombre") String operadorNombre, @Path("valor") int valor
    );

    /**
     * Consulta los paquetes disponibles.
     * @return la respuesta del servidor
     */
    @GET("paquetes/pin")
    Observable<List<Paquete>> getPaquetes();

    /**
     * Consulta los productos disponibles para el vendedor.
     * @param idVendedor el id del vendedor
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/ventas/productos")
    Observable<ProductoResponse> getProductos(@Path("idVendedor") int idVendedor);

    /**
     * Consulta los productos disponibles para el vendedor.
     * @param idVendedor el id del vendedor
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/zonas")
    Observable<List<Zona>> getZonas(@Path("idVendedor") int idVendedor);

    /**
     * Consulta los clientes disponibles para el vendedor.
     * @param idVendedor el id del vendedor
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/cliente/null/identificacion/null")
    Observable<List<Cliente>> getClientes(@Path("idVendedor") int idVendedor);

    /**
     * Consulta las placas disponibles para el cliente.
     * @param idCliente el id del vendedor
     * @return la respuesta del servidor
     */
    @GET("{idCliente}/recursoscliente/{idCliente}/1")
    Observable<List<Placa>> getPlacas(@Path("idCliente") int idCliente);

    /**
     * Consulta las ventas por vendedor.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @param fechaInicial la fecha inicial para la búsqueda
     * @param fechaFinal la fecha final para la búsqueda
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/ventasVendedor/{idVendedor}/productos/{idProducto}/cliente/null/{fechaInicial}/{fechaFinal}")
    Observable<List<ReporteItem>> ventasVendedor(
            @Path("idVendedor") int idVendedor, @Path("idProducto") int idProducto,
            @Path("fechaInicial") String fechaInicial,
            @Path("fechaFinal") String fechaFinal
    );

    /**
     * Consulta las transacciones por un PIN.
     * @param idVendedor el id del vendedor
     * @param fechaInicial la fecha inicial para la búsqueda
     * @param fechaFinal la fecha final para la búsqueda
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/consultaUsoPin/null/{fechaInicial}/{fechaFinal}")
    Observable<List<ReporteItem>> transaccionesPin(
            @Path("idVendedor") int idVendedor, @Path("fechaInicial") String fechaInicial,
            @Path("fechaFinal") String fechaFinal
    );

    /**
     * Consulta las ventas por cliente.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @param fechaInicial la fecha inicial para la búsqueda
     * @param fechaFinal la fecha final para la búsqueda
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/ventas/productos/{idProducto}/cliente/null/{fechaInicial}/{fechaFinal}")
    Observable<List<ReporteItem>> ventasCliente(
            @Path("idVendedor") int idVendedor, @Path("idProducto") int idProducto,
            @Path("fechaInicial") String fechaInicial,
            @Path("fechaFinal") String fechaFinal
    );

    /**
     * Venta de paquete de tránsito.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @param placa la placa
     * @param idPaquete el id del paquete
     * @param idZona el id de la zona
     * @param idCliente el id del cliente
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/ventas/productos/{idProducto}/pin/{placa}/confirmarVenta/{idPaquete}/zona/{idZona}/cliente/{idCliente}")
    Observable<VentaPaqueteTransitoResponse> ventaPaqueteTransito(
            @Path("idVendedor") int idVendedor, @Path("idProducto") int idProducto,
            @Path("placa") String placa, @Path("idPaquete") int idPaquete,
            @Path("idZona") int idZona, @Path("idCliente") int idCliente
    );

    /**
     * Venta de paquete de tránsito.
     * @param idVendedor el id del vendedor
     * @param idProducto el id del producto
     * @param idPaquete el id del paquete
     * @param idCliente el id del cliente
     * @return la respuesta del servidor
     */
    @GET("{idVendedor}/ventas/productos/{idProducto}/pin/confirmarVenta/{idPaquete}/cliente/{idCliente}")
    Observable<VentaPaqueteTransitoResponse> ventaPinTransito(
            @Path("idVendedor") int idVendedor, @Path("idProducto") int idProducto,
            @Path("idPaquete") int idPaquete, @Path("idCliente") int idCliente
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
    @POST("{idVendedor}/auditorias/registrar")
    @FormUrlEncoded
    // SUPPRESS CHECKSTYLE: ParameterNumber
    Observable<MiRecargaResponse> auditoria(
            @Path("idVendedor") int idVendedor, @Field("operacion") String operacion,
            @Field("entidad") String entidad, @Field("codEntidad") String codEntidad,
            @Field("origen") String origen, @Field(value = "mensaje") String mensaje,
            @Field("dato1") String dato1, @Field("dato2") String dato2,
            @Field("dato3") String dato3, @Field("detalles") String detalles
    );
}
