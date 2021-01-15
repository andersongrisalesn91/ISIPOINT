package co.com.mirecarga.core.offline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * Lógia para detectar cambios de conectividad.
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    /**
     * Los listeners actuales.
     */
    private Set<NetworkStateReceiverListener> listeners;

    /**
     * Indica si se encuentra conectado.
     */
    private Boolean connected;

    /**
     * Constructor vacío.
     */
    public NetworkStateReceiver() {
        listeners = new HashSet<>();
        connected = null;
    }

    /**
     * Recibe el evento de conectividad.
     *
     * @param context el contexto de la app
     * @param intent  el intent actual
     */
    public void onReceive(final Context context, final Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();

            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                connected = false;
            }

            notifyStateToAll();
        }
    }

    /**
     * Envía el evento.
     */
    private void notifyStateToAll() {
        for (NetworkStateReceiverListener listener : listeners) {
            notifyState(listener);
        }
    }

    /**
     * Notifica al listener.
     * @param listener el listener
     */
    private void notifyState(final NetworkStateReceiverListener listener) {
        if (connected != null && listener != null) {
            if (connected) {
                listener.networkAvailable();
            } else {
                listener.networkUnavailable();
            }
        }
    }

    /**
     * Agrega el listener.
     * @param listener el listener
     */
    public void addListener(final NetworkStateReceiverListener listener) {
        listeners.add(listener);
        notifyState(listener);
    }

    /**
     * Retira el listener.
     * @param listener el listener
     */
    public void removeListener(final NetworkStateReceiverListener listener) {
        listeners.remove(listener);
    }

    /**
     * Contrato para la implementación del listener.
     */
    public interface NetworkStateReceiverListener {
        /**
         * Indica que la red está de nuevo disponible.
         */
        void networkAvailable();

        /**
         * Indica que la red no está disponible.
         */
        void networkUnavailable();
    }
}
