package co.com.mirecarga.vendedor.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.business.EntidadDato;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public class Persistence_Notificacion extends Persistence implements PersistenceInterface {

    private static final String TAG = Persistence_Notificacion.class.getSimpleName();

    //columns to return
    private String[] entidadColumns = {
            ENTIDAD_COLUMN_ID_ENTIDAD,
            ENTIDAD_COLUMN_ID_VENDEDOR,
            ENTIDAD_COLUMN_LOGIN,
            ENTIDAD_COLUMN_CORREOELECTRONICO,
            ENTIDAD_COLUMN_NOMBRECOMPLETO,
            ENTIDAD_COLUMN_PROVEEDORAUTENTICACION,
            ENTIDAD_COLUMN_ID_SUSCRIBER,
            ENTIDAD_COLUMN_ID_ENTIDAD_SYS
    };

    /**
     * Creates the persistence object passing it a context
     *
     * @param context Context that the application is running in
     */
    public Persistence_Notificacion(Context context) {
        super(context);
    }

    @Override
    public long insertNotificacion(final NotificacionDato object) throws PersistenceException {

        final NotificacionDato dato = object;
        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(NOTIFICACION_COLUMN_TITLE, dato.getTitle());
        values.put(NOTIFICACION_COLUMN_BODY, dato.getBody());
        values.put(NOTIFICACION_COLUMN_DATA, dato.getData());

        long newRowId = db.insert(TABLE_NOTIFICACION, null, values);
        db.close();
        if (newRowId == -1) {
            throw new PersistenceException("Failed to persist an item of table " + TABLE_NOTIFICACION);
        } else {
            return newRowId;
        }
    }

    @Override
    public long insertEntidad(final EntidadDato object) throws PersistenceException {

        final EntidadDato dato = object;
        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();
        long newRowId = -1;

        if (selectEntidadIfExist(dato.getId_vendedor())) {
            values.put(ENTIDAD_COLUMN_ID_VENDEDOR, dato.getId_vendedor());
            values.put(ENTIDAD_COLUMN_LOGIN, dato.getLogin());
            values.put(ENTIDAD_COLUMN_CORREOELECTRONICO, dato.getCorreoElectronico());
            values.put(ENTIDAD_COLUMN_NOMBRECOMPLETO, dato.getNombreCompleto());
            values.put(ENTIDAD_COLUMN_PROVEEDORAUTENTICACION, dato.getProveedorAutenticacion());
            values.put(ENTIDAD_COLUMN_ID_SUSCRIBER, dato.getId_suscriber());
            values.put(ENTIDAD_COLUMN_ID_ENTIDAD_SYS, dato.getId_entidad_sys());

            newRowId = db.insert(TABLE_ENTIDAD, null, values);
            db.close();

        }

        if (newRowId == -1) {
            throw new PersistenceException("Failed to persist an item of table " + TABLE_ENTIDAD);
        } else {
            return newRowId;
        }
    }

    public boolean selectEntidadIfExist(int id) throws PersistenceException {

        final SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[]{String.valueOf(id)};

        Cursor c = db.query(TABLE_ENTIDAD, entidadColumns, ENTIDAD_COLUMN_ID_VENDEDOR + "=?", args, null, null, null);
        ArrayList<EntidadDato> entidadList = new ArrayList<>(c.getCount());
        for (int x = 0; x < c.getCount(); x++) {
            if (!c.moveToNext()) { //move to the next item throw persistence exception, if it fails
                throw new PersistenceException("Failed restoring subscription - count: " + c.getCount() + "loop iteration: " + x);
            }

            int id_entidad = c.getInt(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_ID_ENTIDAD));
            int id_vendedor = c.getInt(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_ID_VENDEDOR));
            String login = c.getString(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_LOGIN));
            String correoElectronico = c.getString(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_CORREOELECTRONICO));
            String nombreCompleto = c.getString(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_NOMBRECOMPLETO));
            String proveedorAutenticacion = c.getString(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_PROVEEDORAUTENTICACION));
            String id_suscriber = c.getString(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_ID_SUSCRIBER));
            String id_entidad_sys = c.getString(c.getColumnIndexOrThrow(ENTIDAD_COLUMN_ID_ENTIDAD_SYS));

            EntidadDato entidad = new EntidadDato(id_entidad, id_vendedor, login, correoElectronico, nombreCompleto, proveedorAutenticacion, id_suscriber, id_entidad_sys);
            AppLog.debug(TAG, "Restoring Entidad: " + entidad.toString());
            entidadList.add(entidad);
        }
        c.close();

        return entidadList.size() == 0;
    }
}
