package co.com.mirecarga.core.util;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;

/**
 * Escribe log de depuración del ciclo de vida de los fragmentos.
 */
public class LogFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
    /**
     * El tag para el log.
     */
    private static final String TAG = "FragmentLifecycle";
    /**
     * Formato de mensajes (PMD).
     */
    public static final String MSG = "%s %s";
    /**
     * Formato de mensajes (PMD).
     */
    public static final String MSG_STATE = "%s %s savedInstanceState %s";

    @Override
    public final void onFragmentPreAttached(final FragmentManager fm, final Fragment f, final Context context) {
        super.onFragmentPreAttached(fm, f, context);
        AppLog.verbose(TAG, MSG, f, "PreAttached");
    }

    @Override
    public final void onFragmentAttached(final FragmentManager fm, final Fragment f, final Context context) {
        super.onFragmentAttached(fm, f, context);
        AppLog.verbose(TAG, MSG, f, "Attached");
    }

    @Override
    public final void onFragmentCreated(final FragmentManager fm, final Fragment f, final Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        AppLog.verbose(TAG, MSG_STATE, f, "Created", savedInstanceState != null);
    }

    @Override
    public final void onFragmentActivityCreated(final FragmentManager fm, final Fragment f, final Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        AppLog.verbose(TAG, MSG_STATE, f, "ActivityCreated", savedInstanceState != null);
    }

    @Override
    public final void onFragmentViewCreated(final FragmentManager fm, final Fragment f, final View v, final Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        AppLog.verbose(TAG, MSG_STATE, f, "ViewCreated", savedInstanceState != null);
    }

    @Override
    public final void onFragmentStarted(final FragmentManager fm, final Fragment f) {
        super.onFragmentStarted(fm, f);
        AppLog.verbose(TAG, MSG, f, "Started");
    }

    @Override
    public final void onFragmentResumed(final FragmentManager fm, final Fragment f) {
        super.onFragmentResumed(fm, f);
        AppLog.verbose(TAG, MSG, f, "Resumed");
    }

    @Override
    public final void onFragmentPaused(final FragmentManager fm, final Fragment f) {
        super.onFragmentPaused(fm, f);
        AppLog.verbose(TAG, MSG, f, "Paused");
    }

    @Override
    public final void onFragmentStopped(final FragmentManager fm, final Fragment f) {
        super.onFragmentStopped(fm, f);
        AppLog.verbose(TAG, MSG, f, "Stopped");
    }

    @Override
    public final void onFragmentSaveInstanceState(final FragmentManager fm, final Fragment f, final Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
        AppLog.verbose(TAG, MSG, f, "SaveInstanceState");
    }

    @Override
    public final void onFragmentViewDestroyed(final FragmentManager fm, final Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        AppLog.verbose(TAG, MSG, f, "ViewDestroyed");
    }

    @Override
    public final void onFragmentDestroyed(final FragmentManager fm, final Fragment f) {
        super.onFragmentDestroyed(fm, f);
        AppLog.verbose(TAG, MSG, f, "Destroyed");
    }

    @Override
    public final void onFragmentDetached(final FragmentManager fm, final Fragment f) {
        super.onFragmentDetached(fm, f);
        AppLog.verbose(TAG, MSG, f, "Detached");
    }
}
