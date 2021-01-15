package co.com.mirecarga.cliente.venta;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de para el inicio de uso de un PIN.
 */
@Singleton
public class InicioUsoPinServiceBean implements InicioUsoPinService {
    /**
     * Los últimos datos de confirmación.
     */
    private InicioUsoPinDatos datosConfirmar;

    @Override
    public final InicioUsoPinDatos getDatosConfirmar() {
        return datosConfirmar;
    }

    @Override
    public final void setDatosConfirmar(final InicioUsoPinDatos datosConfirmar) {
        this.datosConfirmar = datosConfirmar;
    }

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public InicioUsoPinServiceBean() {
        // Constructor con las dependencias
    }
}
