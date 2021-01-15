package co.com.mirecarga.vendedor.ventatarjeta;

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
import co.com.mirecarga.vendedor.api.ApiZERService;
import co.com.mirecarga.vendedor.api.TarjetaPrepago;
import co.com.mirecarga.vendedor.api.VentaTarjetaPrepagoDetalle;
import co.com.mirecarga.vendedor.api.VentaTarjetaPrepagoResponse;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraBluetooth;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraLocal;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.RespuestaConfiguracionImpresora;

/**
 * Fragmento con los datos de confirmación de la página de venta de tarjetaPrepagos de transito.
 */
public class ConfirmarVentaTarjetaFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ConfirmarVentaTarjeta";

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_saldo)
    transient TextView confirmarSaldo;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_tarjeta_prepago)
    transient TextView confirmarTarjetaPrepago;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_cliente)
    transient TextView confirmarCliente;

    /**
     * Control de página.
     */
    @BindView(R.id.precio)
    transient TextView precio;

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
    transient VentaTarjetaService service;

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
     * Manejador de plantillas.
     */
    @Inject
    transient TemplateService templateService;

    /**
     * Datos para imprimir.
     */
    private transient VentaTarjetaPrepagoResponse datosImprimir;

    /**
     * Servicio de impresión.
     */
    @Inject
    transient ImpresoraService impresoraService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_confirmar_venta_tarjeta;
    }

    @Override
    protected final void consultarModelo() {
        final ConfirmarVentaTarjetaDatos datosConfirmar = service.getDatosConfirmar();
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
    private void mostrarDatos(final ConfirmarVentaTarjetaDatos datos) {
        datos.setPrecio(null);
        confirmarSaldo.setText(getString(R.string.tu_saldo, datos.getSaldo()));
        confirmarTarjetaPrepago.setText(datos.getTarjetaPrepago().getSerial());
        if (datos.getCliente() == null) {
            confirmarCliente.setText(R.string.anonimo);
        } else {
            confirmarCliente.setText(datos.getCliente().getNombre());
        }
        final int idproducto = datos.getTarjetaPrepago().getIdproducto();
        final Integer idVendedor = getIdUsuario();
        subscribe(apiZERService.getPrecioProducto(idVendedor, idproducto), resp -> {
            precio.setText(format.formatearDecimal(resp.getValorasbigdecimal().doubleValue()));
            datos.setPrecio(resp.getValorasbigdecimal());
        });
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
        final ConfirmarVentaTarjetaDatos datos = service.getDatosConfirmar();
        final Integer idVendedor = getIdUsuario();
        final int idCliente;
        if (datos.getCliente() == null) {
            idCliente = config.getIdClienteAnonimo();
        } else {
            idCliente = datos.getCliente().getId();
        }

        if (isSesionActiva()) {
            final TarjetaPrepago tarjetaPrepago = datos.getTarjetaPrepago();
            subscribe(apiZERService.ventaTarjetaPrepago(idVendedor, tarjetaPrepago.getId(),
                    tarjetaPrepago.getSerial(), tarjetaPrepago.getIdec(),
                    idCliente), this::confirmarVentaRespuesta);
        }
    }

    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarVentaRespuesta(final VentaTarjetaPrepagoResponse resp) {
        if (resp == null || resp.getDetalles() == null || resp.getDetalles().isEmpty()) {
            mostrarMensaje(getString(R.string.msg_venta_tarjeta_saldo_insuficiente));
        } else {
            final ConfirmarVentaTarjetaDatos datos = service.getDatosConfirmar();
            datosImprimir = resp;
            service.setDatosConfirmar(null);
            mostrarMensaje(getString(R.string.msg_venta_tarjeta_exitoso));
            contenedorBotonVenta.setVisibility(View.GONE);
            contenedorBotonImpresion.setVisibility(View.VISIBLE);
            final String cliente;
            if (datosImprimir.getCliente() == null) {
                cliente = getString(R.string.anonimo);
            } else {
                cliente = datosImprimir.getCliente();
            }
            final String auditoria = getString(
                    R.string.msg_venta_tarjeta_auditoria,
                    cliente, datos.getTarjetaPrepago().getSerial());
            AppLog.debug(TAG, "Auditoría: %s", auditoria);
            subscribeSinProcesando(api.auditoria(getIdUsuario(), "Venta tarjeta",
                    "Tarjeta",
                    String.valueOf(resp.getId()), "App movil", auditoria, Constantes.AUDITORIA_VACIO,
                    Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
                    this::respuestaAuditoria);
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
        valores.put("saldo", getString(R.string.tu_saldo,
                format.formatearDecimal(datosImprimir.getNuevosaldo())));
        valores.put("valortotal", format.formatearDecimal(datosImprimir.getValortotal()));
        final VentaTarjetaPrepagoDetalle detalle = datosImprimir.getDetalles().get(0);
        final String serial = detalle.getSerial();
        valores.put("serial", serial);
        valores.put("producto", detalle.getProducto());
        valores.put("valorunitario", format.formatearDecimal(detalle.getValorunitario()));
        final String cliente;
        if (datosImprimir.getCliente() == null) {
            cliente = getString(R.string.anonimo);
        } else {
            cliente = datosImprimir.getCliente();
        }
        valores.put("cliente", cliente);
        final String texto = templateService.remplazar(R.raw.venta_tarjeta_prepago, valores);
        final String qrcode = String.format("Cliente:%s;Serial:%s;",
                cliente, serial);
        impresoraService.imprimir(this, texto, qrcode);
    }
}
