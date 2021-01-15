package co.com.mirecarga.vendedor.persistence;

import android.os.Bundle;

import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.vendedor.business.EntidadDato;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public class PersistencePresenter implements PersistenceMVP.Presenter {

    private PersistenceMVP.View view;
    private PersistenceMVP.Model model;

    public PersistencePresenter(final PersistenceMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(PersistenceMVP.View view) {
        this.view = view;
    }

    @Override
    public long insertNotificacion() throws PersistenceException {
        if (view != null) {
            Bundle bundle = view.getBundle();
            String title = bundle.getString("title");
            String body = bundle.getString("body");
            String data = bundle.getString("data");
            NotificacionDato dato = new NotificacionDato(title, body, data);

            return model.insertNotification(dato);
        }
        return -1;
    }

    @Override
    public long insertEntidad() throws PersistenceException {
        if (view != null) {
            RespuestaInicio respuestaInicio = view.getEntidad();

            int id_vendedor = respuestaInicio.getIdUsuario();
            String login  = respuestaInicio.getLogin();
            String correoElectronico  = respuestaInicio.getCorreoElectronico();
            String nombreCompleto =   respuestaInicio.getNombreCompleto();
            String proveedorAutenticacion  = respuestaInicio.getProveedorAutenticacion().name();
            String id_suscriber = "";
            String id_entidad_sys = "";

            EntidadDato entidad = new EntidadDato(id_vendedor, login, correoElectronico, nombreCompleto, proveedorAutenticacion, id_suscriber, id_entidad_sys);

            return model.insertEntidad(entidad);
        }
        return -1;
    }


}
