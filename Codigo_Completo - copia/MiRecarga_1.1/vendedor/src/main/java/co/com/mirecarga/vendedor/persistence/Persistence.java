package co.com.mirecarga.vendedor.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.com.mirecarga.vendedor.mqtt.MQTTConnection;
import co.com.mirecarga.vendedor.mqtt.MQTTConnections;

/**
 * <code>Persistence</code> deals with interacting with the database to persist
 * {@link MQTTConnection} objects so created clients survive, the destruction of the
 * singleton {@link MQTTConnections} object.
 */
public class Persistence extends SQLiteOpenHelper {

    private static final String TAG = Persistence.class.getSimpleName();

    /**
     * The version of the database
     **/
    private static final int DATABASE_VERSION = 1;

    /**
     * The name of the database file
     **/
    private static final String DATABASE_NAME = "connections.db";

//    /**
//     * Table column for client handle
//     **/
//    private static final String COLUMN_CLIENT_HANDLE = "clientHandle";

//    /**
//     * Table column for host
//     **/
//    private static final String COLUMN_HOST = "host";
//    /**
//     * Table column for client id
//     **/
//    private static final String COLUMN_CLIENT_ID = "clientID";
//    /**
//     * Table column for port
//     **/
//    private static final String COLUMN_PORT = "port";
//    /**
//     * Table column for ssl enabled
//     **/
//    private static final String COLUMN_SSL = "ssl";

    //connection options
//    /**
//     * Table column for client's timeout
//     **/
//    private static final String COLUMN_TIME_OUT = "timeout";
//    /**
//     * Table column for client's keepalive
//     **/
//    private static final String COLUMN_KEEP_ALIVE = "keepalive";
//    /**
//     * Table column for the client's username
//     **/
//    private static final String COLUMN_USER_NAME = "username";
//    /**
//     * Table column for the client's password
//     **/
//    private static final String COLUMN_PASSWORD = "password";
//    /**
//     * Table column for clean session
//     **/
//    private static final String COLUMN_CLEAN_SESSION = "cleanSession";

    //last will
//    /**
//     * Table column for last will topic
//     **/
//    private static final String COLUMN_TOPIC = "topic";
//    /**
//     * Table column for the last will message payload
//     **/
//    private static final String COLUMN_MESSAGE = "message";
//    /**
//     * Table column for the last will message qos
//     **/
//    private static final String COLUMN_QOS = "qos";
//    /**
//     * Table column for the retained state of the message
//     **/
//    private static final String COLUMN_RETAINED = "retained";

    /**
     * The name of the connections table
     **/
    protected final String TABLE_CONNECTIONS = "connections";

    /**
     * The name of the subscriptions table
     **/
    protected final String TABLE_SUBSCRIPTIONS = "subscriptions";


    /**
     * El nombre de la tabla para el detalle de la transacción
     **/
    private static final String TABLE_API = "api";

    /**
     * Columna de la tabla para id_api
     **/
    private static final String API_COLUMN_ID_API = "id_api";
    /**
     * Columna de la tabla para end_point
     **/
    private static final String API_COLUMN_END_POINT = "end_point";

    /**
     * El nombre de la tabla para el detalle de la transacción
     **/
    private static final String TABLE_DETALLE_TRANSACCION = "detalle_transaccion";

    /**
     * Columna de la tabla para id_detalle_transaccion
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_ID_DETALLE_TRANSACCION = "id_detalle_transaccion";
    /**
     * Columna de la tabla para idtarifa
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_IDTARIFA = "idtarifa";
    /**
     * Columna de la tabla para costoxminuto
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_COSTOXMINUTO = "costoxminuto";
    /**
     * Columna de la tabla para fechahoraentrada
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_FECHAHORAENTRADA = "fechahoraentrada";
    /**
     * Columna de la tabla para fechahorasalida
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_FECHAHORASALIDA = "fechahorasalida";
    /**
     * Columna de la tabla para cerrado
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_CERRADO = "cerrado";
    /**
     * Columna de la tabla para pagado
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_PAGADO = "pagado";
    /**
     * Columna de la tabla para costototal
     **/
    private static final String DETALLE_TRANSACCION_COLUMN_COSTOTOTAL = "costototal";


    /**
     * El nombre de la tabla para entidad
     **/
    protected static final String TABLE_ENTIDAD = "entidad";

    /**
     * Columna de la tabla para id_entidad
     **/
    protected static final String ENTIDAD_COLUMN_ID_ENTIDAD = "id_entidad";
    /**
     * Columna de la tabla para id_vendedor
     **/
    protected static final String ENTIDAD_COLUMN_ID_VENDEDOR = "id_vendedor";
    /**
     * Columna de la tabla para login
     **/
    protected static final String ENTIDAD_COLUMN_LOGIN = "login";
    /**
     * Columna de la tabla para correoElectronico
     **/
    protected static final String ENTIDAD_COLUMN_CORREOELECTRONICO = "correoElectronico";
    /**
     * Columna de la tabla para nombreCompleto
     **/
    protected static final String ENTIDAD_COLUMN_NOMBRECOMPLETO = "nombreCompleto";
    /**
     * Columna de la tabla para proveedorAutenticacion
     **/
    protected static final String ENTIDAD_COLUMN_PROVEEDORAUTENTICACION = "proveedorAutenticacion";
    /**
     * Columna de la tabla para id_suscriber
     **/
    protected static final String ENTIDAD_COLUMN_ID_SUSCRIBER = "id_suscriber";
    /**
     * Columna de la tabla para id_entidad_sys
     **/
    protected static final String ENTIDAD_COLUMN_ID_ENTIDAD_SYS = "id_entidad_sys";

    /**
     * El nombre de la tabla para estado
     **/
    private static final String TABLE_ESTADO = "estado";

    /**
     * Columna de la tabla para id_estado
     **/
    private static final String ESTADO_COLUMN_ID_ESTADO = "id_estado";
    /**
     * Columna de la tabla para estado
     **/
    private static final String ESTADO_COLUMN_ESTADO = "estado";

    /**
     * El nombre de la tabla paraevento
     **/
    private static final String TABLE_EVENTO = "evento";

    /**
     * Columna de la tabla para id_evento
     **/
    private static final String EVENTO_COLUMN_ID_EVENTO = "id_evento";
    /**
     * Columna de la tabla para evt
     **/
    private static final String EVENTO_COLUMN_EVT = "evt";
    /**
     * Columna de la tabla para ts
     **/
    private static final String EVENTO_COLUMN_TS = "ts";
    /**
     * Columna de la tabla para idp
     **/
    private static final String EVENTO_COLUMN_IDP = "idp";
    /**
     * Columna de la tabla para idd
     **/
    private static final String EVENTO_COLUMN_IDD = "idd";
    /**
     * Columna de la tabla para idm
     **/
    private static final String EVENTO_COLUMN_IDM = "idm";
    /**
     * Columna de la tabla para pyzid
     **/
    private static final String EVENTO_COLUMN_PYZID = "pyzid";
    /**
     * Columna de la tabla para cid
     **/
    private static final String EVENTO_COLUMN_CID = "cid";
    /**
     * Columna de la tabla para tid
     **/
    private static final String EVENTO_COLUMN_TID = "tid";

    /**
     * El nombre de la tabla para gps_frecuente
     **/
    private static final String TABLE_GPS_FRECUENTE = "gps_frecuente";

    /**
     * Columna de la tabla para id_gps_frecuente
     **/
    private static final String GPS_FRECUENTE_COLUMN_ID_GPS_FRECUENTE = "id_gps_frecuente";
    /**
     * Columna de la tabla para fecha
     **/
    private static final String GPS_FRECUENTE_COLUMN_FECHA = "fecha";
    /**
     * Columna de la tabla para id_localizacion
     **/
    private static final String GPS_FRECUENTE_COLUMN_ID_LOCALIZACION = "id_localizacion";
    /**
     * Columna de la tabla para id_usuario
     **/
    private static final String GPS_FRECUENTE_COLUMN_ID_USUARIO = "id_usuario";

    /**
     * El nombre de la tabla para instrumentalizacion_api
     **/
    private static final String TABLE_INSTRUMENTALIZACION_API = "instrumentalizacion_api";

    /**
     * Columna de la tabla para id_instrumentalizacion_api
     **/
    private static final String INSTRUMENTALIZACION_API_COLUMN_ID_INSTRUMENTALIZACION_API = "id_instrumentalizacion_api";
    /**
     * Columna de la tabla para id_usuario
     **/
    private static final String INSTRUMENTALIZACION_API_COLUMN_ID_USUARIO = "id_usuario";
    /**
     * Columna de la tabla para id_api
     **/
    private static final String INSTRUMENTALIZACION_API_COLUMN_ID_API = "id_api";
    /**
     * Columna de la tabla para fecha_consumo
     **/
    private static final String INSTRUMENTALIZACION_API_COLUMN_FECHA_CONSUMO = "fecha_consumo";

    /**
     * El nombre de la tabla para instrumentalizacion_api
     **/
    private static final String TABLE_INSTRUMENTALIZACION_WIDGET = "instrumentalizacion_widget";

    /**
     * Columna de la tabla para id_instrumentalizacion_widget
     **/
    private static final String INSTRUMENTALIZACION_WIDGET_COLUMN_ID_INSTRUMENTALIZACION_WIDGET = "id_instrumentalizacion_widget";
    /**
     * Columna de la tabla para fecha_uso
     **/
    private static final String INSTRUMENTALIZACION_WIDGET_COLUMN_FECHA_USO = "fecha_uso";
    /**
     * Columna de la tabla para id_widget
     **/
    private static final String INSTRUMENTALIZACION_WIDGET_COLUMN_ID_WIDGET = "id_widget";
    /**
     * Columna de la tabla para id_usuario
     **/
    private static final String INSTRUMENTALIZACION_WIDGET_COLUMN_ID_USUARIO = "id_usuario";

    /**
     * El nombre de la tabla para kmz
     **/
    private static final String TABLE_KMZ = "kmz";

    /**
     * Columna de la tabla para id_kmz
     **/
    private static final String KMZ_COLUMN_ID_KMZ = "id_kmz";
    /**
     * Columna de la tabla para zone
     **/
    private static final String KMZ_COLUMN_ZONE = "zone";
    /**
     * Columna de la tabla para cell
     **/
    private static final String KMZ_COLUMN_CELL = "cell";

    /**
     * El nombre de la tabla para lista_detalle
     **/
    private static final String TABLE_LISTA_DETALLE = "lista_detalle";

    /**
     * Columna de la tabla para id_transaccion
     **/
    private static final String LISTA_DETALLE_COLUMN_ID_TRANSACCION = "id_transaccion";
    /**
     * Columna de la tabla para id_detalle_transaccion
     **/
    private static final String LISTA_DETALLE_COLUMN_ID_DETALLE_TRANSACCION = "id_detalle_transaccion";

    /**
     * El nombre de la tabla para localizacion
     **/
    private static final String TABLE_LOCALIZACION = "localizacion";

    /**
     * Columna de la tabla para id_localizacion
     **/
    private static final String LOCALIZACION_COLUMN_ID_LOCALIZACION = "id_localizacion";
    /**
     * Columna de la tabla para accuracy
     **/
    private static final String LOCALIZACION_COLUMN_ACCURACY = "accuracy";
    /**
     * Columna de la tabla para altitude
     **/
    private static final String LOCALIZACION_COLUMN_ALTITUDE = "altitude";
    /**
     * Columna de la tabla para bearing
     **/
    private static final String LOCALIZACION_COLUMN_BEARING = "bearing";
    /**
     * Columna de la tabla para bearing_accuracy_degrees
     **/
    private static final String LOCALIZACION_COLUMN_BEARING_ACCURACY_DEGREES = "bearing_accuracy_degrees";
    /**
     * Columna de la tabla para elapsed_realtime_nanos
     **/
    private static final String LOCALIZACION_COLUMN_ELAPSED_REALTIME_NANOS = "elapsed_realtime_nanos";
    /**
     * Columna de la tabla para elapsed_realtime_uncertainty_nanos
     **/
    private static final String LOCALIZACION_COLUMN_ELAPSED_REALTIME_UNCERTAINTY_NANOS = "elapsed_realtime_uncertainty_nanos";
    /**
     * Columna de la tabla para extras_satellites
     **/
    private static final String LOCALIZACION_COLUMN_EXTRAS_SATELLITES = "extras_satellites";
    /**
     * Columna de la tabla para latitude
     **/
    private static final String LOCALIZACION_COLUMN_LATITUDE = "latitude";
    /**
     * Columna de la tabla para longitude
     **/
    private static final String LOCALIZACION_COLUMN_LONGITUDE = "longitude";
    /**
     * Columna de la tabla para provider
     **/
    private static final String LOCALIZACION_COLUMN_PROVIDER = "provider";
    /**
     * Columna de la tabla para speed
     **/
    private static final String LOCALIZACION_COLUMN_SPEED = "speed";
    /**
     * Columna de la tabla para speed_accuracy_meters_per_second
     **/
    private static final String LOCALIZACION_COLUMN_SPEED_ACCURACY_METERS_PER_SECOND = "speed_accuracy_meters_per_second";
    /**
     * Columna de la tabla para time_gps
     **/
    private static final String LOCALIZACION_COLUMN_TIME_GPS = "time_gps";

    /**
     * El nombre de la tabla para notificacion
     **/
    protected static final String TABLE_NOTIFICACION = "notificacion";

    /**
     * Columna de la tabla para id_notificacion
     **/
    protected static final String NOTIFICACION_COLUMN_ID_NOTIFICACION = "id_notificacion";
    /**
     * Columna de la tabla para title
     **/
    protected static final String NOTIFICACION_COLUMN_TITLE = "title";
    /**
     * Columna de la tabla para body
     **/
    protected static final String NOTIFICACION_COLUMN_BODY = "body";
    /**
     * Columna de la tabla para data
     **/
    protected static final String NOTIFICACION_COLUMN_DATA = "data";

    /**
     * El nombre de la tabla para parametrizacion
     **/
    private static final String TABLE_PARAMETRIZACION = "parametrizacion";

    /**
     * Columna de la tabla para id_parametrizacion
     **/
    private static final String PARAMETRIZACION_COLUMN_ID_PARAMETRIZACION = "id_parametrizacion";
    /**
     * Columna de la tabla para parametro
     **/
    private static final String PARAMETRIZACION_COLUMN_PARAMETRO = "parametro";
    /**
     * Columna de la tabla para dato
     **/
    private static final String PARAMETRIZACION_COLUMN_DATO = "dato";

    /**
     * El nombre de la tabla para ping
     **/
    private static final String TABLE_PING = "ping";

    /**
     * Columna de la tabla para id_ping
     **/
    private static final String PING_COLUMN_ID_PING = "id_ping";
    /**
     * Columna de la tabla para fecha
     **/
    private static final String PING_COLUMN_FECHA = "fecha";

    /**
     * El nombre de la tabla para sesion
     **/
    private static final String TABLE_SESION = "sesion";

    /**
     * Columna de la tabla para id_sesion
     **/
    private static final String SESION_COLUMN_ID_SESION = "id_sesion";
    /**
     * Columna de la tabla para estado
     **/
    private static final String SESION_COLUMN_ESTADO = "estado";

    /**
     * El nombre de la tabla para tipo_widget
     **/
    private static final String TABLE_TIPO_WIDGET = "tipo_widget";

    /**
     * Columna de la tabla para id_tipo_widget
     **/
    private static final String TIPO_WIDGET_COLUMN_ID_TIPO_WIDGET = "id_tipo_widget";
    /**
     * Columna de la tabla para tipo
     **/
    private static final String TIPO_WIDGET_COLUMN_TIPO = "tipo";

    /**
     * El nombre de la tabla para transaccion
     **/
    private static final String TABLE_TRANSACCION = "transaccion";

    /**
     * Columna de la tabla para id_transaccion
     **/
    private static final String TRANSACCION_COLUMN_ID_TRANSACCION = "id_transaccion";
    /**
     * Columna de la tabla para tid
     **/
    private static final String TRANSACCION_COLUMN_TID = "tid";
    /**
     * Columna de la tabla para parqueaderoyzona
     **/
    private static final String TRANSACCION_COLUMN_PARQUEADEROYZONA = "parqueaderoyzona";
    /**
     * Columna de la tabla para idtipodevehiculo
     **/
    private static final String TRANSACCION_COLUMN_IDTIPODEVEHICULO = "idtipodevehiculo";
    /**
     * Columna de la tabla para placa
     **/
    private static final String TRANSACCION_COLUMN_PLACA = "placa";
    /**
     * Columna de la tabla para idcelda
     **/
    private static final String TRANSACCION_COLUMN_IDCELDA = "idcelda";
    /**
     * Columna de la tabla para fechahoraentrada
     **/
    private static final String TRANSACCION_COLUMN_FECHAHORAENTRADA = "fechahoraentrada";
    /**
     * Columna de la tabla para fechahoravigencia
     **/
    private static final String TRANSACCION_COLUMN_FECHAHORAVIGENCIA = "fechahoravigencia";
    /**
     * Columna de la tabla para hms
     **/
    private static final String TRANSACCION_COLUMN_HMS = "hms";
    /**
     * Columna de la tabla para dias
     **/
    private static final String TRANSACCION_COLUMN_DIAS = "dias";
    /**
     * Columna de la tabla para horas
     **/
    private static final String TRANSACCION_COLUMN_HORAS = "horas";
    /**
     * Columna de la tabla para minutos
     **/
    private static final String TRANSACCION_COLUMN_MINUTOS = "minutos";
    /**
     * Columna de la tabla para segundos
     **/
    private static final String TRANSACCION_COLUMN_SEGUNDOS = "segundos";
    /**
     * Columna de la tabla para fechahorasalida
     **/
    private static final String TRANSACCION_COLUMN_FECHAHORASALIDA = "fechahorasalida";
    /**
     * Columna de la tabla para pagado
     **/
    private static final String TRANSACCION_COLUMN_PAGADO = "pagado";
    /**
     * Columna de la tabla para pagopendiente
     **/
    private static final String TRANSACCION_COLUMN_PAGOPENDIENTE = "pagopendiente";

    /**
     * El nombre de la tabla para traza_evento
     **/
    private static final String TABLE_TRAZA_EVENTO = "traza_evento";

    /**
     * Columna de la tabla para id_traza_evento
     **/
    private static final String TRAZA_EVENTO_COLUMN_ID_TRAZA_EVENTO = "id_traza_evento";
    /**
     * Columna de la tabla para id_estado
     **/
    private static final String TRAZA_EVENTO_COLUMN_ID_ESTADO = "id_estado";
    /**
     * Columna de la tabla para fecha
     **/
    private static final String TRAZA_EVENTO_COLUMN_FECHA = "fecha";
    /**
     * Columna de la tabla para id_evento
     **/
    private static final String TRAZA_EVENTO_COLUMN_ID_EVENTO = "id_evento";
    /**
     * Columna de la tabla para id_localizacion
     **/
    private static final String TRAZA_EVENTO_COLUMN_ID_LOCALIZACION = "id_localizacion";
    /**
     * Columna de la tabla para id_usuario
     **/
    private static final String TRAZA_EVENTO_COLUMN_ID_USUARIO = "id_usuario";

    /**
     * El nombre de la tabla para traza_notificacion
     **/
    private static final String TABLE_TRAZA_NOTIFICACION = "traza_notificacion";

    /**
     * Columna de la tabla para id_traza_notificacion
     **/
    private static final String TRAZA_NOTIFICACION_COLUMN_ID_TRAZA_NOTIFICACION = "id_traza_notificacion";
    /**
     * Columna de la tabla para id_notificacion
     **/
    private static final String TRAZA_NOTIFICACION_COLUMN_ID_NOTIFICACION = "id_notificacion";
    /**
     * Columna de la tabla para id_estado
     **/
    private static final String TRAZA_NOTIFICACION_COLUMN_ID_ESTADO = "id_estado";
    /**
     * Columna de la tabla para fecha
     **/
    private static final String TRAZA_NOTIFICACION_COLUMN_FECHA = "fecha";
    /**
     * Columna de la tabla para id_localizacion
     **/
    private static final String TRAZA_NOTIFICACION_COLUMN_ID_LOCALIZACION = "id_localizacion";
    /**
     * Columna de la tabla para id_usuario
     **/
    private static final String TRAZA_NOTIFICACION_COLUMN_ID_USUARIO = "id_usuario";

    /**
     * El nombre de la tabla para widget
     **/
    private static final String TABLE_WIDGET = "widget";

    /**
     * Columna de la tabla para id_widget
     **/
    private static final String WIDGET_COLUMN_ID_WIDGET = "id_widget";
    /**
     * Columna de la tabla para id_sys
     **/
    private static final String WIDGET_COLUMN_ID_SYS = "id_sys";
    /**
     * Columna de la tabla para nombre
     **/
    private static final String WIDGET_COLUMN_NOMBRE = "nombre";
    /**
     * Columna de la tabla para id_tipo_widget
     **/
    private static final String WIDGET_COLUMN_ID_TIPO_WIDGET = "id_tipo_widget";
    /**
     * Columna de la tabla para contenedor
     **/
    private static final String WIDGET_COLUMN_CONTENEDOR = "contenedor";
    /**
     * Columna de la tabla para funcion
     **/
    private static final String WIDGET_COLUMN_FUNCION = "funcion";

    //sql lite data types
    /**
     * Text type for SQLite
     **/
    protected static final String TEXT_TYPE = " TEXT";
    /**
     * Int type for SQLite
     **/
    protected static final String INT_TYPE = " INTEGER";
    /**
     * Float type for SQLite
     **/
    protected static final String REAL_TYPE = " REAL";
    /**
     * Comma separator
     **/
    protected static final String COMMA_SEP = ",";
    /**
     * Comma separator
     **/
    protected static final String SEMI_COLON = ";";
    /**
     * Initial parenthesis
     **/
    protected static final String PARENTHESIS_INI = " (";
    /**
     * Final parenthesis
     **/
    protected static final String PARENTHESIS_FIN = ")";
    /**
     * Final parenthesis and semicolon
     **/
    protected static final String PARENTHESIS_FSC = ");";
    /**
     * Table constraint primary key
     **/
    protected static final String CONSTRAINT_PK = " PRIMARY KEY";
    /**
     * Table constraint foreign key
     **/
    protected static final String CONSTRAINT_FK = " FOREIGN KEY (";
    /**
     * Table constraint reference
     **/
    protected static final String CONSTRAINT_REF = ") REFERENCES ";
    /**
     * Table NULL
     **/
    protected static final String NULL = " NULL";
    /**
     * Table NOT NULL
     **/
    protected static final String NOT_NULL = " NOT NULL";
    /**
     * Create table
     **/
    protected static final String CREATE_TABLE = "CREATE TABLE ";
    /**
     * Autoincrement
     **/
    protected static final String AUTOINCREMENT = " AUTOINCREMENT";
    /**
     * Drop table
     **/
    protected static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    //Create tables query

    /**
     * Table connections
     **/
    @Inject
    public Persistence_Connections pCon;
    /**
     * Table suscriptions
     **/
    @Inject
    public Persistence_Subscriptions pSus;

    /**
     * Table kmz
     **/
    private static final String SQL_CREATE_KMZ_ENTRIES =
            CREATE_TABLE + TABLE_KMZ + PARENTHESIS_INI +
                    KMZ_COLUMN_ID_KMZ + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    KMZ_COLUMN_ZONE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    KMZ_COLUMN_CELL + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table tipo_widget
     **/
    private static final String SQL_CREATE_TIPO_WIDGET_ENTRIES =
            CREATE_TABLE + TABLE_TIPO_WIDGET + PARENTHESIS_INI +
                    TIPO_WIDGET_COLUMN_ID_TIPO_WIDGET + INT_TYPE + CONSTRAINT_PK +
                    AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    TIPO_WIDGET_COLUMN_TIPO + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table widget
     **/
    private static final String SQL_CREATE_WIDGET_ENTRIES =
            CREATE_TABLE + TABLE_WIDGET + PARENTHESIS_INI +
                    WIDGET_COLUMN_ID_WIDGET + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    WIDGET_COLUMN_ID_SYS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    WIDGET_COLUMN_NOMBRE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    WIDGET_COLUMN_ID_TIPO_WIDGET + INT_TYPE + NOT_NULL + COMMA_SEP +
                    WIDGET_COLUMN_CONTENEDOR + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    WIDGET_COLUMN_FUNCION + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + WIDGET_COLUMN_ID_TIPO_WIDGET + CONSTRAINT_REF + TABLE_TIPO_WIDGET + PARENTHESIS_INI + TIPO_WIDGET_COLUMN_ID_TIPO_WIDGET + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table entidad
     **/
    private static final String SQL_CREATE_ENTIDAD_ENTRIES =
            CREATE_TABLE + TABLE_ENTIDAD + PARENTHESIS_INI +
                    ENTIDAD_COLUMN_ID_ENTIDAD + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_ID_VENDEDOR + INT_TYPE + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_LOGIN + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_CORREOELECTRONICO + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_NOMBRECOMPLETO + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_PROVEEDORAUTENTICACION + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_ID_SUSCRIBER + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ENTIDAD_COLUMN_ID_ENTIDAD_SYS + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table instrumentalizacion_widget
     **/
    private static final String SQL_CREATE_INSTRUMENTALIZACION_WIDGET_ENTRIES =
            CREATE_TABLE + TABLE_INSTRUMENTALIZACION_WIDGET + PARENTHESIS_INI +
                    INSTRUMENTALIZACION_WIDGET_COLUMN_ID_INSTRUMENTALIZACION_WIDGET + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    INSTRUMENTALIZACION_WIDGET_COLUMN_FECHA_USO + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    INSTRUMENTALIZACION_WIDGET_COLUMN_ID_WIDGET + INT_TYPE + NOT_NULL + COMMA_SEP +
                    INSTRUMENTALIZACION_WIDGET_COLUMN_ID_USUARIO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + INSTRUMENTALIZACION_WIDGET_COLUMN_ID_WIDGET + CONSTRAINT_REF + TABLE_WIDGET + PARENTHESIS_INI + WIDGET_COLUMN_ID_WIDGET + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + INSTRUMENTALIZACION_WIDGET_COLUMN_ID_USUARIO + CONSTRAINT_REF + TABLE_ENTIDAD + PARENTHESIS_INI + ENTIDAD_COLUMN_ID_ENTIDAD + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table localizacion
     **/
    private static final String SQL_CREATE_LOCALIZACION_ENTRIES =
            CREATE_TABLE + TABLE_LOCALIZACION + PARENTHESIS_INI +
                    LOCALIZACION_COLUMN_ID_LOCALIZACION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_ACCURACY + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_ALTITUDE + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_BEARING + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_BEARING_ACCURACY_DEGREES + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_ELAPSED_REALTIME_NANOS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_ELAPSED_REALTIME_UNCERTAINTY_NANOS + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_EXTRAS_SATELLITES + INT_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_LATITUDE + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_LONGITUDE + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_PROVIDER + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_SPEED + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_SPEED_ACCURACY_METERS_PER_SECOND + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    LOCALIZACION_COLUMN_TIME_GPS + INT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table transaccion
     **/
    private static final String SQL_CREATE_TRANSACCION_ENTRIES =
            CREATE_TABLE + TABLE_TRANSACCION + PARENTHESIS_INI +
                    TRANSACCION_COLUMN_ID_TRANSACCION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_TID + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_PARQUEADEROYZONA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_IDTIPODEVEHICULO + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_PLACA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_IDCELDA + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_FECHAHORAENTRADA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_FECHAHORAVIGENCIA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_HMS + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_DIAS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_HORAS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_MINUTOS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_SEGUNDOS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_FECHAHORASALIDA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_PAGADO + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRANSACCION_COLUMN_PAGOPENDIENTE + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table parametrizacion
     **/
    private static final String SQL_CREATE_PARAMETRIZACION_ENTRIES =
            CREATE_TABLE + TABLE_PARAMETRIZACION + PARENTHESIS_INI +
                    PARAMETRIZACION_COLUMN_ID_PARAMETRIZACION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    PARAMETRIZACION_COLUMN_PARAMETRO + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    PARAMETRIZACION_COLUMN_DATO + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table sesion
     **/
    private static final String SQL_CREATE_SESION_ENTRIES =
            CREATE_TABLE + TABLE_SESION + PARENTHESIS_INI +
                    SESION_COLUMN_ID_SESION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    SESION_COLUMN_ESTADO + INT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table ping
     **/
    private static final String SQL_CREATE_PING_ENTRIES =
            CREATE_TABLE + TABLE_PING + PARENTHESIS_INI +
                    PING_COLUMN_ID_PING + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    PING_COLUMN_FECHA + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table evento
     **/
    private static final String SQL_CREATE_EVENTO_ENTRIES =
            CREATE_TABLE + TABLE_EVENTO + PARENTHESIS_INI +
                    EVENTO_COLUMN_ID_EVENTO + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_EVT + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_TS + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_IDP + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_IDD + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_IDM + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_PYZID + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_CID + INT_TYPE + NOT_NULL + COMMA_SEP +
                    EVENTO_COLUMN_TID + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + EVENTO_COLUMN_TID + CONSTRAINT_REF + TABLE_TRANSACCION + PARENTHESIS_INI + TRANSACCION_COLUMN_TID + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table estado
     **/
    private static final String SQL_CREATE_ESTADO_ENTRIES =
            CREATE_TABLE + TABLE_ESTADO + PARENTHESIS_INI +
                    ESTADO_COLUMN_ID_ESTADO + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    ESTADO_COLUMN_ESTADO + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table notificacion
     **/
    private static final String SQL_CREATE_NOTIFICACION_ENTRIES =
            CREATE_TABLE + TABLE_NOTIFICACION + PARENTHESIS_INI +
                    NOTIFICACION_COLUMN_ID_NOTIFICACION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    NOTIFICACION_COLUMN_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    NOTIFICACION_COLUMN_BODY + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    NOTIFICACION_COLUMN_DATA + TEXT_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table traza_notificacion
     **/
    private static final String SQL_CREATE_TRAZA_NOTIFICACION_ENTRIES =
            CREATE_TABLE + TABLE_TRAZA_NOTIFICACION + PARENTHESIS_INI +
                    TRAZA_NOTIFICACION_COLUMN_ID_TRAZA_NOTIFICACION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    TRAZA_NOTIFICACION_COLUMN_ID_NOTIFICACION + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRAZA_NOTIFICACION_COLUMN_ID_ESTADO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRAZA_NOTIFICACION_COLUMN_FECHA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRAZA_NOTIFICACION_COLUMN_ID_LOCALIZACION + INT_TYPE + NULL + COMMA_SEP +
                    TRAZA_NOTIFICACION_COLUMN_ID_USUARIO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_NOTIFICACION_COLUMN_ID_NOTIFICACION + CONSTRAINT_REF + TABLE_NOTIFICACION + PARENTHESIS_INI + NOTIFICACION_COLUMN_ID_NOTIFICACION + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_NOTIFICACION_COLUMN_ID_ESTADO + CONSTRAINT_REF + TABLE_ESTADO + PARENTHESIS_INI + ESTADO_COLUMN_ID_ESTADO + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_NOTIFICACION_COLUMN_ID_LOCALIZACION + CONSTRAINT_REF + TABLE_LOCALIZACION + PARENTHESIS_INI + LOCALIZACION_COLUMN_ID_LOCALIZACION + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_NOTIFICACION_COLUMN_ID_USUARIO + CONSTRAINT_REF + TABLE_ENTIDAD + PARENTHESIS_INI + ENTIDAD_COLUMN_ID_ENTIDAD + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table traza_evento
     **/
    private static final String SQL_CREATE_TRAZA_EVENTO_ENTRIES =
            CREATE_TABLE + TABLE_TRAZA_EVENTO + PARENTHESIS_INI +
                    TRAZA_EVENTO_COLUMN_ID_TRAZA_EVENTO + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    TRAZA_EVENTO_COLUMN_ID_ESTADO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRAZA_EVENTO_COLUMN_FECHA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    TRAZA_EVENTO_COLUMN_ID_EVENTO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    TRAZA_EVENTO_COLUMN_ID_LOCALIZACION + INT_TYPE + NULL + COMMA_SEP +
                    TRAZA_EVENTO_COLUMN_ID_USUARIO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_EVENTO_COLUMN_ID_ESTADO + CONSTRAINT_REF + TABLE_ESTADO + PARENTHESIS_INI + ESTADO_COLUMN_ID_ESTADO + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_EVENTO_COLUMN_ID_EVENTO + CONSTRAINT_REF + TABLE_EVENTO + PARENTHESIS_INI + EVENTO_COLUMN_ID_EVENTO + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_EVENTO_COLUMN_ID_LOCALIZACION + CONSTRAINT_REF + TABLE_LOCALIZACION + PARENTHESIS_INI + LOCALIZACION_COLUMN_ID_LOCALIZACION + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + TRAZA_EVENTO_COLUMN_ID_USUARIO + CONSTRAINT_REF + TABLE_ENTIDAD + PARENTHESIS_INI + ENTIDAD_COLUMN_ID_ENTIDAD + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table gps_frecuente
     **/
    private static final String SQL_CREATE_GPS_FRECUENTE_ENTRIES =
            CREATE_TABLE + TABLE_GPS_FRECUENTE + PARENTHESIS_INI +
                    GPS_FRECUENTE_COLUMN_ID_GPS_FRECUENTE + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    GPS_FRECUENTE_COLUMN_FECHA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    GPS_FRECUENTE_COLUMN_ID_LOCALIZACION + INT_TYPE + NOT_NULL + COMMA_SEP +
                    GPS_FRECUENTE_COLUMN_ID_USUARIO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + GPS_FRECUENTE_COLUMN_ID_LOCALIZACION + CONSTRAINT_REF + TABLE_LOCALIZACION + PARENTHESIS_INI + LOCALIZACION_COLUMN_ID_LOCALIZACION + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + GPS_FRECUENTE_COLUMN_ID_USUARIO + CONSTRAINT_REF + TABLE_ENTIDAD + PARENTHESIS_INI + ENTIDAD_COLUMN_ID_ENTIDAD + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table detalle_transaccion
     **/
    private static final String SQL_CREATE_DETALLE_TRANSACCION_ENTRIES =
            CREATE_TABLE + TABLE_DETALLE_TRANSACCION + PARENTHESIS_INI +
                    DETALLE_TRANSACCION_COLUMN_ID_DETALLE_TRANSACCION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_IDTARIFA + INT_TYPE + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_COSTOXMINUTO + REAL_TYPE + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_FECHAHORAENTRADA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_FECHAHORASALIDA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_CERRADO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_PAGADO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    DETALLE_TRANSACCION_COLUMN_COSTOTOTAL + REAL_TYPE + NOT_NULL +
                    PARENTHESIS_FSC;

    /**
     * Table lista_detalle
     **/
    private static final String SQL_CREATE_LISTA_DETALLE_ENTRIES =
            CREATE_TABLE + TABLE_LISTA_DETALLE + PARENTHESIS_INI +
                    LISTA_DETALLE_COLUMN_ID_TRANSACCION + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    LISTA_DETALLE_COLUMN_ID_DETALLE_TRANSACCION + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + LISTA_DETALLE_COLUMN_ID_TRANSACCION + CONSTRAINT_REF + TABLE_TRANSACCION + PARENTHESIS_INI + TRANSACCION_COLUMN_ID_TRANSACCION + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + LISTA_DETALLE_COLUMN_ID_DETALLE_TRANSACCION + CONSTRAINT_REF + TABLE_DETALLE_TRANSACCION + PARENTHESIS_INI + DETALLE_TRANSACCION_COLUMN_ID_DETALLE_TRANSACCION + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Table api
     **/
    private static final String SQL_CREATE_API_ENTRIES =
            CREATE_TABLE + TABLE_API + PARENTHESIS_INI +
                    API_COLUMN_ID_API + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    API_COLUMN_END_POINT + TEXT_TYPE +
                    PARENTHESIS_FSC;

    /**
     * Table instrumentalizacion_api
     **/
    private static final String SQL_CREATE_INSTRUMENTALIZACION_API_ENTRIES =
            CREATE_TABLE + TABLE_INSTRUMENTALIZACION_API + PARENTHESIS_INI +
                    INSTRUMENTALIZACION_API_COLUMN_ID_INSTRUMENTALIZACION_API + INT_TYPE + CONSTRAINT_PK + AUTOINCREMENT + NOT_NULL + COMMA_SEP +
                    INSTRUMENTALIZACION_API_COLUMN_ID_USUARIO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    INSTRUMENTALIZACION_API_COLUMN_ID_API + INT_TYPE + NOT_NULL + COMMA_SEP +
                    INSTRUMENTALIZACION_API_COLUMN_FECHA_CONSUMO + INT_TYPE + NOT_NULL + COMMA_SEP +
                    CONSTRAINT_FK + INSTRUMENTALIZACION_API_COLUMN_ID_USUARIO + CONSTRAINT_REF + TABLE_ENTIDAD + PARENTHESIS_INI + ENTIDAD_COLUMN_ID_ENTIDAD + PARENTHESIS_FIN + COMMA_SEP +
                    CONSTRAINT_FK + INSTRUMENTALIZACION_API_COLUMN_ID_API + CONSTRAINT_REF + TABLE_API + PARENTHESIS_INI + API_COLUMN_ID_API + PARENTHESIS_FIN +
                    PARENTHESIS_FSC;

    /**
     * Inserts tabla estado
     **/
    private static final String SQL_INSERT_ESTADO_0 =
            "insert into estado(estado)values('recibido');";

    private static final String SQL_INSERT_ESTADO_1 =
            "insert into estado(estado)values('leido');";

    private static final String SQL_INSERT_ESTADO_2 =
            "insert into estado(estado)values('visto');";

    private static final String SQL_INSERT_ESTADO_3 =
            "insert into estado(estado)values('procesado');";

    //Delete tables query

    /**
     * Table connections
     **/
    private final String SQL_DELETE_CONNECTIONS_ENTRIES =
            DROP_TABLE + TABLE_CONNECTIONS + SEMI_COLON;

    /**
     * Table suscriptions
     **/
    private final String SQL_DELETE_SUBSCRIPTION_ENTRIES =
            DROP_TABLE + TABLE_SUBSCRIPTIONS + SEMI_COLON;

    /**
     * Table kmz
     **/
    private final String SQL_DELETE_KMZ_ENTRIES =
            DROP_TABLE + TABLE_KMZ + SEMI_COLON;

    /**
     * Table tipo_widget
     **/
    private final String SQL_DELETE_TIPO_WIDGET_ENTRIES =
            DROP_TABLE + TABLE_TIPO_WIDGET + SEMI_COLON;

    /**
     * Table widget
     **/
    private final String SQL_DELETE_WIDGET_ENTRIES =
            DROP_TABLE + TABLE_WIDGET + SEMI_COLON;

    /**
     * Table entidad
     **/
    private final String SQL_DELETE_ENTIDAD_ENTRIES =
            DROP_TABLE + TABLE_ENTIDAD + SEMI_COLON;

    /**
     * Table instrumentalizacion_widget
     **/
    private static final String SQL_DELETE_INSTRUMENTALIZACION_WIDGET_ENTRIES =
            DROP_TABLE + TABLE_INSTRUMENTALIZACION_WIDGET + SEMI_COLON;

    /**
     * Table localizacion
     **/
    private static final String SQL_DELETE_LOCALIZACION_ENTRIES =
            DROP_TABLE + TABLE_LOCALIZACION + SEMI_COLON;

    /**
     * Table transaccion
     **/
    private static final String SQL_DELETE_TRANSACCION_ENTRIES =
            DROP_TABLE + TABLE_TRANSACCION + SEMI_COLON;

    /**
     * Table parametrizacion
     **/
    private static final String SQL_DELETE_PARAMETRIZACION_ENTRIES =
            DROP_TABLE + TABLE_PARAMETRIZACION + SEMI_COLON;

    /**
     * Table sesion
     **/
    private static final String SQL_DELETE_SESION_ENTRIES =
            DROP_TABLE + TABLE_SESION + SEMI_COLON;

    /**
     * Table ping
     **/
    private static final String SQL_DELETE_PING_ENTRIES =
            DROP_TABLE + TABLE_PING + SEMI_COLON;

    /**
     * Table evento
     **/
    private static final String SQL_DELETE_EVENTO_ENTRIES =
            DROP_TABLE + TABLE_EVENTO + SEMI_COLON;

    /**
     * Table estado
     **/
    private static final String SQL_DELETE_ESTADO_ENTRIES =

            DROP_TABLE + TABLE_ESTADO + SEMI_COLON;

    /**
     * Table notificacion
     **/
    private static final String SQL_DELETE_NOTIFICACION_ENTRIES =
            DROP_TABLE + TABLE_NOTIFICACION + SEMI_COLON;

    /**
     * Table traza_notificacion
     **/
    private static final String SQL_DELETE_TRAZA_NOTIFICACION_ENTRIES =
            DROP_TABLE + TABLE_TRAZA_NOTIFICACION + SEMI_COLON;

    /**
     * Table traza_evento
     **/
    private static final String SQL_DELETE_TRAZA_EVENTO_ENTRIES =
            DROP_TABLE + TABLE_TRAZA_EVENTO + SEMI_COLON;

    /**
     * Table gps_frecuente
     **/
    private static final String SQL_DELETE_GPS_FRECUENTE_ENTRIES =
            DROP_TABLE + TABLE_GPS_FRECUENTE + SEMI_COLON;

    /**
     * Table detalle_transaccion
     **/
    private static final String SQL_DELETE_DETALLE_TRANSACCION_ENTRIES =
            DROP_TABLE + TABLE_DETALLE_TRANSACCION + SEMI_COLON;

    /**
     * Table lista_detalle
     **/
    private static final String SQL_DELETE_LISTA_DETALLE_ENTRIES =
            DROP_TABLE + TABLE_LISTA_DETALLE + SEMI_COLON;

    /**
     * Table api
     **/
    private static final String SQL_DELETE_API_ENTRIES =
            DROP_TABLE + TABLE_API + SEMI_COLON;

    /**
     * Table instrumentalizacion_api
     **/
    private static final String SQL_DELETE_INSTRUMENTALIZACION_API_ENTRIES =
            DROP_TABLE + TABLE_INSTRUMENTALIZACION_API + SEMI_COLON;

    /**
     * Table instrumentalizacion_api
     **/
    private final Context context;

    /**
     * Creates the persistence object passing it a context
     *
     * @param context Context that the application is running in
     */
    public Persistence(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /*(non-Javadoc)
     * @see org.eclipse.paho.android.service.database.sqlite.SQLiteOpenHelper#onCreate(org.eclipse.paho.android.service.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        pCon = new Persistence_Connections(context);
        pSus = new Persistence_Subscriptions(context);

        db.execSQL(pCon.SQL_CREATE_CONNECTIONS_ENTRIES);
        db.execSQL(pSus.SQL_CREATE_SUBSCRIPTION_ENTRIES);

        db.execSQL(SQL_CREATE_API_ENTRIES);
        db.execSQL(SQL_CREATE_DETALLE_TRANSACCION_ENTRIES);
        db.execSQL(SQL_CREATE_ENTIDAD_ENTRIES);
        db.execSQL(SQL_CREATE_ESTADO_ENTRIES);
        db.execSQL(SQL_CREATE_EVENTO_ENTRIES);
        db.execSQL(SQL_CREATE_GPS_FRECUENTE_ENTRIES);
        db.execSQL(SQL_CREATE_INSTRUMENTALIZACION_API_ENTRIES);
        db.execSQL(SQL_CREATE_INSTRUMENTALIZACION_WIDGET_ENTRIES);
        db.execSQL(SQL_CREATE_KMZ_ENTRIES);
        db.execSQL(SQL_CREATE_LISTA_DETALLE_ENTRIES);
        db.execSQL(SQL_CREATE_LOCALIZACION_ENTRIES);
        db.execSQL(SQL_CREATE_NOTIFICACION_ENTRIES);
        db.execSQL(SQL_CREATE_PARAMETRIZACION_ENTRIES);
        db.execSQL(SQL_CREATE_PING_ENTRIES);
        db.execSQL(SQL_CREATE_SESION_ENTRIES);
        db.execSQL(SQL_CREATE_TIPO_WIDGET_ENTRIES);
        db.execSQL(SQL_CREATE_TRANSACCION_ENTRIES);
        db.execSQL(SQL_CREATE_TRAZA_EVENTO_ENTRIES);
        db.execSQL(SQL_CREATE_TRAZA_NOTIFICACION_ENTRIES);
        db.execSQL(SQL_CREATE_WIDGET_ENTRIES);

        //InsertS tabla estados
        db.execSQL(SQL_INSERT_ESTADO_0);
        db.execSQL(SQL_INSERT_ESTADO_1);
        db.execSQL(SQL_INSERT_ESTADO_2);
        db.execSQL(SQL_INSERT_ESTADO_3);
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.android.service.database.sqlite.SQLiteOpenHelper#onUpgrade(org.eclipse.paho.android.service.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CONNECTIONS_ENTRIES);
        db.execSQL(SQL_DELETE_SUBSCRIPTION_ENTRIES);
        db.execSQL(SQL_DELETE_API_ENTRIES);
        db.execSQL(SQL_DELETE_DETALLE_TRANSACCION_ENTRIES);
        db.execSQL(SQL_DELETE_ENTIDAD_ENTRIES);
        db.execSQL(SQL_DELETE_ESTADO_ENTRIES);
        db.execSQL(SQL_DELETE_EVENTO_ENTRIES);
        db.execSQL(SQL_DELETE_GPS_FRECUENTE_ENTRIES);
        db.execSQL(SQL_DELETE_INSTRUMENTALIZACION_API_ENTRIES);
        db.execSQL(SQL_DELETE_INSTRUMENTALIZACION_WIDGET_ENTRIES);
        db.execSQL(SQL_DELETE_KMZ_ENTRIES);
        db.execSQL(SQL_DELETE_LISTA_DETALLE_ENTRIES);
        db.execSQL(SQL_DELETE_LOCALIZACION_ENTRIES);
        db.execSQL(SQL_DELETE_NOTIFICACION_ENTRIES);
        db.execSQL(SQL_DELETE_PARAMETRIZACION_ENTRIES);
        db.execSQL(SQL_DELETE_PING_ENTRIES);
        db.execSQL(SQL_DELETE_SESION_ENTRIES);
        db.execSQL(SQL_DELETE_TIPO_WIDGET_ENTRIES);
        db.execSQL(SQL_DELETE_TRANSACCION_ENTRIES);
        db.execSQL(SQL_DELETE_TRAZA_EVENTO_ENTRIES);
        db.execSQL(SQL_DELETE_TRAZA_NOTIFICACION_ENTRIES);
        db.execSQL(SQL_DELETE_WIDGET_ENTRIES);
    }

    public void init() {
        SQLiteDatabase db = getReadableDatabase();

        pCon = new Persistence_Connections(context);
        pSus = new Persistence_Subscriptions(context);

        db.close();
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.paho.android.service.database.sqlite.SQLiteOpenHelper#onDowngrade(org.eclipse.paho.android.service.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Recreates connection objects based upon information stored in the database
     *
     * @param context Context for creating {@link MQTTConnection} objects
     * @return list of connections that have been restored
     * @throws PersistenceException if restoring connections fails, this is thrown
     */
    public List<MQTTConnection> restoreConnections(Context context) throws PersistenceException {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(TABLE_CONNECTIONS, pCon.getConnectionColumns(), null, null, null, null, pCon.getCONNECTIONS_COLUMN_HOST());
        ArrayList<MQTTConnection> list = new ArrayList<>(c.getCount());
        MQTTConnection connection;
        for (int i = 0; i < c.getCount(); i++) {
            if (!c.moveToNext()) { //move to the next item throw persistence exception, if it fails
                throw new PersistenceException("Failed restoring connection - count: " + c.getCount() + "loop iteration: " + i);
            }
            //get data from cursor
            Long id = c.getLong(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_ID()));
            //basic client information
            String clientHandle = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_CLIENT_HANDLE()));
            String host = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_HOST()));
            String clientID = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_CLIENT_ID()));
            int port = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_PORT()));

            //connect options strings
            String username = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_USER_NAME()));
            String password = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_PASSWORD()));
            String topic = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_TOPIC()));
            String message = c.getString(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_MESSAGE()));

            //connect options integers
            int qos = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_QOS()));
            int keepAlive = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_KEEP_ALIVE()));
            int timeout = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_TIME_OUT()));

            //get all values that need converting and convert integers to booleans in line using "condition ? trueValue : falseValue"
            boolean cleanSession = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_CLEAN_SESSION())) == 1;
            boolean retained = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_RETAINED())) == 1;
            boolean ssl = c.getInt(c.getColumnIndexOrThrow(pCon.getCONNECTIONS_COLUMN_SSL())) == 1;

            //rebuild objects starting with the connect options
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setCleanSession(cleanSession);
            opts.setKeepAliveInterval(keepAlive);
            opts.setConnectionTimeout(timeout);

            opts.setPassword(password != null ? password.toCharArray() : null);
            opts.setUserName(username);

            if (topic != null) {
                opts.setWill(topic, message.getBytes(), qos, retained);
            }

            //now create the connection object
            connection = MQTTConnection.createConnection(clientHandle, clientID, host, port, context, ssl);
            connection.addConnectionOptions(opts);
            connection.assignPersistenceId(id);

            // Now we recover all subscriptions for this connection
            String[] args = {clientHandle};
            System.out.println("SUB: " + connection.toString());

            Cursor sub_c = db.query(TABLE_SUBSCRIPTIONS, pSus.subscriptionColumns, pSus.subscriptionWhereQuery, args, null, null, pSus.getSubscriptionsColumnHost());
            ArrayList<Subscription> subscriptions = new ArrayList<>(sub_c.getCount());
            for (int x = 0; x < sub_c.getCount(); x++) {
                if (!sub_c.moveToNext()) { //move to the next item throw persistence exception, if it fails
                    throw new PersistenceException("Failed restoring subscription - count: " + sub_c.getCount() + "loop iteration: " + x);
                }
                Long sub_id = sub_c.getLong(sub_c.getColumnIndexOrThrow(pSus.getSUBSCRIPTIONS_COLUMN_ID()));
                String sub_clientHandle = sub_c.getString(sub_c.getColumnIndexOrThrow(pSus.getSubscriptionsColumnClientHandle()));
                String sub_topic = sub_c.getString(sub_c.getColumnIndexOrThrow(pSus.getSubscriptionsColumnTopic()));
                boolean sub_notify = sub_c.getInt(sub_c.getColumnIndexOrThrow(pSus.getSubscriptionsColumnNotify())) == 1;
                int sub_qos = sub_c.getInt(sub_c.getColumnIndexOrThrow(pSus.getSubscriptionsColumnQos()));
                Subscription sub = new Subscription(sub_topic, sub_qos, sub_clientHandle, sub_notify);
                sub.setPersistenceId(sub_id);
                Log.d(TAG, "Restoring Subscription: " + sub.toString());
                subscriptions.add(sub);
            }
            sub_c.close();

            connection.setSubscriptions(subscriptions);

            //store it in the list
            list.add(connection);

        }
        //close the cursor now we are finished with it
        c.close();


        db.close();
        return list;

    }


}