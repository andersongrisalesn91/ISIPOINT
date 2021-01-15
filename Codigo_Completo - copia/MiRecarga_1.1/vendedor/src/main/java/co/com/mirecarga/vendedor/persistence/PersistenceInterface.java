package co.com.mirecarga.vendedor.persistence;

import co.com.mirecarga.vendedor.business.EntidadDato;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public interface PersistenceInterface {

    long insertNotificacion(NotificacionDato object) throws PersistenceException;
    long insertEntidad(EntidadDato object) throws PersistenceException;
    boolean selectEntidadIfExist(int id) throws PersistenceException;
}
