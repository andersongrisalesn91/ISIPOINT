package co.com.mirecarga.core.login;

/**
 * Datos requeridos para iniciar sesión desde un auteticador externo como facebook o twitter.
 */
public class DatosIniciarSesionExterno {
    /**
     * El nombre completo de la persona.
     */
    private String nombreCompleto;

    /**
     * Correo electrónica de la persona.
     */
    private String correoElectronico;

    /**
     * El proveedor con el que se autenticó el usuario.
     */
    private ProveedorAutenticacion proveedorAutenticacion;

    /**
     * Regresa el campo nombreCompleto.
     *
     * @return el valor de nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el valor del campo nombreCompleto.
     *
     * @param nombreCompleto el valor a establecer
     */
    public void setNombreCompleto(final String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Regresa el campo correoElectronico.
     *
     * @return el valor de correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Establece el valor del campo correoElectronico.
     *
     * @param correoElectronico el valor a establecer
     */
    public void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Regresa el campo proveedorAutenticacion.
     *
     * @return el valor de proveedorAutenticacion
     */
    public ProveedorAutenticacion getProveedorAutenticacion() {
        return proveedorAutenticacion;
    }

    /**
     * Establece el valor del campo proveedorAutenticacion.
     *
     * @param proveedorAutenticacion el valor a establecer
     */
    public void setProveedorAutenticacion(final ProveedorAutenticacion proveedorAutenticacion) {
        this.proveedorAutenticacion = proveedorAutenticacion;
    }
}
