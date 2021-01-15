package co.com.mirecarga.cliente.api;

import com.google.gson.annotations.SerializedName;

/**
 * Respuesta con la información del Token.
 */
public class MiRecargaTokenResponse {
    /**
     * Token generado por el servicio.
     */
    @SerializedName("access_token")
    private String accessToken;

    /**
     * Regresa el campo accessToken.
     *
     * @return el valor de accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Establece el valor del campo accessToken.
     *
     * @param accessToken el valor a establecer
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }
}
