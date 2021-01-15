package co.com.mirecarga.vendedor.ventapaquetetransito;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.core.util.TemplateService;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.api.VentaPaqueteTransitoResponse;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraBluetooth;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraLocal;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.RespuestaConfiguracionImpresora;

/**
 * Fragmento con los datos de confirmación de la página de venta de paquetes de transito.
 */
public class ConfirmarVentaPaqueteTransitoFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ConfirmarVentaPaquete";

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_saldo)
    transient TextView confirmarSaldo;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_metodo_pago)
    transient TextView confirmarMetodoPago;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_paquete)
    transient TextView confirmarPaquete;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_zona)
    transient TextView confirmarZona;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_placa)
    transient TextView confirmarPlaca;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_cliente)
    transient TextView confirmarCliente;

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
    transient VentaPaqueteTransitoService service;

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
     * Manejador de formato de la aplicación.
     */
    @Inject
    transient AppFormatterService format;

    /**
     * Manejador de plantillas.
     */
    @Inject
    transient TemplateService templateService;

    /**
     * Datos para imprimir.
     */
    private transient ConfirmarVentaPaqueteTransitoDatos datosImprimir;

    /**
     * Servicio de impresión.
     */
    @Inject
    transient ImpresoraService impresoraService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_confirmar_venta_paquete_transito;
    }

    @Override
    protected final void consultarModelo() {
        final ConfirmarVentaPaqueteTransitoDatos datosConfirmar = service.getDatosConfirmar();
        if (datosConfirmar == null) {
            getNavegador().irAtras();
        } else {
            mostrarDatos(datosConfirmar);
        }
    }

    /**
     * Muestra los datos al cargar la página.
     * @param datos los datos a mostrar
     */
    private void mostrarDatos(final ConfirmarVentaPaqueteTransitoDatos datos) {
        confirmarSaldo.setText(getString(R.string.tu_saldo, datos.getSaldo()));
        confirmarMetodoPago.setText(datos.getMetodoPago().getDescripcion());
        confirmarPaquete.setText(datos.getPaquete().getNombre());
        confirmarZona.setText(datos.getZona().getNombre());
        confirmarPlaca.setText(datos.getPlaca());
        if (datos.getCliente() == null) {
            confirmarCliente.setText(R.string.anonimo);
        } else {
            confirmarCliente.setText(datos.getCliente().getNombre());
        }
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
        final ConfirmarVentaPaqueteTransitoDatos datos = service.getDatosConfirmar();
        final Integer idVendedor = getIdUsuario();
        final int idCliente;
        if (datos.getCliente() == null) {
            idCliente = config.getIdClienteAnonimo();
        } else {
            idCliente = datos.getCliente().getId();
        }

        if (isSesionActiva()) {
            subscribe(api.ventaPaqueteTransito(idVendedor, config.getProductos().getIdPinTransito(),
                    datos.getPlaca(), datos.getPaquete().getId(), datos.getZona().getId(),
                    idCliente), this::confirmarVentaRespuesta);
        }
    }

    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarVentaRespuesta(final VentaPaqueteTransitoResponse resp) {
        if (TextUtils.equals(resp.getRet(), "0")) {
            final ConfirmarVentaPaqueteTransitoDatos datos = service.getDatosConfirmar();
            datosImprimir = datos;
            service.setDatosConfirmar(null);
            mostrarMensaje(getString(R.string.msg_venta_paquete_exitoso, datos.getPlaca()));
            contenedorBotonVenta.setVisibility(View.GONE);
            contenedorBotonImpresion.setVisibility(View.VISIBLE);
            final String auditoria = getString(
                    R.string.msg_venta_paquete_auditoria,
                    datos.getZona().getNombre(), datos.getPaquete().getNombre(),
                    datos.getPlaca(), resp.getValor());
            AppLog.debug(TAG, "Auditoría: %s", auditoria);
            subscribeSinProcesando(api.auditoria(getIdUsuario(), "Compra pin", "Pin de transito",
                    resp.getPin(), "App movil", auditoria, String.valueOf(datos.getZona().getId()),
                    datos.getPlaca(), Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
                    this::respuestaAuditoria);
        } else {
            mostrarMensaje(getString(R.string.msg_venta_paquete_saldo_insuficiente));
        }
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
        service.setDatosConfirmar(null);
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
        final Map<String, String> valores = new HashMap<>();
        valores.put("saldo", getString(R.string.tu_saldo, datosImprimir.getSaldo()));
        valores.put("metodoPago", datosImprimir.getMetodoPago().getDescripcion());
        valores.put("paquete", datosImprimir.getPaquete().getNombre());
        valores.put("zona", datosImprimir.getZona().getNombre());
        valores.put("placa", datosImprimir.getPlaca());
        final String cliente;
        if (datosImprimir.getCliente() == null) {
            cliente = getString(R.string.anonimo);
        } else {
            cliente = datosImprimir.getCliente().getNombre();
        }
        valores.put("cliente", cliente);
        final String texto = templateService.remplazar(R.raw.venta_paquete_transito, valores);
        final String qrcode = String.format("Cliente:%s;Placa:%s;Paquete:%s;Zona:%s;",
                cliente,
                datosImprimir.getPlaca(),
                datosImprimir.getPaquete().getNombre(),
                datosImprimir.getZona().getNombre());
        impresoraService.imprimir(this, texto, qrcode);
    }
}
