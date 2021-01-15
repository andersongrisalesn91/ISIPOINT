package co.com.mirecarga.cliente.recarga;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.api.CompraPaqueteResponse;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.api.Paquete;
import co.com.mirecarga.core.api.Placa;
import co.com.mirecarga.core.api.Zona;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.Requerido;

/**
 * Fragmento para la compra de paquetes.
 */
public class CompraPaqueteFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "CompraPaquete";
    /**
     * Control de página.
     */
    @BindView(R.id.saldo)
    transient TextView saldo;
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
    @BindView(R.id.paquete)
    transient Spinner paquete;
    /**
     * Control de página.
     */
    @BindView(R.id.metodo_pago)
    transient TextView metodoPago;
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
    private transient ListaSeleccion<Paquete> listaPaquete;
    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient CompraPaqueteService service;
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
        return R.layout.fragment_compra_paquete;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected final void consultarModelo() {
        if (isSesionActiva()) {
            setTitulo(getString(R.string.titulo_compra_paquete));
            final Integer idCliente = getIdUsuario();
            final String codigoMetodoPago = service.getCodigoMetodoPago();
            metodoPago.setText(codigoMetodoPago);
            subscribe(api.consultaSaldo(idCliente,
                    configService.getConfig().getProductos().getIdPinTransito()),
                    resp -> saldo.setText(getString(R.string.tu_saldo, resp.getSaldo()))
            );
            subscribe(api.getZonas(idCliente),
                    list -> listaZona = ListaSeleccion.iniciar(zona, list,
                            getString(R.string.seleccione_zona), IdNombre::getNombre));
            subscribe(api.getPlacas(idCliente),
                    list -> listaPlaca = ListaSeleccion.iniciar(placa, list, Placa::getCodigo));
            subscribe(api.getPaquetes(),
                    list -> listaPaquete = ListaSeleccion.iniciar(paquete, list,
                            getString(R.string.seleccione_paquete), IdNombre::getNombre));
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
    @OnClick(R.id.vender)
    public void confirmarVenta() {
        if (isSesionActiva() && Requerido.verificar(zona, paquete)) {
            subscribe(api.compraPaquete(getIdUsuario(), service.getCodigoMetodoPago(),
                    listaPlaca.getItem(placa).getCodigo(), listaZona.getItem(zona).getId(),
                    listaPaquete.getItem(paquete).getId()), this::confirmarVentaRespuesta);
        }
    }

    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarVentaRespuesta(final CompraPaqueteResponse resp) {
        if (TextUtils.equals(resp.getRet(), "0")) {
            final String codigoMetodoPago = service.getCodigoMetodoPago();
            mostrarMensaje(getString(R.string.msg_compra_paquete_exitoso, listaPlaca.getItem(placa).getCodigo()));
            final String auditoria = getString(
                    R.string.msg_compra_paquete_auditoria, String.valueOf(listaPaquete.getItem(paquete).getValor()),
                    String.valueOf(listaZona.getItem(zona).getNombre()), listaPlaca.getItem(placa).getCodigo(),
                    codigoMetodoPago);
            AppLog.debug(TAG, "Auditoría: %s", auditoria);
            subscribeSinProcesando(api.auditoria(getIdUsuario(), "Recarga tarjeta", "Pin de transito",
                    codigoMetodoPago, "App movil", auditoria, String.valueOf(listaZona.getItem(zona).getId()),
                    listaPlaca.getItem(placa).getCodigo(),  String.valueOf(listaPaquete.getItem(paquete).getId()), Constantes.AUDITORIA_VACIO),
                    this::respuestaAuditoria);
            getNavegador().irAtras();
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
