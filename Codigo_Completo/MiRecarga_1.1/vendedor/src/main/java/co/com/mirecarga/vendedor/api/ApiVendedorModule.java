package co.com.mirecarga.vendedor.api;

import android.content.Context;

import javax.inject.Singleton;

import co.com.mirecarga.core.login.AuthStateManager;
import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.ServiceGenerator;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.vendedor.login.TokenVendedorServiceBean;
import co.com.mirecarga.vendedor.mapa.TokenZERServiceBean;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Módulo Dagger para inyección en el cliente del API.
 */
@Module
public class ApiVendedorModule {
    /**
     * Asocia el servicio a la implementación.  Para el vendedor se utiliza el token AppAuth.
     * @param authStateManager el estado de la autenticación
     * @return la instancia creada
     */
    @Singleton
    @Provides
    public TokenService getTokenProvider(final AuthStateManager authStateManager) {
        return new TokenVendedorServiceBean(authStateManager);
    }

    /**
     * Crea el proveedor de retrofit para acceso al API.
     * @param context el contexto de la aplicación
     * @param configVendedorService el servicio de configuración
     * @param tokenProvider el almacén del token oauth2
     * @return el proveedor de retrofit
     */
    @Singleton
    @Provides
    public final Retrofit provideRetrofit(final Context context,
                                    final ConfigService<ConfigVendedor> configVendedorService,
                                    final TokenService tokenProvider) {
        final ConfigWso2 wso2 = configVendedorService.getConfig().getWso2();
        return ServiceGenerator.getRetrofit(context, wso2.getApiBaseUri() + wso2.getApiUri(), wso2, tokenProvider);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param retrofit el proveedor de retrofit
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiVendedorService provideApiVendedorService(final Retrofit retrofit) {
        return retrofit.create(ApiVendedorService.class);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param context el contexto de la aplicación
     * @param configVendedorService el servicio de configuración
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiMapaVendedorService provideTokenMapaVendedorService(final Context context,
                                                                final ConfigService<ConfigVendedor> configVendedorService) {
        final ConfigWso2 wso2 = configVendedorService.getConfig().getWso2();
        final String urlCartografia = configVendedorService.getConfig().getMapa().getUrlCartografia();

        final Retrofit retrofitToken = ServiceGenerator.getRetrofit(context, urlCartografia, wso2, null);
        return retrofitToken.create(ApiMapaVendedorService.class);
    }

    /**
     * Construye el servicio de acceso al API.
     * @param context el contexto de la aplicación
     * @param configVendedorService el servicio de configuración
     *  @param tokenProvider el almacén del token oauth2
     * @return el servicio de acceso al API
     */
    @Provides
    @Singleton
    public final ApiZERService provideTokenZERService(final Context context,
                                                                   final ConfigService<ConfigVendedor> configVendedorService,
                                                      final TokenZERServiceBean tokenProvider) {
        final ConfigWso2 wso2 = configVendedorService.getConfig().getWso2();
        final String urlZER = configVendedorService.getConfig().getMapa().getUrlZER();
        final Retrofit retrofitToken = ServiceGenerator.getRetrofit(context, urlZER, wso2, tokenProvider);
        return retrofitToken.create(ApiZERService.class);
    }
}
