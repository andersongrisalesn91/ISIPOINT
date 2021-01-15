package co.com.mirecarga.vendedor.reportes;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.TemplateService;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ReporteItem;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraBluetooth;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraLocal;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.RespuestaConfiguracionImpresora;


/**
 * Fragmento con los datos de la página Ver Detalle de la consulta por cliente.
 */
public class VerDetalleFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VerDetalleFragment";

    /**
     * Registro que se debe mostrar.
     */
    private ReporteItem item;

    /**
     * Control de página.
     */
    @BindView(R.id.pin)
    transient TextView pin;

    /**
     * Control de página.
     */
    @BindView(R.id.fechaInicial)
    transient TextView fechaInicial;

    /**
     * Control de página.
     */
    @BindView(R.id.fechaFinal)
    transient TextView fechaFinal;

    /**
     * Control de página.
     */
    @BindView(R.id.saldo)
    transient TextView saldo;

    /**
     * Control de página.
     */
    @BindView(R.id.imprimir)
    transient Button imprimir;

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
     * Manejador de plantillas.
     */
    @Inject
    transient TemplateService templateService;

    /**
     * Servicio de impresión.
     */
    @Inject
    transient ImpresoraService impresoraService;

    /**
     * El servicio de configuración de impresoras.
     */
    @Inject
    transient ConfiguracionImpresoraService configuracionImpresoraService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_ver_detalle;
    }

    /**
     * Regresa el campo item.
     *
     * @return el valor de item
     */
    public ReporteItem getItem() {
        return item;
    }

    /**
     * Establece el valor del campo item.
     *
     * @param item el valor a establecer
     */
    public void setItem(final ReporteItem item) {
        this.item = item;
    }

    @Override
    protected final void consultarModelo() {
        pin.setText(item.getKeyTwo());
        fechaInicial.setText(format.formatearFechaHora(item.getFechaCreacion()));
        fechaFinal.setText(format.formatearFechaHora(item.getFechaFin()));
        saldo.setText(format.formatearDecimal(item.getValorSaldoBalance()));
        final RespuestaConfiguracionImpresora impresora = configuracionImpresoraService.consultarModelo();
        if (TextUtils.isEmpty(impresora.getTipoImpresora())) {
            imprimir.setVisibility(View.GONE);
            mostrarMensaje(getString(R.string.msg_configurar_impresora));
        }
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.imprimir)
    public void imprimirClick() {
        final Map<String, String> valores = new HashMap<>();
        final String pinTexto = item.getKeyTwo();
        final String saldoTexto = format.formatearDecimal(item.getValorSaldoBalance());
        final String fechaInicioTexto = format.formatearFechaHora(item.getFechaCreacion());
        final String fechaFinTexto = format.formatearFechaHora(item.getFechaFin());
        valores.put("pin", pinTexto);
        valores.put("fechaInicio", fechaInicioTexto);
        valores.put("fechaFin", fechaFinTexto);
        valores.put("saldo", saldoTexto);
        final String texto = templateService.remplazar(R.raw.ver_detalle, valores);
        try {
            final String qrcode = String.format("Pin No:%s;Saldo:%s;Fecha Inicio:%s;Fecha Fin:%s;",
                    pinTexto, saldoTexto, fechaInicioTexto, fechaFinTexto);
            impresoraService.imprimir(this, texto, qrcode);
        } catch (final Exception ex) {
            AppLog.error(TAG, ex, "Error al imprimir %s", texto);
        }
    }
}
