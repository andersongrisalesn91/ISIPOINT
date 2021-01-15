package co.com.mirecarga.vendedor.configuracionimpresora;

import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;

/**
 * Lógica compartida de impresoras.
 */
public abstract class AbstractImpresora implements Impresora {
    /**
     * El tag para el log.
     */
    private static final String TAG = "AbstractImpresora";

    /**
     * El contexto de ejecución.
     */
    private final transient AbstractAppFragment fragment;

    /**
     * Constructor con las propiedades.
     * @param fragment el fragmento para mostrar mensajes
     */
    public AbstractImpresora(final AbstractAppFragment fragment) {
        this.fragment = fragment;
    }

    /**
     * Muestra un mensaje breve al usuario de forma estándar.
     * @param mensaje el mensaje a mostrar
     * @param args argumentos variables para String.format.
     */
    public final void mostrarMensaje(final String mensaje, final Object... args) {
        fragment.mostrarMensaje(mensaje, args);
    }

    /**
     * Muestra el mensaje tipo toast.
     * @param msg el mensaje
     * @param args argumentos variables para String.format.
     */
    public final void informativo(final String msg, final Object... args) {
        AppLog.warn(TAG, msg, args);
        mostrarMensaje(msg, args);
    }

    /**
     * Muestra el mensaje tipo toast.
     * @param msg el mensaje
     * @param args argumentos variables para String.format.
     */
    public final void error(final String msg, final Object... args) {
        AppLog.error(TAG, msg, args);
        mostrarMensaje(msg, args);
    }

    /**
     * Muestra el mensaje tipo toast.
     * @param cause la causa
     */
    public final void error(final Throwable cause) {
        AppLog.error(TAG, cause, "Error de impresora");
        mostrarMensaje("Error de impresora %s", cause.getMessage());
    }
}
