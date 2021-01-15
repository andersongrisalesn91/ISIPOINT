package co.com.mirecarga.vendedor.configuracionimpresora;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.TemplateService;
import co.com.mirecarga.core.util.Validador;
import co.com.mirecarga.vendedor.R;

/**
 * Fragmento con los datos de la página configuración impresora para el vendedor.
 */
public class ConfiguracionImpresoraFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ConfiguracionImpresoraF";

    /**
     * Control de página.
     */
    @BindView(R.id.tipo_impresora)
    transient RadioGroup tipoImpresora;

    /**
     * Control de página.
     */
    @BindView(R.id.tipo_impresora_local)
    transient RadioButton tipoImpresoraLocal;

    /**
     * Control de página.
     */
    @BindView(R.id.tipo_impresora_bluetooth)
    transient RadioButton tipoImpresoraBluetooth;

    /**
     * Control de página.
     */
    @BindView(R.id.tipo_impresora_zebra)
    transient RadioButton tipoImpresoraZebra;

    /**
     * Control de página.
     */
    @BindView(R.id.mac_impresora_zebra)
    transient EditText macImpresoraZebra;

    /**
     * Control de página.
     */
    @BindView(R.id.nombre_impresora_bluetooth)
    transient EditText nombreImpresoraBluetooth;

    /**
     * Control de página.
     */
    @BindView(R.id.contenedor_bluetooth_info)
    transient LinearLayout contenedorBluetoothInfo;

    /**
     * Control de página.
     */
    @BindView(R.id.contenedor_zebra_info)
    transient LinearLayout contenedorZebraInfo;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient ConfiguracionImpresoraService service;

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

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_configuracion_impresora;
    }

    @Override
    protected final void consultarModelo() {
        setTitulo(getString(R.string.titulo_configuracion_impresora));
        final RespuestaConfiguracionImpresora datosModelo = service.consultarModelo();
        mostrarDatos(datosModelo);
        tipoImpresora.setOnCheckedChangeListener((group, checkedId) -> actualizarTipoImpresora(checkedId));
    }

    /**
     * Actualiza el tipo de impresora.
     * @param idControl el radio seleccionado
     */
    private void actualizarTipoImpresora(final int idControl) {
        contenedorBluetoothInfo.setVisibility(View.GONE);
        contenedorZebraInfo.setVisibility(View.GONE);
        switch (idControl) {
            case R.id.tipo_impresora_local:
                mostrarMensaje(getString(R.string.impresora_local));
                service.actualizarPreferencia(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_PREFERENCIA,
                ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_LOCAL);
                break;
            case R.id.tipo_impresora_bluetooth:
                mostrarMensaje(getString(R.string.impresora_bluetooth));
                service.actualizarPreferencia(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_PREFERENCIA,
                ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_BLUETOOTH);
                contenedorBluetoothInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.tipo_impresora_zebra:
                mostrarMensaje(getString(R.string.impresora_zebra));
                service.actualizarPreferencia(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_PREFERENCIA,
                        ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_ZEBRA);
                contenedorZebraInfo.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * Muestra los datos al cargar la página.
     * @param datos los datos a mostrar
     */
    private void mostrarDatos(final RespuestaConfiguracionImpresora datos) {
        contenedorBluetoothInfo.setVisibility(View.GONE);
        contenedorZebraInfo.setVisibility(View.GONE);
        if (datos.getTipoImpresora().equals(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_LOCAL)) {
            tipoImpresoraLocal.setChecked(true);
        } else if (datos.getTipoImpresora().equals(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_BLUETOOTH))  {
            tipoImpresoraBluetooth.setChecked(true);
            contenedorBluetoothInfo.setVisibility(View.VISIBLE);
        } else if (datos.getTipoImpresora().equals(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_ZEBRA))  {
            tipoImpresoraZebra.setChecked(true);
            contenedorZebraInfo.setVisibility(View.VISIBLE);
        }
        macImpresoraZebra.setText(datos.getMacImpresoraBluetooth());
        nombreImpresoraBluetooth.setText(datos.getNombreImpresoraBluetooth());
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.guardar_zebra)
    public void guardarZebra() {
        final Validador validador = new Validador();
        validador.requerido(macImpresoraZebra);
        if (validador.isValido()) {
            service.actualizarPreferencia(ConfiguracionImpresoraServiceBean.IMPRESORA_DIRECCION_MAC_PREFERENCIA,
                    macImpresoraZebra.getText().toString());
            mostrarMensaje(getString(R.string.msg_mac_impresora_exitoso));
        }
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.guardar_bluetooth)
    public void guardarBluetooth() {
        final Validador validador = new Validador();
        validador.requerido(nombreImpresoraBluetooth);
        if (validador.isValido()) {
            service.actualizarPreferencia(ConfiguracionImpresoraServiceBean.IMPRESORA_NOMBRE_PREFERENCIA,
                    nombreImpresoraBluetooth.getText().toString());
            mostrarMensaje(getString(R.string.msg_nombre_impresora_exitoso));
        }
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.probar_impresora)
    public void probarImpresora() {
        final Map<String, String> valores = new HashMap<>();
        final String texto = templateService.remplazar(R.raw.probar_impresora, valores);
        AppLog.debug(TAG, "texto %s", texto);
        try {
            final String qrcode = String.format("Prueba:%s;Impresora:%s;",
                    1, 2);
            impresoraService.imprimir(this, texto, qrcode);
        } catch (final Exception ex) {
            AppLog.error(TAG, ex, "Error al imprimir %s", texto);
        }
    }
}
