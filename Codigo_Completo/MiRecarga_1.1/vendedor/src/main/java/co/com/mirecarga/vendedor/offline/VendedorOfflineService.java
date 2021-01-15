package co.com.mirecarga.vendedor.offline;

import co.com.mirecarga.core.api.TransaccionCelda;
import io.reactivex.Observable;

/**
 * Contrato para el servicio que efectúa lógica fuera de línea.
 */
public interface VendedorOfflineService {
    /**
     * Registra la venta.
     * @param datos los datos para el registro
     * @return la respuesta del servicio
     */
    Observable<TransaccionCelda> registrarVenta(VentaDatos datos);

    /**
     * Verifica si es necesario sincronizar.
     */
    void sincronizar();

}
