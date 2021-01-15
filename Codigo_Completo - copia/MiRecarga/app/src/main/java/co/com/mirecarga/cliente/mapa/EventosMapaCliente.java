package co.com.mirecarga.cliente.mapa;

import android.view.View;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.ConfigService;

/**
 * Contrato para los eventos del mapa del vendedor.
 */
public interface EventosMapaCliente {
    /**
     * Actualiza las celdas según la zona.
     * @param zonaMapa los datos de la zona
     * @param infoWindow el cuadro de díalogo
     * @param kmlPlacemark la marca seleccionada
     */
    void actualizarCeldas(ZonaMapa zonaMapa, InfoWindow infoWindow, KmlPlacemark kmlPlacemark);

    /**
     * Inicia la venta.
     * @param idZona el identificador de la zona
     * @param idCelda el identificador de la celda
     */
    void iniciarVenta(int idZona, int idCelda);

    /**
     * Inicia la venta en una celda múltiple.
     * @param idZona el identificador de la zona
     * @param idCelda el identificador de la celda
     */
    void iniciarVentaMultiple(int idZona, int idCelda);

    /**
     * Regresa el campo configService.
     *
     * @return el valor de configService
     */
    ConfigService<ConfigCliente> getConfigService();

    /**
     * Establece el valor del campo configService.
     *
     * @param configService el valor a establecer
     */
    void setConfigService(ConfigService<ConfigCliente> configService);

    /**
     * Ejecuta la acción en el hilo principal.
     * @param action la acción
     */
    void runOnUiThread(Runnable action);
}
