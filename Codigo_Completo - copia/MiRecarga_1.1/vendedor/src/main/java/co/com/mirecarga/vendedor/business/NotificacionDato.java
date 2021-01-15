package co.com.mirecarga.vendedor.business;

public class NotificacionDato {

    private int id_notificacion;
    private String title;
    private String body;
    private String data;

    public NotificacionDato() {
    }

    public NotificacionDato(final String title, final String body, final String data) {
        this.id_notificacion = 0;
        this.title = title;
        this.body = body;
        this.data = data;
    }

    public NotificacionDato(final int id, final String title, final String body, final String data) {
        this.id_notificacion = id;
        this.title = title;
        this.body = body;
        this.data = data;
    }

    public int getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(final int id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }
}
