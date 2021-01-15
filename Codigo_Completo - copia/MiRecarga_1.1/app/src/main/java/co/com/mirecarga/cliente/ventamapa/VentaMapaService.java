package co.com.mirecarga.cliente.ventamapa;

import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.mapa.ZonaMapa;

/**
 * Contrato para el servicio de venta de paquetes de tránsito.
 */

public interface VentaMapaService {
    /**
     * Lee los últimos datos de confirmación establecidos.
     * @return los últimos datos de confirmación establecidos
     */
    ConfirmarVentaMapaDatos getDatosConfirmar();

    /**
     * Establece los últimos datos de confirmación.
     * @param datosConfirmar los últimos datos de confirmación
     */
    void setDatosConfirmar(ConfirmarVentaMapaDatos datosConfirmar);

    /**
     * Regresa los datos de la zona Seleccionada.
     *
     * @return los datos de la zona Seleccionada
     */
    ZonaMapa getZonaSeleccionada();

    /**
     * Establece los datos de la zona Seleccionada.
     *
     * @param zonaSeleccionada el valor a establecer
     */
    void setZonaSeleccionada(ZonaMapa zonaSeleccionada);

    /**
     * Regresa el campo idCeldaSeleccionada.
     *
     * @return el valor de idCeldaSeleccionada
     */
    int getIdCeldaSeleccionada();

    /**
     * Establece el valor del campo idCeldaSeleccionada.
     *
     * @param idCeldaSeleccionada el valor a establecer
     */
    void setIdCeldaSeleccionada(int idCeldaSeleccionada);

    /**
     * Calcula el costo total de la transacción.
     * @param resp la respuesta
     * @return el costo total
     */
    double getCostoTotal(TransaccionCelda resp);

    /**
     * Calcula el costo por minuto de la transacción.
     * @param resp la respuesta
     * @return el costo por minuto
     */
    double getCostoPorMinuto(TransaccionCelda resp);

    /**
     * Regresa el campo celdaMultiple.
     *
     * @return el valor de celdaMultiple
     */
    boolean isCeldaMultiple();

    /**
     * Establece el valor del campo celdaMultiple.
     *
     * @param celdaMultiple el valor a establecer
     */
    void setCeldaMultiple(boolean celdaMultiple);

    /**
     * Regresa el campo filtrarPendientes.
     *
     * @return el valor de filtrarPendientes
     */
    boolean isFiltrarPendientes();

    /**
     * Establece el valor del campo filtrarPendientes.
     *
     * @param filtrarPendientes el valor a establecer
     */
    void setFiltrarPendientes(boolean filtrarPendientes);
}
