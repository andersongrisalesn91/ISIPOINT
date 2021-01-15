package co.com.mirecarga.vendedor.ventamapa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.api.TransaccionCeldaDetalle;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.mapa.ZonaPk;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.TemplateService;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraBluetooth;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraLocal;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.RespuestaConfiguracionImpresora;
import co.com.mirecarga.core.mapa.ZonaMapa;

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
     * Servicio de impresión.
     */
    private final transient ImpresoraService impresoraService;

    /**
     * Manejador de formato de la aplicación.
     */
    private final transient AppFormatterService format;

    /**
     * Manejador de plantillas.
     */
    private final transient TemplateService templateService;

    /**
     * Constructor con las dependencias.
     * @param impresoraService el servicio de impresoras
     * @param format el Manejador de formato de la aplicación
     * @param templateService el Manejador de plantillas
     */
    @Inject
    public VentaMapaServiceBean(final ImpresoraService impresoraService,
                                final AppFormatterService format,
                                final TemplateService templateService) {
        this.impresoraService = impresoraService;
        this.format = format;
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
    public final void imprimirCelda(final ZonaPk zonaPk, final TransaccionCelda celda,
                                    final AbstractAppFragment fragment) {
        final Map<String, String> valores = new HashMap<>();
        valores.put("fechaHora", format.formatearFechaHora(celda.getFechahoraentrada()));
        valores.put("vendedor", celda.getVendedor());
        valores.put("cliente", celda.getCliente());
        valores.put("id", String.valueOf(celda.getId()));
        valores.put("zona", celda.getParqueaderoyzona());
        valores.put("celda", String.valueOf(celda.getIdcelda()));
        valores.put("tipoVehiculo", String.valueOf(celda.getIdtipodevehiculo()));
        valores.put("placa", celda.getPlaca());
        valores.put("ingreso", format.formatearFechaHora(celda.getFechahoraentrada()));
        final List<TransaccionCeldaDetalle> detalles = celda.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            valores.put("paquete", "");
        } else {
            valores.put("paquete", String.valueOf(detalles.get(0).getIdtarifa()));
        }
        final double costoTotal = getCostoTotal(celda);
        final double costoPorMinuto = getCostoPorMinuto(celda);
        valores.put("costoPorMinuto", format.formatearDecimal(costoPorMinuto));
        valores.put("vigencia", format.formatearFechaHora(celda.getFechahoravigencia()));
        valores.put("costoTotal", format.formatearDecimal(costoTotal));
        final String texto = templateService.remplazar(R.raw.venta_mapa, valores);
        AppLog.debug(TAG, "texto %s", texto);
        try {
            final String qrcode = String.format("t:1;p:%s;d:%s;" +
                            "m:%s;z:%s;c:%s;pl:%s;n:%s",
                    zonaPk.getIdPais(),
                    zonaPk.getIdDepartamento(),
                    zonaPk.getIdMunicipio(),
                    zonaPk.getId(),
                    celda.getIdcelda(),
                    celda.getPlaca(),
                    celda.getParqueaderoyzona());
            impresoraService.imprimir(fragment, texto, qrcode);
        } catch (final Exception ex) {
            AppLog.error(TAG, ex, "Error al imprimir %s", texto);
        }
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
