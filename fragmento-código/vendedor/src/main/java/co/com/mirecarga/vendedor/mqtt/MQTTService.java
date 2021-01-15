package co.com.mirecarga.vendedor.mqtt;

/**
 * Servicio de suscripción a MQTT.
 */
public interface MQTTService {
    /**
     * Inicia la suscripción al tópico.
     * @param topic el tópico a suscribir.
     */
    void suscribir(String topic);


    void addReceivedMessageListner(IReceivedMessageListener listener);

    void removeReceivedMessageListner(IReceivedMessageListener listener);

}
