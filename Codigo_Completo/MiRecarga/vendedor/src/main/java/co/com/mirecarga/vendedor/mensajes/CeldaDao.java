package co.com.mirecarga.vendedor.mensajes;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.util.AppLog;

@Singleton
public class CeldaDao implements CeldaRepository {
    /**
     * El tag para el log.
     */
    private static final String TAG = "CeldaDao";

    /**
     * Indice de celdas con estado.
     */
    private final Map<String, MensajeCelda> celdas = new HashMap<>();

    /**
     * Constructor vacío.
     */
     @Inject
    public CeldaDao() {
        super();
         AppLog.debug(TAG, "Inicializando CeldaDao");
    }

    /**
     * Llave única de la celda.
     * @param idZona el id de la zona
     * @param idCelda el id de la celda
     * @return la llave de la celda
     */
    public String getKey(final int idZona, final int idCelda) {
        return String.format("%s;%s", idZona, idCelda);
    }

    @Override
    public void actualizar(final MensajeCelda celda) {
        celdas.put(getKey(celda.getIdZona(), celda.getIdCelda()), celda);
    }

    @Override
    public Integer getIdEstadoCelda(final int idZona, final int idCelda) {
        Integer idEstado;
        final MensajeCelda celda = celdas.get(getKey(idZona, idCelda));
        if (celda == null) {
            idEstado = null;
        } else {
            idEstado = celda.getIdEstado();
        }
        return idEstado;
    }
}
