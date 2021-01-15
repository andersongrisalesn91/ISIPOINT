package co.com.mirecarga.vendedor.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

/**
 * Módulo dagger para inyectar el contexto de la aplicación.
 */
@Module
public class ApplicationModule {

    /**
     * Instancia de la aplicación actual.
     */
    private final transient Application application;

    /**
     * Constructor para inyectar el contexto de la aplicación.
     * @param application la aplicación actual
     */
    public ApplicationModule(final Application application) {
        this.application = application;
    }

    /**
     * Proporciona el contexto de la aplicación.
     * @return el contexto de la aplicación
     */
    @Provides
    public Context getContext() {
        return application;
    }
    /**
     * Proporciona el manejador de recursos de la aplicación.
     * @param context el contexto de la aplicación
     * @return el manejador de recursos de la aplicación
     */
    @Provides
    public Resources getResources(final Context context) {
        return context.getResources();
    }
    /**
     * Proporciona la localidad del usuario.
     * @return la localidad del usuario
     */
    @Provides
    public Locale getLocale() {
        return Locale.getDefault();
    }
}
