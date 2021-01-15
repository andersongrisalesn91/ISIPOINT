package co.com.mirecarga.vendedor.mapa;

import android.view.View;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Contrato para los eventos del mapa del vendedor.
 */
public interface EventosMapaVendedor {
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
     * Muestra el historial de transacciones de la celda.
     * @param idZona el identificador de la zona
     * @param idCelda identificador de la celda
     * @param filtrarPendientes indica si se deben mostrar únicamente las pendientes
     */
    void mostrarHistorial(int idZona, int idCelda, boolean filtrarPendientes);

    /**
     * Llena la información de una celda ocupada en la infowindow.
     * @param idZona el identificador de la zona
     * @param idCelda identificador de la celda
     * @param mView la vista con el infowindow
     */
    void llenarInformacionCeldaOcupada(int idZona, int idCelda, View mView);

    /**
     * Regresa el campo configService.
     *
     * @return el valor de configService
     */
    ConfigService<ConfigVendedor> getConfigService();

    /**
     * Establece el valor del campo configService.
     *
     * @param configService el valor a establecer
     */
    void setConfigService(ConfigService<ConfigVendedor> configService);

    /**
     * Procesa el evento del botón salirCelda.
     * @param celda los datos de la celda a imprimir
     */
    void salirCelda(TransaccionCelda celda);

    /**
     * Ejecuta la acción en el hilo principal.
     * @param action la acción
     */
    void runOnUiThread(Runnable action);
}
