package co.com.mirecarga.vendedor.recargaenlinea;

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
import co.com.mirecarga.vendedor.api.RecargarEnLineaResponse;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraBluetooth;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraLocal;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.RespuestaConfiguracionImpresora;


/**
 * Fragmento con los datos de la página recarga en línea.
 */
public class ConfirmarRecargaEnLineaFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ConfirmarRecargaEnLinea";

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_saldo)
    transient TextView confirmarSaldo;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_numero_recargar)
    transient TextView confirmarNumeroRecargar;

    /**
     * Control de página.
     */
    @BindView(R.id.confirmar_operador)
    transient TextView confirmarOperador;

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
     * Control de página.
     */
    @BindView(R.id.confirmar_valor_recargar)
    transient TextView confirmarValorRecargar;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient RecargaEnLineaService recargaEnLineaService;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiVendedorService api;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    /**
     * Manejador de formato de la aplicación.
     */
    @Inject
    transient AppFormatterService format;

    /**
     * Servicio de impresión.
     */
    @Inject
    transient ImpresoraService impresoraService;

    /**
     * Manejador de plantillas.
     */
    @Inject
    transient TemplateService templateService;

    /**
     * Datos para imprimir.
     */
    private transient ConfirmarRecargaEnLineaDatos datosImprimir;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_confirmar_recarga_en_linea;
    }

    @Override
    protected final void consultarModelo() {
        final ConfirmarRecargaEnLineaDatos datosConfirmar = recargaEnLineaService.getDatosConfirmar();
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
    private void mostrarDatos(final ConfirmarRecargaEnLineaDatos datos) {
        confirmarSaldo.setText(getString(R.string.tu_saldo, datos.getSaldo()));
        confirmarOperador.setText(datos.getNombreOperador());
        confirmarNumeroRecargar.setText(datos.getNumeroRecargar());
        confirmarValorRecargar.setText(format.formatearDecimal(datos.getValorRecargar()));
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
    public void confirmarRecarga() {
        final ConfigVendedor config = configService.getConfig();
        final ConfirmarRecargaEnLineaDatos datos = recargaEnLineaService.getDatosConfirmar();
        final Integer idUsuario = getIdUsuario();

        if (isSesionActiva()) {
            subscribe(api.recargaEnLinea(idUsuario, config.getProductos().getIdRecargaEnLinea(),
                    datos.getNumeroRecargar(), datos.getIdOperador(), datos.getNombreOperador(),
                    datos.getValorRecargar()), this::recargaEnLineaRespuesta);
        }
    }

    /**
            * Procesa la respuesta de recarga en línea.
     * @param resp la respuesta del servicio
     */
    private void recargaEnLineaRespuesta(final RecargarEnLineaResponse resp) {
        if (TextUtils.equals(resp.getRet(), "0")) {
            datosImprimir = recargaEnLineaService.getDatosConfirmar();
            recargaEnLineaService.setDatosConfirmar(null);
            mostrarMensaje(getString(R.string.msg_recarga_en_linea_exitoso), resp.getCelular());
            contenedorBotonVenta.setVisibility(View.GONE);
            contenedorBotonImpresion.setVisibility(View.VISIBLE);
            final String auditoria = getString(R.string.msg_recarga_en_linea_auditoria,
                    resp.getCelular(), resp.getOperador(), resp.getValor());
            AppLog.debug(TAG, "Auditoría: %s", auditoria);
            subscribeSinProcesando(api.auditoria(getIdUsuario(), "Venta recarga en linea",
                    "Recarga en linea", resp.getCelular(), "App movil", auditoria,
                    Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO,
                    Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
                    this::respuestaAuditoria);
        } else {
            mostrarMensaje(getString(R.string.msg_recarga_en_linea_saldo_insuficiente));
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
    @OnClick(R.id.realizar_otra_recarga)
    public void realizarOtraVenta() {
        recargaEnLineaService.setDatosConfirmar(null);
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
        final String valorTexto = format.formatearDecimal(datosImprimir.getValorRecargar());
        valores.put("celular", datosImprimir.getNumeroRecargar());
        valores.put("operador", datosImprimir.getNombreOperador());
        valores.put("valor", valorTexto);
        final String texto = templateService.remplazar(R.raw.recarga_en_linea, valores);
        final String qrcode = String.format("Numero de celular:%s;Operador:%s;Valor:%s;",
                datosImprimir.getNumeroRecargar(), datosImprimir.getNombreOperador(), valorTexto);
        impresoraService.imprimir(this, texto, qrcode);
    }
}
