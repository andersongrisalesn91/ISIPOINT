package co.com.mirecarga.vendedor.ventatarjeta;

/**
 * Contrato para el servicio de venta de paquetes de tránsito.
 */

public interface VentaTarjetaService {
    /**
     * Lee los últimos datos de confirmación establecidos.
     * @return los últimos datos de confirmación establecidos
     */
    ConfirmarVentaTarjetaDatos getDatosConfirmar();

    /**
     * Establece los últimos datos de confirmación.
     * @param datosConfirmar los últimos datos de confirmación
     */
    void setDatosConfirmar(ConfirmarVentaTarjetaDatos datosConfirmar);
}
