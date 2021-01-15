package co.com.mirecarga.cliente.reportes;

/**
 * Contrato para el servicio de reporte transacciones por pin.
 */
public interface TransaccionesPorPinService {
    /**
     * Regresa el campo metodoPago.
     *
     * @return el valor de metodoPago
     */
    String getMetodoPago();

    /**
     * Establece el valor del campo metodoPago.
     *
     * @param metodoPago el valor a establecer
     */
    void setMetodoPago(String metodoPago);
}
