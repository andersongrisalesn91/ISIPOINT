package co.com.mirecarga.vendedor.ventamapa;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.api.Tarifa;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.api.TransaccionCeldaDetalle;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.api.ApiZERService;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Fragmento con los datos de confirmación de la página de venta de paquetes de transito.
 */
public class PostpagoFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "PostpagoFragment";

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_saldo)
    transient TextView confirmarSaldo;

    /**
     * Control de página.
     */
    @BindView(R.id.hora_entrada)
    transient TextView horaEntrada;

    /**
     * Control de página.
     */
    @BindView(R.id.hora_salida)
    transient TextView horaSalida;

    /**
     * Control de página.
     */
    @BindView(R.id.total)
    transient TextView total;

    /**
     * Layout de página.
     */
    @BindView(R.id.contenedor_boton_venta)
    transient LinearLayout contenedorBotonVenta;

    /**
     * Control de página.
     */
    @BindView(R.id.contenedor_boton_impresion)
    transient LinearLayout contenedorBotonImpresion;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient VentaMapaService service;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiVendedorService api;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiZERService apiZERService;

    /**
     * Manejador de formato de la aplicación.
     */
    @Inject
    transient AppFormatterService format;

    /**
     * Datos iniciales de la transacción.
     */
    private transient TransaccionCelda celda;

    /**
     * Datos iniciales de la transacción.
     */
    private transient TransaccionCeldaDetalle detalle;

    /**
     * Datos para imprimir.
     */
    private transient TransaccionCelda respImprimir;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_postpago;
    }

    @Override
    protected final void consultarModelo() {
        final Integer idVendedor = getIdUsuario();
        subscribe(api.consultaSaldo(idVendedor,
                configService.getConfig().getProductos().getIdPinTransito()),
                resp -> {
                    confirmarSaldo.setText(getString(R.string.tu_saldo, resp.getSaldo()));
                }
        );
        horaEntrada.setText(format.formatearFechaHora(detalle.getFechahoraentrada()));
        horaSalida.setText(format.formatearFechaHora(detalle.getFechahorasalida()));
        total.setText(format.formatearDecimal(detalle.getCostototal()));
    }

    /**
     * Inicializa las variables antes de la carga.
     * @param celda2 los datos de la celda
     * @param detalle2 los datos del detalle a pagar
     */
    public final void inicializar(final TransaccionCelda celda2, final TransaccionCeldaDetalle detalle2) {
        this.celda = celda2;
        this.detalle = detalle2;
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.cancelar_venta)
    public void cancelar() {
        getNavegador().irAtras();
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.confirmar_venta)
    public void confirmarVenta() {
        final ConfigVendedor config = configService.getConfig();
        final Integer idVendedor = getIdUsuario();

        if (isSesionActiva()) {
            final ZonaMapa zonaMapa = service.getZonaSeleccionada();
            final Map<String, String> par = new HashMap<>();
            par.put("idTicket", String.valueOf(celda.getId()));
            par.put("idDetalleTicket", String.valueOf(detalle.getId()));
            par.put("placa", celda.getPlaca());
            subscribe(apiZERService.pagarCelda(zonaMapa.getIdPais(),
                    zonaMapa.getIdDepartamento(), zonaMapa.getIdMunicipio(), zonaMapa.getId(),
                    celda.getIdcelda(), par), this::confirmarVentaRespuesta);
        }
    }

    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarVentaRespuesta(final TransaccionCelda resp) {
        respImprimir = resp;
        mostrarMensaje(getString(R.string.msg_venta_paquete_exitoso, celda.getPlaca()));
        contenedorBotonVenta.setVisibility(View.GONE);
        contenedorBotonImpresion.setVisibility(View.VISIBLE);
        final double costoTotal = service.getCostoTotal(resp);
        final ZonaMapa zonaMapa = service.getZonaSeleccionada();
        final String auditoria = getString(
                R.string.msg_venta_paquete_auditoria,
                zonaMapa.getNombre(), String.valueOf(detalle.getIdtarifa()),
                celda.getPlaca(), format.formatearDecimal(costoTotal));
        AppLog.debug(TAG, "Auditoría: %s", auditoria);
        final String pin = String.valueOf(resp.getId());
        subscribeSinProcesando(api.auditoria(getIdUsuario(), "Compra pin", "Pin de transito",
                pin, "App movil", auditoria, String.valueOf(zonaMapa.getId()),
                celda.getPlaca(), Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
                this::respuestaAuditoria);
    }

    /**
     * Procesa la respuesta de auditoría.
     * @param resp la respuesta
     */
    private void respuestaAuditoria(final MiRecargaResponse resp) {
        AppLog.debug(TAG, "Respuesta de auditoría %s", resp);
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.realizar_otra_venta)
    public void realizarOtraVenta() {
        getNavegador().irAtras();
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.volver_inicio)
    public void volverInicio() {
        getNavegador().irInicio();
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.imprimir)
    public void imprimir() {
        final ZonaMapa zonaMapa = service.getZonaSeleccionada();
        service.imprimirCelda(zonaMapa, respImprimir, this);
    }
}
