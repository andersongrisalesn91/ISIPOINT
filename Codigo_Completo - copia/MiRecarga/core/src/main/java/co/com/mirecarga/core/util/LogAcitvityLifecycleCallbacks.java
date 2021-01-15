package co.com.mirecarga.core.util;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

/**
 * Escribe log del ciclo de vida del activity.
 */
public class LogAcitvityLifecycleCallbacks implements ActivityLifecycleCallbacks {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ActivityLifecycle";
    /**
     * Formato de mensajes (PMD).
     */
    public static final String MSG = "%s %s";

    @Override
    public final void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
        AppLog.verbose(TAG, "%s %s savedInstanceState %s", activity, "created",
                savedInstanceState != null);
    }

    @Override
    public final void onActivityStarted(final Activity activity) {
        AppLog.verbose(TAG, MSG, activity, "started");
    }

    @Override
    public final void onActivityResumed(final Activity activity) {
        AppLog.verbose(TAG, MSG, activity, "resumed");
    }

    @Override
    public final void onActivityPaused(final Activity activity) {
        AppLog.verbose(TAG, MSG, activity, "paused");
    }

    @Override
    public final void onActivityStopped(final Activity activity) {
        AppLog.verbose(TAG, MSG, activity, "stopped");
    }

    @Override
    public final void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
        AppLog.verbose(TAG, MSG, activity, "saved");
    }

    @Override
    public final void onActivityDestroyed(final Activity activity) {
        AppLog.verbose(TAG, MSG, activity, "destroyed");
    }
}
