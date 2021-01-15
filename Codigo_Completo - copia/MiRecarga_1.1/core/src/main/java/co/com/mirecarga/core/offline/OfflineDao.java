package co.com.mirecarga.core.offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;

import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ServiceGenerator;

/**
 * Servicio de persistencia.
 */
public class OfflineDao extends SQLiteOpenHelper implements OfflineRepository {
    /**
     * El tag para el log.
     */
    private static final String TAG = "OfflineDao";

    /**
     * El contexto de la aplicación.
     */
    private final transient Context context;

    /**
     * Objeto para sincronización.
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructor con las dependencias.
     *
     * @param context el contexto de la aplicación
     */
    @Inject
    public OfflineDao(final Context context) {
        super(context, "miRecargaDb", null, 1);
        this.context = context;
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {
        AppLog.debug(TAG, "Crea bd");
        db.execSQL("CREATE TABLE OFFLINE_MESSAGE (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TIPO TEXT, TEXTO TEXT);");
    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS OFFLINE_MESSAGE");
            onCreate(db);
        }
    }

    @Override
    public final void guardar(final OfflineMessage message) {
        lock.lock();
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("tipo", message.getTipo());
            values.put("texto", message.getTexto());
            db.insertOrThrow("OFFLINE_MESSAGE", null, values);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public final List<OfflineMessage> consultarPorTipo(final String tipo) {
        List<OfflineMessage> lista = new ArrayList<>();
        String sql = "SELECT ID, TIPO, TEXTO FROM OFFLINE_MESSAGE WHERE TIPO = ? ORDER BY ID";
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.rawQuery(sql, new String[]{tipo})) {
            while (cursor.moveToNext()) {
                OfflineMessage reg = new OfflineMessage();
                reg.setId(cursor.getInt(0));
                reg.setTipo(cursor.getString(1));
                reg.setTexto(cursor.getString(2));
                lista.add(reg);
            }
        }
        return lista;
    }

    @Override
    public final void eliminar(final long id) {
        lock.lock();
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete("OFFLINE_MESSAGE", "id = " + id, null);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public final boolean isConnected() {
        return ServiceGenerator.isConnected(context);
    }
}
