package co.com.mirecarga.vendedor.mqtt;

public interface IReceivedMessageListener {

    void onMessageReceived(ReceivedMessage message);
}