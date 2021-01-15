package co.com.mirecarga.vendedor.ventapintransito;

/**
 * Contrato para el servicio de venta de paquetes de tránsito.
 */

public interface VentaPinTransitoService {
    /**
     * Lee los últimos datos de confirmación establecidos.
     * @return los últimos datos de confirmación establecidos
     */
    ConfirmarVentaPinTransitoDatos getDatosConfirmar();

    /**
     * Establece los últimos datos de confirmación.
     * @param datosConfirmar los últimos datos de confirmación
     */
    void setDatosConfirmar(ConfirmarVentaPinTransitoDatos datosConfirmar);
}
