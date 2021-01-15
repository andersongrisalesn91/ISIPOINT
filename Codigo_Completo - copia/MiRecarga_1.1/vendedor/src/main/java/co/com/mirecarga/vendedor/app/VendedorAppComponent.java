package co.com.mirecarga.vendedor.app;

import javax.inject.Singleton;

import co.com.mirecarga.vendedor.api.ApiVendedorModule;
import co.com.mirecarga.vendedor.home.MainActivityModule;
import co.com.mirecarga.vendedor.persistence.PersistenceModule;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Componente para inyección de dependencias en la aplicación.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        AbstractVendedorAppModule.class,
        MainActivityModule.class,
        ApiVendedorModule.class,
        PersistenceModule.class})
public interface VendedorAppComponent {
    /**
     * Agrega las dependencias iniciales a la aplicación.
     * @param app la aplicación
     */
    void inject(VendedorApplication app);
}
