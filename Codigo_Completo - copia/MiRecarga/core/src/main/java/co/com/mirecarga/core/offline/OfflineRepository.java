package co.com.mirecarga.core.offline;

import java.util.List;

/**
 * Contrato para el servicio de persistencia.
 */
public interface OfflineRepository {
    /**
     * Almacena el mensaje en el repositorio.
     * @param message los datos del mensaje
     */
    void guardar(OfflineMessage message);

    /**
     * Consulta los mensajes por tipo.
     * @param tipo el tipo de mensaje
     * @return la lista de mensajes
     */
    List<OfflineMessage> consultarPorTipo(String tipo);

    /**
     * Elimina el mensaje.
     * @param id el id del mensaje
     */
    void eliminar(long id);

    /**
     * Indica si se encuentra conectado a internet.
     * @return true si está conectado
     */
    boolean isConnected();
}
