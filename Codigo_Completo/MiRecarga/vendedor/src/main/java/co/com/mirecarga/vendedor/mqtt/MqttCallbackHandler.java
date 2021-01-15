/*******************************************************************************
 * Copyright (c) 1999, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 */
package co.com.mirecarga.vendedor.mqtt;

import android.content.Context;
import android.util.Log;

//import org.eclipse.paho.android.sample.MQTTConnection.ConnectionStatus;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Handles call backs from the MQTT Client.
 *
 */
class MqttCallbackHandler implements MqttCallback {

  /** {@link Context} for the application used to format and import external strings**/
  private final Context context;
  /** Client handle to reference the connection that this handler is attached to**/
  private final String clientHandle;

  private static final String TAG = "MqttCallbackHandler";
    private static final String activityClass = "org.eclipse.paho.android.sample.activity.MainActivity";

  /**
   * Creates an <code>MqttCallbackHandler</code> object
   * @param context The application's context
   * @param clientHandle The handle to a {@link MQTTConnection} object
   */
  public MqttCallbackHandler(Context context, String clientHandle)
  {
    this.context = context;
    this.clientHandle = clientHandle;
  }

  /**
   * @see MqttCallback#connectionLost(Throwable)
   */
  @Override
  public void connectionLost(Throwable cause) {
    if (cause != null) {
      Log.d(TAG, "MQTT Connection Lost: " + cause.getMessage());
      MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
      c.addAction("MQTTConnection Lost");
      c.changeConnectionStatus(MQTTConnection.ConnectionStatus.DISCONNECTED);
    }
  }

  /**
   * @see MqttCallback#messageArrived(String, MqttMessage)
   */
  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {

    //Get connection object associated with this object
    MQTTConnection c = MQTTConnections.getInstance(context).getConnection(clientHandle);
    c.messageArrived(topic, message);
    //get the string from strings.xml and format
    String messageString = String.format("MQTT recibido %s topic %s qos %s retained %s", new String(message.getPayload()), topic, message.getQos(), message.isRetained());

    Log.i(TAG, messageString);

    //update client history
    c.addAction(messageString);
  }

  /**
   * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
   */
  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    // Do nothing
  }

}
