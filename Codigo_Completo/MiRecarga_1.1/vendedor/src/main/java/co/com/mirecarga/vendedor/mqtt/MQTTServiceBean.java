package co.com.mirecarga.vendedor.mqtt;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.login.TokenService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.persistence.Subscription;

/**
 * Servicio de suscripción a MQTT.
 */
@Singleton
public class MQTTServiceBean implements MQTTService {
    /**
     * El tag para el log.
     */
    private static final String TAG = "MQTTServiceBean";

    /**
     * El tag para el log.
     */
    private static final String CLIENT_HANDLE = "CLIENT_HANDLE";

    /**
     * El tag para el log.
     */
    private static final String CLIENT_ID = "CLIENT_ID";

    /**
     * El contexto de la aplicación.
     */
    private final transient Context context;

    private MQTTConnection connection;

    private TokenService tokenService;

    @Inject
    public MQTTServiceBean(final Context context, final TokenService tokenService) {
        this.tokenService = tokenService;
        this.context = context;
        this.connection = connect();
    }

    private MQTTConnection connect() {
        AppLog.debug(TAG, "MQTT Persisting new connection:" + CLIENT_HANDLE);
        MQTTConnection connection = MQTTConnection.createConnection(CLIENT_HANDLE, "CLIENT_ID", "64.76.89.167", 1883, context, false);
        // connection.registerChangeListener(changeListener);
        connection.changeConnectionStatus(MQTTConnection.ConnectionStatus.CONNECTING);


        String[] actionArgs = new String[1];
        actionArgs[0] = "CLIENT_ID";
        final ActionListener callback = new ActionListener(context,
                ActionListener.Action.CONNECT, connection, actionArgs);
        connection.getClient().setCallback(new MqttCallbackHandler(context, CLIENT_HANDLE));


        // connection.getClient().setTraceCallback(new MqttTraceCallback());
        //Seermebe valido tokenService no sea null para que se pueda suscribir un topico
        MqttConnectOptions connOpts = null;
        if (tokenService.getAccessToken() != null) {
            connOpts = optionsFromModel();
        }

        connection.addConnectionOptions(connOpts);
        MQTTConnections.getInstance(context).addConnection(connection);

        AppLog.debug(TAG, "MQTT conectando %s opts %s", connection, connOpts);
        try {
            connection.getClient().connect(connOpts, null, callback);
            AppLog.debug(TAG, "MQTT conectado %s", connection);
        } catch (MqttException e) {
            AppLog.error(TAG, e, "MQTT  MqttException occurred");
        }
        AppLog.debug(TAG, "MQTT fin connect %s", connection);
        return connection;
    }

    private MqttConnectOptions optionsFromModel() {

        MqttConnectOptions connOpts = new MqttConnectOptions();
        // connOpts.setCleanSession(model.isCleanSession());
        // connOpts.setConnectionTimeout(model.getTimeout());
        // connOpts.setKeepAliveInterval(model.getKeepAlive());
        connOpts.setUserName("oauth");

        // connOpts.setPassword("f3d76b4f-4d66-3e6c-9329-936b725eb99c".toCharArray());
        String accessToken = tokenService.getAccessToken();
        AppLog.debug(TAG, "MQTT pwd %s", accessToken);
        connOpts.setPassword(accessToken.toCharArray());
        //if(!model.getLwtTopic().equals(ActivityConstants.empty) && !model.getLwtMessage().equals(ActivityConstants.empty)){
        //    connOpts.setWill(model.getLwtTopic(), model.getLwtMessage().getBytes(), model.getLwtQos(), model.isLwtRetain());
        //}
        //   if(tlsConnection){
        //       // TODO Add Keys to conOpts here
        //       //connOpts.setSocketFactory();
        //   }
        return connOpts;
    }

    @Override
    public void suscribir(final String topic) {
        AppLog.debug(TAG, "MQTT Suscribir a %s", topic);
        final Subscription sub = new Subscription(topic, 0, CLIENT_HANDLE, false);
        try {
            connection.addNewSubscription(sub);
            AppLog.debug(TAG, "MQTT Suscrito a %s", topic);
        } catch (MqttException e) {
            AppLog.error(TAG, e, "MQTT Error al suscribir %s", topic);
        }
    }

    @Override
    public void addReceivedMessageListner(final IReceivedMessageListener listener) {
        connection.addReceivedMessageListner(listener);
    }

    @Override
    public void removeReceivedMessageListner(final IReceivedMessageListener listener) {
        connection.removeReceivedMessageListner(listener);
    }
}
