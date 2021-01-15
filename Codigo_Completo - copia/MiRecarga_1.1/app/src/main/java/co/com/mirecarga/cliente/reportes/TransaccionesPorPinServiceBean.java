package co.com.mirecarga.cliente.reportes;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de reporte transacciones por pin.
 */
@Singleton
public class TransaccionesPorPinServiceBean implements TransaccionesPorPinService {
    /**
     * El método de pago para consultar.
     */
    private String metodoPago;

    @Override
    public final String getMetodoPago() {
        return metodoPago;
    }

    @Override
    public final void setMetodoPago(final String metodoPago) {
        this.metodoPago = metodoPago;
    }

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public TransaccionesPorPinServiceBean() {
        // Constructor con las dependencias
    }
}
