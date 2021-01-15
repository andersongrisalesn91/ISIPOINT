package co.com.mirecarga.cliente.app;

import javax.inject.Singleton;

import co.com.mirecarga.cliente.api.ApiClienteModule;
import co.com.mirecarga.cliente.home.MainActivityModule;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Componente para inyección de dependencias en la aplicación.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        AbstractClienteAppModule.class,
        MainActivityModule.class,
        ApiClienteModule.class})
public interface ClienteAppComponent {
    /**
     * Agrega las dependencias iniciales a la aplicación.
     * @param app la aplicación
     */
    void inject(ClienteApplication app);
}
