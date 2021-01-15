package co.com.mirecarga.core.login;

import android.net.Uri;

/**
 * Datos de conexión a WSO2.
 */
public class ConfigWso2 {
    /**
     * El clientId a usar en oAuth2.
     */
    private String clientId;

    /**
     * La URI a la que llegarán las peticiones en oAuth2.  Debe ser igual a la configurada en
     * appAuthRedirectScheme del build.gradle del módulo del apk.
     */
    private Uri redirectUri;

    /**
     * Los datos que se obtienen en la solicitud de autorización.
     */
    private String authorizationScope;

    /**
     * El endpoint de autorización.
     */
    private Uri authorizationEndpointUri;

    /**
     * El endpoint para obtener el token.
     */
    private Uri tokenEndpointUri;

    /**
     * Indica si se puede ignorar la validez de los certificados https. True para producción.
     */
    private boolean httpsRequired;

    /**
     * Secreto asignado a la aplicación por el WSO2.
     */
    private String clientSecret;

    /**
     * El endpoint base del API.
     */
    private String apiBaseUri;

    /**
     * El path del endpoint del API.
     */
    private String apiUri;

    /**
     * Segundos para obtener desde caché si se encuentra en línea.
     */
    private int segundosCacheEnLinea;

    /**
     * Segundos para obtener desde caché si se encuentra en línea.
     */
    private int horasCacheFueraDeLinea;

    /**
     * Tamaño máximo del caché.
     */
    private long maxCacheSize;

    /**
     * Regresa el campo clientId.
     *
     * @return el valor de clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Establece el valor del campo clientId.
     *
     * @param clientId el valor a establecer
     */
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    /**
     * Regresa el campo redirectUri.
     *
     * @return el valor de redirectUri
     */
    public Uri getRedirectUri() {
        return redirectUri;
    }

    /**
     * Establece el valor del campo redirectUri.
     *
     * @param redirectUri el valor a establecer
     */
    public void setRedirectUri(final Uri redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**
     * Regresa el campo authorizationScope.
     *
     * @return el valor de authorizationScope
     */
    public String getAuthorizationScope() {
        return authorizationScope;
    }

    /**
     * Establece el valor del campo authorizationScope.
     *
     * @param authorizationScope el valor a establecer
     */
    public void setAuthorizationScope(final String authorizationScope) {
        this.authorizationScope = authorizationScope;
    }

    /**
     * Regresa el campo authorizationEndpointUri.
     *
     * @return el valor de authorizationEndpointUri
     */
    public Uri getAuthorizationEndpointUri() {
        return authorizationEndpointUri;
    }

    /**
     * Establece el valor del campo authorizationEndpointUri.
     *
     * @param authorizationEndpointUri el valor a establecer
     */
    public void setAuthorizationEndpointUri(final Uri authorizationEndpointUri) {
        this.authorizationEndpointUri = authorizationEndpointUri;
    }

    /**
     * Regresa el campo tokenEndpointUri.
     *
     * @return el valor de tokenEndpointUri
     */
    public Uri getTokenEndpointUri() {
        return tokenEndpointUri;
    }

    /**
     * Establece el valor del campo tokenEndpointUri.
     *
     * @param tokenEndpointUri el valor a establecer
     */
    public void setTokenEndpointUri(final Uri tokenEndpointUri) {
        this.tokenEndpointUri = tokenEndpointUri;
    }

    /**
     * Regresa el campo httpsRequired.
     *
     * @return el valor de httpsRequired
     */
    public boolean isHttpsRequired() {
        return httpsRequired;
    }

    /**
     * Establece el valor del campo httpsRequired.
     *
     * @param httpsRequired el valor a establecer
     */
    public void setHttpsRequired(final boolean httpsRequired) {
        this.httpsRequired = httpsRequired;
    }

    /**
     * Regresa el campo clientSecret.
     *
     * @return el valor de clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Establece el valor del campo clientSecret.
     *
     * @param clientSecret el valor a establecer
     */
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Regresa el campo apiBaseUri.
     *
     * @return el valor de apiBaseUri
     */
    public String getApiBaseUri() {
        return apiBaseUri;
    }

    /**
     * Establece el valor del campo apiBaseUri.
     *
     * @param apiBaseUri el valor a establecer
     */
    public void setApiBaseUri(final String apiBaseUri) {
        this.apiBaseUri = apiBaseUri;
    }

    /**
     * Regresa el campo apiUri.
     *
     * @return el valor de apiUri
     */
    public String getApiUri() {
        return apiUri;
    }

    /**
     * Establece el valor del campo apiUri.
     *
     * @param apiUri el valor a establecer
     */
    public void setApiUri(final String apiUri) {
        this.apiUri = apiUri;
    }

    /**
     * Regresa el campo segundosCacheEnLinea.
     *
     * @return el valor de segundosCacheEnLinea
     */
    public int getSegundosCacheEnLinea() {
        return segundosCacheEnLinea;
    }

    /**
     * Establece el valor del campo segundosCacheEnLinea.
     *
     * @param segundosCacheEnLinea el valor a establecer
     */
    public void setSegundosCacheEnLinea(final int segundosCacheEnLinea) {
        this.segundosCacheEnLinea = segundosCacheEnLinea;
    }

    /**
     * Regresa el campo horasCacheFueraDeLinea.
     *
     * @return el valor de horasCacheFueraDeLinea
     */
    public int getHorasCacheFueraDeLinea() {
        return horasCacheFueraDeLinea;
    }

    /**
     * Establece el valor del campo horasCacheFueraDeLinea.
     *
     * @param horasCacheFueraDeLinea el valor a establecer
     */
    public void setHorasCacheFueraDeLinea(final int horasCacheFueraDeLinea) {
        this.horasCacheFueraDeLinea = horasCacheFueraDeLinea;
    }

    /**
     * Regresa el campo maxCacheSize.
     *
     * @return el valor de maxCacheSize
     */
    public long getMaxCacheSize() {
        return maxCacheSize;
    }

    /**
     * Establece el valor del campo maxCacheSize.
     *
     * @param maxCacheSize el valor a establecer
     */
    public void setMaxCacheSize(final long maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }
}
