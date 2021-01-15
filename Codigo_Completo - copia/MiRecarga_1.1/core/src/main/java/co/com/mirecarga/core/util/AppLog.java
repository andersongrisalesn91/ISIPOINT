package co.com.mirecarga.core.util;

import android.util.Log;

import co.com.mirecarga.core.BuildConfig;

/**
 * Utilitarios para escribir log.
 */

public final class AppLog {
    /**
     * Constructor privado para evitar instancias.
     */
    private AppLog() {
        // Constructor privado para evitar instancias
    }

    /**
     * Escribe el mensaje de depuración.
     * @param tag el tag del log
     * @param msg el mensaje
     * @param args parámetros variables para el mensaje
     */
    public static void verbose(final String tag, final String msg, final Object... args) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, String.format(msg, args));
        }
    }

    /**
     * Escribe el mensaje de depuración.
     * @param tag el tag del log
     * @param msg el mensaje
     * @param args parámetros variables para el mensaje
     */
    public static void debug(final String tag, final String msg, final Object... args) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, String.format(msg, args));
        }
    }

    /**
     * Escribe el mensaje informativo.
     * @param tag el tag del log
     * @param msg el mensaje
     * @param args parámetros variables para el mensaje
     */
    public static void info(final String tag, final String msg, final Object... args) {
        Log.i(tag, String.format(msg, args));
    }

    /**
     * Escribe el mensaje de advertencia.
     * @param tag el tag del log
     * @param msg el mensaje
     * @param args parámetros variables para el mensaje
     */
    public static void warn(final String tag, final String msg, final Object... args) {
        Log.w(tag, String.format(msg, args));
    }

    /**
     * Escribe el mensaje de error.
     * @param tag el tag del log
     * @param msg el mensaje
     * @param args parámetros variables para el mensaje
     */
    public static void error(final String tag, final String msg, final Object... args) {
        Log.e(tag, String.format(msg, args));
    }

    /**
     * Escribe el mensaje de error con la excepción.
     * @param tag el tag del log
     * @param e la excepción
     * @param msg el mensaje
     * @param args parámetros variables para el mensaje
     */
    public static void error(final String tag, final Throwable e, final String msg, final Object... args) {
        Log.e(tag, String.format(msg, args), e);
    }
}
