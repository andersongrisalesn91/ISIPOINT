package co.com.mirecarga.vendedor.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.inject.Singleton;

import co.com.mirecarga.vendedor.mqtt.MQTTConnection;

@Singleton
public class Persistence_Connections extends Persistence {

    /**
     * Table column for ID
     **/
    private final String CONNECTIONS_COLUMN_ID = "_id";

    /**
     * Table column for client handle
     **/
    private final String CONNECTIONS_COLUMN_CLIENT_HANDLE = "clientHandle";

    /**
     * Table column for host
     **/
    private final String CONNECTIONS_COLUMN_HOST = "host";

    /**
     * Table column for client id
     **/
    private final String CONNECTIONS_COLUMN_CLIENT_ID = "clientID";

    /**
     * Table column for port
     **/
    private final String CONNECTIONS_COLUMN_PORT = "port";

    /**
     * Table column for ssl enabled
     **/
    private final String CONNECTIONS_COLUMN_SSL = "ssl";

    /**
     * Table column for client's timeout
     **/
    private final String CONNECTIONS_COLUMN_TIME_OUT = "timeout";

    /**
     * Table column for client's keepalive
     **/
    private final String CONNECTIONS_COLUMN_KEEP_ALIVE = "keepalive";

    /**
     * Table column for the client's username
     **/
    private final String CONNECTIONS_COLUMN_USER_NAME = "username";

    /**
     * Table column for the client's password
     **/
    private final String CONNECTIONS_COLUMN_PASSWORD = "password";

    /**
     * Table column for clean session
     **/
    private final String CONNECTIONS_COLUMN_CLEAN_SESSION = "cleanSession";

    /**
     * Table column for last will topic
     **/
    private final String CONNECTIONS_COLUMN_TOPIC = "topic";

    /**
     * Table column for the last will message payload
     **/
    private final String CONNECTIONS_COLUMN_MESSAGE = "message";

    /**
     * Table column for the last will message qos
     **/
    private final String CONNECTIONS_COLUMN_QOS = "qos";

    /**
     * Table column for the retained state of the message
     **/
    private final String CONNECTIONS_COLUMN_RETAINED = "retained";

    /**
     * Cursor con la seleccion obtenida de la tabla
     **/
    private Cursor cursor;

    //columns to return
    private String[] connectionColumns = {
            CONNECTIONS_COLUMN_ID,
            CONNECTIONS_COLUMN_CLIENT_HANDLE,
            CONNECTIONS_COLUMN_HOST,
            CONNECTIONS_COLUMN_CLIENT_ID,
            CONNECTIONS_COLUMN_PORT,
            CONNECTIONS_COLUMN_SSL,
            CONNECTIONS_COLUMN_TIME_OUT,
            CONNECTIONS_COLUMN_KEEP_ALIVE,
            CONNECTIONS_COLUMN_USER_NAME,
            CONNECTIONS_COLUMN_PASSWORD,
            CONNECTIONS_COLUMN_CLEAN_SESSION,
            CONNECTIONS_COLUMN_TOPIC,
            CONNECTIONS_COLUMN_MESSAGE,
            CONNECTIONS_COLUMN_QOS,
            CONNECTIONS_COLUMN_RETAINED
    };

    /**
     * Table connections
     **/
    final String SQL_CREATE_CONNECTIONS_ENTRIES =
            CREATE_TABLE + TABLE_CONNECTIONS + PARENTHESIS_INI +
                    CONNECTIONS_COLUMN_ID + INT_TYPE + CONSTRAINT_PK + COMMA_SEP +
                    CONNECTIONS_COLUMN_CLIENT_HANDLE + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_HOST + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_CLIENT_ID + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_PORT + INT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_SSL + INT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_TIME_OUT + INT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_KEEP_ALIVE + INT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_CLEAN_SESSION + INT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_TOPIC + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_QOS + INT_TYPE + COMMA_SEP +
                    CONNECTIONS_COLUMN_RETAINED + INT_TYPE +
                    PARENTHESIS_FSC;

    /**
     * Creates the persistence object passing it a context
     *
     * @param context Context that the application is running in
     */
    Persistence_Connections(final Context context) {
        super(context);
    }

    public String getCONNECTIONS_COLUMN_ID() {
        return CONNECTIONS_COLUMN_ID;
    }

    public String getCONNECTIONS_COLUMN_CLIENT_HANDLE() {
        return CONNECTIONS_COLUMN_CLIENT_HANDLE;
    }

    public String getCONNECTIONS_COLUMN_HOST() {
        return CONNECTIONS_COLUMN_HOST;
    }

    public String getCONNECTIONS_COLUMN_CLIENT_ID() {
        return CONNECTIONS_COLUMN_CLIENT_ID;
    }

    public String getCONNECTIONS_COLUMN_PORT() {
        return CONNECTIONS_COLUMN_PORT;
    }

    public String getCONNECTIONS_COLUMN_SSL() {
        return CONNECTIONS_COLUMN_SSL;
    }

    public String getCONNECTIONS_COLUMN_TIME_OUT() {
        return CONNECTIONS_COLUMN_TIME_OUT;
    }

    public String getCONNECTIONS_COLUMN_KEEP_ALIVE() {
        return CONNECTIONS_COLUMN_KEEP_ALIVE;
    }

    public String getCONNECTIONS_COLUMN_USER_NAME() {
        return CONNECTIONS_COLUMN_USER_NAME;
    }

    public String getCONNECTIONS_COLUMN_PASSWORD() {
        return CONNECTIONS_COLUMN_PASSWORD;
    }

    public String getCONNECTIONS_COLUMN_CLEAN_SESSION() {
        return CONNECTIONS_COLUMN_CLEAN_SESSION;
    }

    public String getCONNECTIONS_COLUMN_TOPIC() {
        return CONNECTIONS_COLUMN_TOPIC;
    }

    public String getCONNECTIONS_COLUMN_MESSAGE() {
        return CONNECTIONS_COLUMN_MESSAGE;
    }

    public String getCONNECTIONS_COLUMN_QOS() {
        return CONNECTIONS_COLUMN_QOS;
    }

    public String getCONNECTIONS_COLUMN_RETAINED() {
        return CONNECTIONS_COLUMN_RETAINED;
    }

    public String[] getConnectionColumns() {
        return connectionColumns;
    }

    public String getSQL_CREATE_CONNECTIONS_ENTRIES() {
        return SQL_CREATE_CONNECTIONS_ENTRIES;
    }

    ContentValues getValues(MQTTConnection connection) {
        MqttConnectOptions conOpts = connection.getConnectionOptions();
        MqttMessage lastWill = conOpts.getWillMessage();
        ContentValues values = new ContentValues();

        //put the column values object
        values.put(CONNECTIONS_COLUMN_CLIENT_HANDLE, connection.handle());
        values.put(CONNECTIONS_COLUMN_HOST, connection.getHostName());
        values.put(CONNECTIONS_COLUMN_PORT, connection.getPort());
        values.put(CONNECTIONS_COLUMN_CLIENT_ID, connection.getId());
        values.put(CONNECTIONS_COLUMN_SSL, connection.isSSL());

        values.put(CONNECTIONS_COLUMN_KEEP_ALIVE, conOpts.getKeepAliveInterval());
        values.put(CONNECTIONS_COLUMN_TIME_OUT, conOpts.getConnectionTimeout());
        values.put(CONNECTIONS_COLUMN_USER_NAME, conOpts.getUserName());
        values.put(CONNECTIONS_COLUMN_TOPIC, conOpts.getWillDestination());

        //uses "condition ? trueValue: falseValue" for in line converting of values
        char[] password = conOpts.getPassword();
        values.put(CONNECTIONS_COLUMN_CLEAN_SESSION, conOpts.isCleanSession() ? 1 : 0); //convert boolean to int and then put in values
        values.put(CONNECTIONS_COLUMN_PASSWORD, password != null ? String.valueOf(password) : null); //convert char[] to String
        values.put(CONNECTIONS_COLUMN_MESSAGE, lastWill != null ? new String(lastWill.getPayload()) : null); // convert byte[] to string
        values.put(CONNECTIONS_COLUMN_QOS, lastWill != null ? lastWill.getQos() : 0);

        if (lastWill == null) {
            values.put(CONNECTIONS_COLUMN_RETAINED, 0);
        } else {
            values.put(CONNECTIONS_COLUMN_RETAINED, lastWill.isRetained() ? 1 : 0); //convert from boolean to int
        }
        return values;
    }

    /**
     * Persist a MQTTConnection to the database
     *
     * @param connection the connection to persist
     * @throws PersistenceException If storing the data fails
     */
    public void persistConnection(MQTTConnection connection) throws PersistenceException {
        SQLiteDatabase db = getWritableDatabase();

        //insert the values into the tables, returns the ID for the row
        long newRowId = db.insert(TABLE_CONNECTIONS, null, getValues(connection));

        db.close(); //close the db then deal with the result of the query

        if (newRowId == -1) {
            throw new PersistenceException("Failed to persist connection: " + connection.handle());
        } else { //Successfully persisted assigning persistenceID
            connection.assignPersistenceId(newRowId);
        }
    }

    /**
     * Updates a {@link MQTTConnection} in the database
     *
     * @param connection {@link MQTTConnection} to update
     */
    public void updateConnection(MQTTConnection connection) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = CONNECTIONS_COLUMN_ID + "=?";
        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(connection.persistenceId());
        db.update(TABLE_CONNECTIONS, getValues(connection), whereClause, whereArgs);
    }

    /**
     * Deletes a connection from the database
     *
     * @param connection The connection to delete from the database
     */
    public void deleteConnection(MQTTConnection connection) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_CONNECTIONS, CONNECTIONS_COLUMN_ID + "=?", new String[]{String.valueOf(connection.persistenceId())});
        db.close();
        //don't care if it failed, means it's not in the db therefore no need to delete
    }
}
