package co.com.mirecarga.vendedor.persistence;

import android.os.Bundle;

import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.vendedor.business.EntidadDato;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public interface PersistenceMVP {

    interface View {
        Bundle getBundle();
        RespuestaInicio getEntidad();
    }

    interface Presenter {
        void setView(PersistenceMVP.View view);
        long insertNotificacion() throws PersistenceException;
        long insertEntidad() throws PersistenceException;
    }

    interface Model {
        long insertNotification(NotificacionDato dato) throws PersistenceException;
        long insertEntidad(EntidadDato dato) throws PersistenceException;
    }
}
