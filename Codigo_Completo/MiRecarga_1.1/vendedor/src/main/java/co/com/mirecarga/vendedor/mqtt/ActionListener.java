package co.com.mirecarga.vendedor.mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;

import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.persistence.Subscription;

/**
 * This Class handles receiving information from the
 * {@link MqttAndroidClient} and updating the {@link MQTTConnection} associated with
 * the action.
 */
public class ActionListener implements IMqttActionListener {

    private static final String TAG = "ActionListener";

    /**
     * Actions that can be performed Asynchronously <strong>and</strong> associated with a
     * {@link ActionListener} object
     */
    enum Action {
        /**
         * Connect Action
         **/
        CONNECT,
        /**
         * Disconnect Action
         **/
        DISCONNECT,
        /**
         * Subscribe Action
         **/
        SUBSCRIBE,
        /**
         * Publish Action
         **/
        PUBLISH
    }

    /**
     * The {@link Action} that is associated with this instance of
     * <code>ActionListener</code>
     **/
    private final Action action;
    /**
     * The arguments passed to be used for formatting strings
     **/
    private final String[] additionalArgs;

    private final MQTTConnection connection;
    /**
     * Handle of the {@link MQTTConnection} this action was being executed on
     **/
    private final String clientHandle;
    /**
     * {@link Context} for performing various operations
     **/
    private final Context context;

    /**
     * Creates a generic action listener for actions performed form any activity
     *
     * @param context        The application context
     * @param action         The action that is being performed
     * @param connection     The connection
     * @param additionalArgs Used for as arguments for string formating
     */
    public ActionListener(Context context, Action action,
                          MQTTConnection connection, String... additionalArgs) {
        this.context = context;
        this.action = action;
        this.connection = connection;
        this.clientHandle = connection.handle();
        this.additionalArgs = additionalArgs;
    }

    /**
     * The action associated with this listener has been successful.
     *
     * @param asyncActionToken This argument is not used
     */
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        switch (action) {
            case CONNECT:
                connect();
                break;
            case DISCONNECT:
                disconnect();
                break;
            case SUBSCRIBE:
                subscribe();
                break;
            case PUBLISH:
                publish();
                break;
        }
    }

    /**
     * A publish action has been successfully completed, update connection
     * object associated with the client this action belongs to, then notify the
     * user of success
     */
    private void publish() {

        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        String actionTaken = String.format("MQTT Publicado '%s' en %s", additionalArgs[0], additionalArgs[1]);
        c.addAction(actionTaken);
        AppLog.debug(TAG, actionTaken);
    }

    /**
     * A addNewSubscription action has been successfully completed, update the connection
     * object associated with the client this action belongs to and then notify
     * the user of success
     */
    private void subscribe() {
        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        String actionTaken = String.format("MQTT Suscrito a %s", additionalArgs[0]);
        c.addAction(actionTaken);
        AppLog.debug(TAG, actionTaken);
    }

    /**
     * A disconnection action has been successfully completed, update the
     * connection object associated with the client this action belongs to and
     * then notify the user of success.
     */
    private void disconnect() {
        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        c.changeConnectionStatus(MQTTConnection.ConnectionStatus.DISCONNECTED);
        String actionTaken = "toast_disconnected";
        c.addAction(actionTaken);
        Log.i(TAG, c.handle() + " disconnected.");
    }

    /**
     * A connection action has been successfully completed, update the
     * connection object associated with the client this action belongs to and
     * then notify the user of success.
     */
    private void connect() {

        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        c.changeConnectionStatus(MQTTConnection.ConnectionStatus.CONNECTED);
        c.addAction("Client Connected");
        Log.i(TAG, c.handle() + " connected.");
        try {

            ArrayList<Subscription> subscriptions = connection.getSubscriptions();
            for (Subscription sub : subscriptions) {
                Log.i(TAG, "Auto-subscribing to: " + sub.getTopic() + "@ QoS: " + sub.getQos());
                connection.getClient().subscribe(sub.getTopic(), sub.getQos());
            }
        } catch (MqttException ex){
            Log.e(TAG, "Failed to Auto-Subscribe: " + ex.getMessage());
        }

    }

    /**
     * The action associated with the object was a failure
     *
     * @param token     This argument is not used
     * @param exception The exception which indicates why the action failed
     */
    @Override
    public void onFailure(IMqttToken token, Throwable exception) {
        switch (action) {
            case CONNECT:
                connect(exception);
                break;
            case DISCONNECT:
                disconnect(exception);
                break;
            case SUBSCRIBE:
                subscribe(exception);
                break;
            case PUBLISH:
                publish(exception);
                break;
        }

    }

    /**
     * A publish action was unsuccessful, notify user and update client history
     *
     * @param exception This argument is not used
     */
    private void publish(Throwable exception) {
        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        String action = String.format("MQTT Error al publicar %s en el topico %s", additionalArgs[0], additionalArgs[1]);
        c.addAction(action);
        AppLog.error(TAG, exception, action);
    }

    /**
     * A addNewSubscription action was unsuccessful, notify user and update client history
     *
     * @param exception This argument is not used
     */
    private void subscribe(Throwable exception) {
        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        String detalle = exception == null ? "" : exception.toString();
        String action = String.format("MQTT  Error al suscribir a %s: %s", additionalArgs[0], detalle);
        c.addAction(action);
        AppLog.error(TAG, exception, action);
    }

    /**
     * A disconnect action was unsuccessful, notify user and update client history
     *
     * @param exception This argument is not used
     */
    private void disconnect(Throwable exception) {
        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        c.changeConnectionStatus(MQTTConnection.ConnectionStatus.DISCONNECTED);
        c.addAction("Disconnect Failed - an error occured");
        AppLog.error(TAG, exception, "MQTT Disconnect Failed %s", connection);
    }

    /**
     * A connect action was unsuccessful, notify the user and update client history
     *
     * @param exception This argument is not used
     */
    private void connect(Throwable exception) {
        MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
        c.changeConnectionStatus(MQTTConnection.ConnectionStatus.ERROR);
        c.addAction("Client failed to connect");
        AppLog.error(TAG, exception, "MQTT Client failed to connect %s", connection);
    }

}
