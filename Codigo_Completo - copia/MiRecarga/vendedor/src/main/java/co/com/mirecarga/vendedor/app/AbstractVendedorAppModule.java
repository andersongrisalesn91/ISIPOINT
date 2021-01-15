package co.com.mirecarga.vendedor.app;

import android.content.res.Resources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import co.com.mirecarga.core.home.ConfigMenu;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppFormatterServiceBean;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.ConfigServiceBean;
import co.com.mirecarga.core.util.TemplateService;
import co.com.mirecarga.core.util.TemplateServiceBean;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.home.MainActivity;
import co.com.mirecarga.vendedor.home.MainActivityModule;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Módulo dagger para la aplicación.
 */
@Module
public abstract class AbstractVendedorAppModule {
    /**
     * Declara la actividad.
     * @return la instancia de la actividad
     */
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    public abstract MainActivity mainActivityInjector();

    /**
     * Servicio de ejecución de código asíncrono.
     * @return la instancia creada
     */
    @Provides
    public static ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(2);
    }

    /**
     * Asocia el servicio a la implementación.
     * @param resources el manejador de recursos
     * @return la instancia creada
     */
    @Singleton
    @Provides
    public static ConfigService<ConfigVendedor> getConfigVendedorService(final Resources resources) {
        return new ConfigServiceBean<>(ConfigVendedor.class, resources, R.raw.config_vendedor);
    }

    /**
     * Asocia el servicio a la implementación.
     * @param resources el manejador de recursos
     * @return la instancia creada
     */
    @Singleton
    @Provides
    public static ConfigService<ConfigMenu> getConfigMenuService(final Resources resources) {
        return new ConfigServiceBean<>(ConfigMenu.class, resources, R.raw.menu_vendedor);
    }

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract TemplateService getTemplateService(TemplateServiceBean bean);

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract AppFormatterService getAppFormatterService(AppFormatterServiceBean bean);

}
