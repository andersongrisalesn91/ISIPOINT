package co.com.mirecarga.vendedor.recargaenlinea;


/**
 * Contrato para el servicio de recarga en línea para el vendedor.
 */
public interface RecargaEnLineaService {
    /**
     * Lee los últimos datos de confirmación establecidos.
     * @return los últimos datos de confirmación establecidos
     */
    ConfirmarRecargaEnLineaDatos getDatosConfirmar();

    /**
     * Establece los últimos datos de confirmación.
     * @param datosConfirmar los últimos datos de confirmación
     */
    void setDatosConfirmar(ConfirmarRecargaEnLineaDatos datosConfirmar);
}
