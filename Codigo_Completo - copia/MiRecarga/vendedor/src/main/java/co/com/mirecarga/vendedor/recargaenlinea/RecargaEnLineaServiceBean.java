package co.com.mirecarga.vendedor.recargaenlinea;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de recarga en línea para el vendedor.
 */
@Singleton
public class RecargaEnLineaServiceBean implements RecargaEnLineaService {
    /**
     * Los últimos datos de confirmación.
     */
    private ConfirmarRecargaEnLineaDatos datosConfirmar;

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public RecargaEnLineaServiceBean() {
        // Constructor con las dependencias
    }

    @Override
    public final ConfirmarRecargaEnLineaDatos getDatosConfirmar() {
        return datosConfirmar;
    }

    @Override
    public final void setDatosConfirmar(final ConfirmarRecargaEnLineaDatos datosConfirmar) {
        this.datosConfirmar = datosConfirmar;
    }
}
