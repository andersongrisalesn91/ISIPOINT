package co.com.mirecarga.vendedor.ventapaquetetransito;

/**
 * Contrato para el servicio de venta de paquetes de tránsito.
 */

public interface VentaPaqueteTransitoService {
    /**
     * Lee los últimos datos de confirmación establecidos.
     * @return los últimos datos de confirmación establecidos
     */
    ConfirmarVentaPaqueteTransitoDatos getDatosConfirmar();

    /**
     * Establece los últimos datos de confirmación.
     * @param datosConfirmar los últimos datos de confirmación
     */
    void setDatosConfirmar(ConfirmarVentaPaqueteTransitoDatos datosConfirmar);
}
