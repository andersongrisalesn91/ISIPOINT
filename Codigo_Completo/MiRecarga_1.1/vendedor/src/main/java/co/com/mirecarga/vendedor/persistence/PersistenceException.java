package co.com.mirecarga.vendedor.persistence;

import co.com.mirecarga.vendedor.mqtt.MQTTConnection;

/**
 * Persistence Exception, defines an error with persisting a {@link MQTTConnection}
 * fails. Example operations are {@link Persistence#persistConnection(MQTTConnection)} and {@link Persistence#restoreConnections(android.content.Context)};
 * these operations throw this exception to indicate unexpected results occurred when performing actions on the database.
 *
 */
public class PersistenceException extends Exception {

    /**
     * Creates a persistence exception with the given error message
     * @param message The error message to display
     */
    public PersistenceException(String message) {
        super(message);
    }

    /** Serialisation ID**/
    private static final long serialVersionUID = 5326458803268855071L;

}
