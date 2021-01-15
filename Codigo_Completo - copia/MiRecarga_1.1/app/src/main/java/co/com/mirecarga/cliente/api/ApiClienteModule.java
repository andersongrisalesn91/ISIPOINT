package co.com.mirecarga.cliente.api;

import android.content.Context;

import javax.inject.Singleton;

import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.cliente.mapa.TokenZERServiceBean;
import co.com.mirecarga.cliente.login.TokenClienteServiceBean;
import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.ServiceGenerator;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Módulo Dagger para inyección en el cliente del API.
 */
@Module
public class ApiClienteModule {
    /**
     * Asocia el servicio a la implementación.  Para el cliente se utiliza el token consultado.
     * @return la instancia creada
     */
    @Singleton
    @Provides
    public TokenService getTokenProvider() {
        return new TokenClienteServiceBean();
    }

    /**
     * Crea el proveedor de retrofit para acceso al API.
     * @param context el contexto de la aplicación
     * @param configClienteService el servicio de configuración
     * @param tokenProvider el almacén del token oauth2
     * @return el proveedor de retrofit
     */
    @Singleton
    @Provides
    public final Retrofit provideRetrofit(final Context context,
                                          final ConfigService<ConfigCliente> configClienteService,
                                          final TokenService tokenProvider) {
        final ConfigWso2 wso2 = configClienteService.getConfig().getWso2();
        return ServiceGenerator.getRetrofit(context, wso2.getApiBaseUri() + wso2.getApiUri(), wso2, tokenProvider);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param retrofit el proveedor de retrofit
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiClienteService provideApiClienteService(final Retrofit retrofit) {
        return retrofit.create(ApiClienteService.class);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param context el contexto de la aplicación
     * @param configClienteService el servicio de configuración
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiBaseClienteService provideTokenClienteService(final Context context,
                                                                  final ConfigService<ConfigCliente> configClienteService) {
        final ConfigWso2 wso2 = configClienteService.getConfig().getWso2();

        final Retrofit retrofitToken = ServiceGenerator.getRetrofit(context, wso2.getApiBaseUri(), wso2, null);
        return retrofitToken.create(ApiBaseClienteService.class);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param context el contexto de la aplicación
     * @param configVendedorService el servicio de configuración
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiMapaClienteService provideTokenMapaVendedorService(final Context context,
                                                                       final ConfigService<ConfigCliente> configVendedorService) {
        final ConfigWso2 wso2 = configVendedorService.getConfig().getWso2();
        final String urlCartografia = configVendedorService.getConfig().getMapa().getUrlCartografia();

        final Retrofit retrofitToken = ServiceGenerator.getRetrofit(context, urlCartografia, wso2, null);
        return retrofitToken.create(ApiMapaClienteService.class);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param context el contexto de la aplicación
     * @param configClienteService el servicio de configuración
     *  @param tokenProvider el almacén del token oauth2
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiZERService provideTokenZERService(final Context context,
                                                      final ConfigService<ConfigCliente> configClienteService,
                                                      final TokenZERServiceBean tokenProvider) {
        final ConfigWso2 wso2 = configClienteService.getConfig().getWso2();
        final String urlZER = configClienteService.getConfig().getMapa().getUrlZER();
        final Retrofit retrofitToken = ServiceGenerator.getRetrofit(context, urlZER, wso2, tokenProvider);
        return retrofitToken.create(ApiZERService.class);
    }

}
