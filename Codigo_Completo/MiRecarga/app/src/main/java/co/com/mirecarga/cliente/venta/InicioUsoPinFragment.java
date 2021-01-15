package co.com.mirecarga.cliente.venta;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.api.InicioUsoPinResponse;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.api.Placa;
import co.com.mirecarga.core.api.Tarjeta;
import co.com.mirecarga.core.api.Zona;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.Requerido;

/**
 * Fragmento con los datos de la página de inicio de uso PIN.
 */
public class InicioUsoPinFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "InicioUsoPIN";
    /**
     * Control de página.
     */
    @BindView(R.id.saldo)
    transient TextView saldo;
    /**
     * Control de página.
     */
    @BindView(R.id.metodo_pago)
    transient Spinner metodoPago;
    /**
     * Control de página.
     */
    @BindView(R.id.zona)
    transient Spinner zona;
    /**
     * Control de página.
     */
    @BindView(R.id.placa)
    transient Spinner placa;
    /**
     * Control de página.
     */
    @BindView(R.id.contenedor_datos_venta)
    transient LinearLayout contenedorDatosVenta;
    /**
     * Control de página.
     */
    @BindView(R.id.contenedor_datos)
    transient LinearLayout contenedorDatos;
    /**
     * Control de página.
     */
    @BindView(R.id.minutos_disponibles)
    transient TextView minutosDisponibles;
    /**
     * Control de página.
     */
    @BindView(R.id.valor_minuto)
    transient TextView valorMinuto;
    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Zona> listaZona;
    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Placa> listaPlaca;
    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Tarjeta> listaMetodoPago;
    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient InicioUsoPinService service;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiClienteService api;
    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigCliente> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_inicio_uso_pin;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected final void consultarModelo() {
        if (isSesionActiva()) {
            setTitulo(getString(R.string.titulo_inicio_pin));
            final InicioUsoPinDatos datosModelo = service.getDatosConfirmar();
            final Integer idCliente = getIdUsuario();
            final String codigoMetodoPago = datosModelo.getCodigoMetodoPago();
            subscribe(api.getTarjetas(idCliente), list -> {
                listaMetodoPago = ListaSeleccion.iniciar(metodoPago, list, getString(R.string.seleccione_metodo_pago), Tarjeta::getCodigo);
                listaMetodoPago.setTextoSeleccionado(metodoPago, codigoMetodoPago);
            });
            subscribe(api.consultaSaldo(idCliente,
                    configService.getConfig().getProductos().getIdPinTransito()),
                    resp -> saldo.setText(getString(R.string.tu_saldo, resp.getSaldo()))
            );
            subscribe(api.getZonas(idCliente), list -> listaZona = ListaSeleccion.iniciar(zona,
                    list, getString(R.string.seleccione_zona), IdNombre::getNombre));
            subscribe(api.getPlacas(idCliente), list -> listaPlaca = ListaSeleccion.iniciar(placa,
                    list, Placa::getCodigo));
        }
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.aceptar)
    public void aceptar() {
        getNavegador().irAtras();
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
    @OnClick(R.id.vender)
    public void confirmarVenta() {
        if (isSesionActiva() && Requerido.verificar(zona, metodoPago)) {
            subscribe(api.iniciarUsoPin(getIdUsuario(), listaMetodoPago.getItem(metodoPago).getCodigo(),
                    listaPlaca.getItem(placa).getCodigo(), listaZona.getItem(zona).getId()), this::confirmarVentaRespuesta);
        }
    }

    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarVentaRespuesta(final InicioUsoPinResponse resp) {
        if (TextUtils.equals(resp.getRet(), "0")) {
            mostrarMensaje(getString(R.string.msg_inicio_uso_pin_exitoso));
            minutosDisponibles.setText(resp.getMinutosDisponibles());
            valorMinuto.setText(resp.getValorMinuto());
            contenedorDatosVenta.setVisibility(View.VISIBLE);
            contenedorDatos.setVisibility(View.GONE);
            final String auditoria = getString(
                    R.string.msg_inicio_uso_pin_auditoria,
                    String.valueOf(listaZona.getItem(zona).getId()), listaPlaca.getItem(placa).getCodigo(),
                    listaMetodoPago.getItem(metodoPago).getCodigo());
            AppLog.debug(TAG, "Auditoría: %s", auditoria);
            subscribeSinProcesando(api.auditoria(getIdUsuario(), "Uso pin", "Pin de transito",
                    listaMetodoPago.getItem(metodoPago).getCodigo(), "App movil", auditoria, String.valueOf(listaZona.getItem(zona).getId()),
                    listaPlaca.getItem(placa).getCodigo(), Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
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
}
