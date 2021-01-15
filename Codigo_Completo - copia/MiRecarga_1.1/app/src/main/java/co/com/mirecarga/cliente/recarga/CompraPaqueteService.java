package co.com.mirecarga.cliente.recarga;

/**
 * Contrato para el servicio de compra de paquetes.
 */

public interface CompraPaqueteService {

    /**
     * Regresa el campo CodigoMetodoPago.
     *
     * @return el valor de CodigoMetodoPago
     */
    String getCodigoMetodoPago();

    /**
     * Establece el valor del campo CodigoMetodoPago.
     *
     * @param codigoMetodoPago el valor a establecer
     */
    void setCodigoMetodoPago(String codigoMetodoPago);
}
