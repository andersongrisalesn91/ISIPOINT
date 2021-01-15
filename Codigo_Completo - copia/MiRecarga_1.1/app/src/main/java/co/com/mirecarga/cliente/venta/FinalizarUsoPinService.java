package co.com.mirecarga.cliente.venta;

/**
 * Contrato para el servicio de finalizar de uso de un PIN.
 */
public interface FinalizarUsoPinService {
    /**
     * Regresa el campo codigoMetodoPago.
     *
     * @return el valor de codigoMetodoPago
     */
    String getCodigoMetodoPago();

    /**
     * Establece el valor del campo codigoMetodoPago.
     *
     * @param codigoMetodoPago el valor a establecer
     */
    void setCodigoMetodoPago(String codigoMetodoPago);
}
