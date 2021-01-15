package co.com.mirecarga.cliente.app;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import co.com.mirecarga.cliente.BuildConfig;
import co.com.mirecarga.core.util.MiRecargaApplication;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

import org.osmdroid.config.Configuration;
import org.osmdroid.config.IConfigurationProvider;

import java.util.concurrent.ExecutorService;

/**
 * Inicialización de la aplicación.
 */
public class ClienteApplication extends MiRecargaApplication implements HasActivityInjector, HasSupportFragmentInjector {

    /**
     * Objeto Dagger para inyección en activities.
     */
    @Inject
    transient DispatchingAndroidInjector<Activity> activityInjectorField;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    @Inject
    transient ExecutorService executor;

    /**
     * Objeto Dagger para inyección en fragmentos.
     */
    @Inject
    transient DispatchingAndroidInjector<Fragment> fragmentInjectorField;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjectorField;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjectorField;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerClienteAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build().inject(this);
        //Twitter.initialize(this);
        // Requerido para el mapa osmdroid
        final Context context = this.getApplicationContext();
        final IConfigurationProvider osmdroidConfig = Configuration.getInstance();
        executor.submit(() -> {
            osmdroidConfig.load(context, PreferenceManager.getDefaultSharedPreferences(context));
            osmdroidConfig.setUserAgentValue(BuildConfig.APPLICATION_ID);
            osmdroidConfig.setAnimationSpeedShort(0);
        });
    }
}
