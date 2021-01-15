package co.com.mirecarga.cliente.venta;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de para finalizar de uso de un PIN.
 */
@Singleton
public class FinalizarUsoPinServiceBean implements  FinalizarUsoPinService {
    /**
     * El código del método de pago.
     */
    private String codigoMetodoPago;

    /**
     * Regresa el campo codigoMetodoPago.
     *
     * @return el valor de codigoMetodoPago
     */
    public final String getCodigoMetodoPago() {
        return codigoMetodoPago;
    }

    /**
     * Establece el valor del campo codigoMetodoPago.
     *
     * @param codigoMetodoPago el valor a establecer
     */
    public final void setCodigoMetodoPago(final String codigoMetodoPago) {
        this.codigoMetodoPago = codigoMetodoPago;
    }

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public FinalizarUsoPinServiceBean() {
        // Constructor con las dependencias
    }
}
