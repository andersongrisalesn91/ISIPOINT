package co.com.mirecarga.cliente.api;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Contrato del servicio de acceso al API.
 */
public interface ApiBaseClienteService {
    /**
     * Obtiene el token para el cliente.
     * @param authorization el secreto para la autorización
     * @param grantType el tipo de permiso
     * @return los datos de token a usar
     */
    @FormUrlEncoded
    @POST("token")
    Observable<MiRecargaTokenResponse> token(@Header("Authorization") String authorization,
                                             @Field("grant_type") String grantType);
}
