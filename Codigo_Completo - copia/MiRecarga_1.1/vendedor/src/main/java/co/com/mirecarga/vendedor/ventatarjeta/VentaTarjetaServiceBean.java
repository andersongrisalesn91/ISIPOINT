package co.com.mirecarga.vendedor.ventatarjeta;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de venta de paquetes de tránsito.
 */
@Singleton
public class VentaTarjetaServiceBean implements VentaTarjetaService {
    /**
     * Los datos para mostrar la confirmación.
     */
    private ConfirmarVentaTarjetaDatos datosConfirmar;

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public VentaTarjetaServiceBean() {
        // Constructor con las dependencias
    }

    @Override
    public final ConfirmarVentaTarjetaDatos getDatosConfirmar() {
        return datosConfirmar;
    }

    @Override
    public final void setDatosConfirmar(final ConfirmarVentaTarjetaDatos datosConfirmar) {
        this.datosConfirmar = datosConfirmar;
    }
}
