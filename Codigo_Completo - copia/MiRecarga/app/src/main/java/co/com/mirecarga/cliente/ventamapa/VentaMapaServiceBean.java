package co.com.mirecarga.cliente.ventamapa;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.api.TransaccionCeldaDetalle;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.TemplateService;

/**
 * Servicio de venta de paquetes de tránsito.
 */
@Singleton
public class VentaMapaServiceBean implements VentaMapaService {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VentaMapaServiceBean";

    /**
     * El identificador de la zona seleccionada.
     */
    private ZonaMapa zonaSeleccionada;

    /**
     * El identificador de la celda seleccionada.
     */
    private int idCeldaSeleccionada;

    /**
     * Indica si se trata de una celda múltiple.
     */
    private boolean celdaMultiple;

    /**
     * Indica al histórico de ventas que únicamente se deben mostrar los pendientes.
     */
    private boolean filtrarPendientes;

    /**
     * Los datos para mostrar la confirmación.
     */
    private ConfirmarVentaMapaDatos datosConfirmar;

    /**
     * Manejador de formato de la aplicación.
     */
    private final transient AppFormatterService format;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    private final transient ExecutorService executor;

    /**
     * Manejador de plantillas.
     */
    private final transient TemplateService templateService;

    /**
     * Constructor con las dependencias.
     * @param format el Manejador de formato de la aplicación
     * @param executor el Servicio de ejecución de código asíncrono
     * @param templateService el Manejador de plantillas
     */
    @Inject
    public VentaMapaServiceBean(final AppFormatterService format,
                                final ExecutorService executor,
                                final TemplateService templateService) {
        this.format = format;
        this.executor = executor;
        this.templateService = templateService;
    }

    @Override
    public final ConfirmarVentaMapaDatos getDatosConfirmar() {
        return datosConfirmar;
    }

    @Override
    public final void setDatosConfirmar(final ConfirmarVentaMapaDatos datosConfirmar) {
        this.datosConfirmar = datosConfirmar;
    }

    @Override
    public final ZonaMapa getZonaSeleccionada() {
        return zonaSeleccionada;
    }

    @Override
    public final void setZonaSeleccionada(final ZonaMapa zonaSeleccionada) {
        this.zonaSeleccionada = zonaSeleccionada;
    }

    @Override
    public final int getIdCeldaSeleccionada() {
        return idCeldaSeleccionada;
    }

    @Override
    public final void setIdCeldaSeleccionada(final int idCeldaSeleccionada) {
        this.idCeldaSeleccionada = idCeldaSeleccionada;
    }

    @Override
    public final boolean isCeldaMultiple() {
        return celdaMultiple;
    }

    @Override
    public final void setCeldaMultiple(final boolean celdaMultiple) {
        this.celdaMultiple = celdaMultiple;
    }

    @Override
    public final double getCostoTotal(final TransaccionCelda resp) {
        double valor = 0;
        final List<TransaccionCeldaDetalle> detalles = resp.getDetalles();
        if (detalles != null) {
            for (final TransaccionCeldaDetalle det : detalles) {
                valor += det.getCostototal();
            }
        }
        return valor;
    }

    @Override
    public final double getCostoPorMinuto(final TransaccionCelda resp) {
        double valor = 0;
        final List<TransaccionCeldaDetalle> detalles = resp.getDetalles();
        if (detalles != null) {
            for (final TransaccionCeldaDetalle det : detalles) {
                valor += det.getCostoxminuto();
            }
        }
        return valor;
    }

    @Override
    public final boolean isFiltrarPendientes() {
        return filtrarPendientes;
    }

    @Override
    public final void setFiltrarPendientes(final boolean filtrarPendientes) {
        this.filtrarPendientes = filtrarPendientes;
    }
}
