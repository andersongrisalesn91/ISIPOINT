package co.com.mirecarga.vendedor.ventapaquetetransito;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de venta de paquetes de tránsito.
 */
@Singleton
public class VentaPaqueteTransitoServiceBean implements VentaPaqueteTransitoService {
    /**
     * Los datos para mostrar la confirmación.
     */
    private ConfirmarVentaPaqueteTransitoDatos datosConfirmar;

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public VentaPaqueteTransitoServiceBean() {
        // Constructor con las dependencias
    }

    @Override
    public final ConfirmarVentaPaqueteTransitoDatos getDatosConfirmar() {
        return datosConfirmar;
    }

    @Override
    public final void setDatosConfirmar(final ConfirmarVentaPaqueteTransitoDatos datosConfirmar) {
        this.datosConfirmar = datosConfirmar;
    }
}
