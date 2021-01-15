package co.com.mirecarga.core.util;

import android.app.Application;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatDelegate;

import co.com.mirecarga.core.BuildConfig;

/**
 * Funcionalidad general de las aplicaciones de mi recarga.
 */

public class MiRecargaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Ensures that the support library is correctly configured for use of vector drawables
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (BuildConfig.DEBUG) {
            // Mostrar advertencias para usos erróneos del framework
            StrictMode.enableDefaults();
            // Escribir log del ciclo de vida de las actividades
            registerActivityLifecycleCallbacks(new LogAcitvityLifecycleCallbacks());
        }
    }
}
