package co.com.mirecarga.vendedor.business;

public class EntidadDato {

    private int id_entidad;
    private int id_vendedor;
    private String login;
    private String correoElectronico;
    private String nombreCompleto;
    private String proveedorAutenticacion;
    private String id_suscriber;
    private String id_entidad_sys;

    public EntidadDato() {
    }

    public EntidadDato(final int id_vendedor, final String login, final String correoElectronico, final String nombreCompleto, final String proveedorAutenticacion, final String id_suscriber, final String id_entidad_sys) {
        this.id_entidad = 0;
        this.id_vendedor = id_vendedor;
        this.login = login;
        this.correoElectronico = correoElectronico;
        this.nombreCompleto = nombreCompleto;
        this.proveedorAutenticacion = proveedorAutenticacion;
        this.id_suscriber = id_suscriber;
        this.id_entidad_sys = id_entidad_sys;
    }

    public EntidadDato(final int id_entidad, final int id_vendedor, final String login, final String correoElectronico, final String nombreCompleto, final String proveedorAutenticacion, final String id_suscriber, final String id_entidad_sys) {
        this.id_entidad = id_entidad;
        this.id_vendedor = id_vendedor;
        this.login = login;
        this.correoElectronico = correoElectronico;
        this.nombreCompleto = nombreCompleto;
        this.proveedorAutenticacion = proveedorAutenticacion;
        this.id_suscriber = id_suscriber;
        this.id_entidad_sys = id_entidad_sys;
    }

    public int getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(final int id_entidad) {
        this.id_entidad = id_entidad;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(final int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(final String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getProveedorAutenticacion() {
        return proveedorAutenticacion;
    }

    public void setProveedorAutenticacion(final String proveedorAutenticacion) {
        this.proveedorAutenticacion = proveedorAutenticacion;
    }

    public String getId_suscriber() {
        return id_suscriber;
    }

    public void setId_suscriber(final String id_suscriber) {
        this.id_suscriber = id_suscriber;
    }

    public String getId_entidad_sys() {
        return id_entidad_sys;
    }

    public void setId_entidad_sys(final String id_entidad_sys) {
        this.id_entidad_sys = id_entidad_sys;
    }
}
