package co.com.mirecarga.cliente.venta;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.api.FinalizarUsoPinResponse;
import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.Constantes;

/**
 * Fragmento con los datos de la página de finalizar de uso PIN.
 */
public class FinalizarUsoPinFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "FinalizarUsoPin";

    /**
     * Control de página.
     */
    @BindView(R.id.minutos_usados)
    transient TextView minutosUsados;
    /**
     * Control de página.
     */
    @BindView(R.id.valor_cobrado)
    transient TextView valorCobrado;
    /**
     * Control de página.
     */
    @BindView(R.id.saldo_disponible)
    transient TextView saldoDisponible;
    /**
     * Control de página.
     */
    @BindView(R.id.descripcion_metodo_pago)
    transient TextView descripcionMetodoPago;
    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient FinalizarUsoPinService service;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiClienteService api;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_finalizar_uso_pin;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected final void consultarModelo() {
        if (isSesionActiva()) {
            final String codigoMetodoPago = service.getCodigoMetodoPago();
            setTitulo(getString(R.string.titulo_finalizar_pin));
            descripcionMetodoPago.setText(getString(R.string.descripcion_metodo_pago, codigoMetodoPago));
            final Integer idCliente = getIdUsuario();
            subscribe(api.finalizarUsoPin(idCliente,
                    codigoMetodoPago), this::confirmarFinalizarRespuesta);
        }
    }
    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarFinalizarRespuesta(final FinalizarUsoPinResponse resp) {
        if (TextUtils.equals(resp.getRet(), "0")) {
            final String codigoMetodoPago = service.getCodigoMetodoPago();
            mostrarMensaje(getString(R.string.msg_finalizar_uso_pin_exitoso, codigoMetodoPago));
            minutosUsados.setText(resp.getTotalUnidades());
            valorCobrado.setText(resp.getValorCobrado());
            saldoDisponible.setText(resp.getSaldoDisponible());
            final String auditoria = getString(
                    R.string.msg_finalizar_uso_pin_auditoria, codigoMetodoPago);
            AppLog.debug(TAG, "Auditoría: %s", auditoria);
            subscribeSinProcesando(api.auditoria(getIdUsuario(), "Finalizar uso pin", "Pin de transito",
                    codigoMetodoPago, "App movil", auditoria, Constantes.AUDITORIA_VACIO,
                    Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
                    this::respuestaAuditoria);
        } else {
            mostrarMensaje(resp.getError());
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
    @OnClick(R.id.aceptar)
    public void aceptar() {
        getNavegador().irAtras();
    }

}
