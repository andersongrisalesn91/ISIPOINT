package co.com.mirecarga.vendedor.ventapintransito;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de venta de paquetes de tránsito.
 */
@Singleton
public class VentaPinTransitoServiceBean implements VentaPinTransitoService {
    /**
     * Los datos para mostrar la confirmación.
     */
    private ConfirmarVentaPinTransitoDatos datosConfirmar;

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public VentaPinTransitoServiceBean() {
        // Constructor con las dependencias
    }

    @Override
    public final ConfirmarVentaPinTransitoDatos getDatosConfirmar() {
        return datosConfirmar;
    }

    @Override
    public final void setDatosConfirmar(final ConfirmarVentaPinTransitoDatos datosConfirmar) {
        this.datosConfirmar = datosConfirmar;
    }
}
