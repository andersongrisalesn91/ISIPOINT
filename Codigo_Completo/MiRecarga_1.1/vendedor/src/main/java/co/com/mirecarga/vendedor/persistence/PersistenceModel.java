package co.com.mirecarga.vendedor.persistence;

import co.com.mirecarga.vendedor.business.EntidadDato;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public class PersistenceModel implements PersistenceMVP.Model {

    private PersistenceInterface persistence;

    public PersistenceModel(final PersistenceInterface persistence) {
        this.persistence = persistence;
    }

    @Override
    public long insertNotification(NotificacionDato dato) throws PersistenceException {
        //Logica si es necesario
        return persistence.insertNotificacion(dato);
    }

    @Override
    public long insertEntidad(EntidadDato dato) throws PersistenceException {
        return persistence.insertEntidad(dato);
    }
}
