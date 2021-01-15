package co.com.mirecarga.cliente.recarga;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de para la compra de paquete.
 */
@Singleton
public class CompraPaqueteServiceBean implements  CompraPaqueteService {

    /**
     * El código del método de pago.
     */
    private String codigoMetodoPago;


    @Override
    public final String getCodigoMetodoPago() {
        return codigoMetodoPago;
    }


    @Override
    public final void setCodigoMetodoPago(final String codigoMetodoPago) {
        this.codigoMetodoPago = codigoMetodoPago;
    }

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public CompraPaqueteServiceBean() {
        // Constructor con las dependencias
    }
}
