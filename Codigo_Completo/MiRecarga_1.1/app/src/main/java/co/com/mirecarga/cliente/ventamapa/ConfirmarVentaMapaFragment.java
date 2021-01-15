package co.com.mirecarga.cliente.ventamapa;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.core.api.MiRecargaResponse;
import co.com.mirecarga.core.api.Tarifa;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.api.ApiZERService;
import co.com.mirecarga.cliente.app.ConfigCliente;

/**
 * Fragmento con los datos de confirmación de la página de venta de paquetes de transito.
 */
public class ConfirmarVentaMapaFragment extends AbstractAppFragment {
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
    @BindView(R.id.confirmar_metodo_pago_label)
    transient TextView confirmarMetodoPagoLabel;

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
    transient ConfigService<ConfigCliente> configService;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiClienteService api;

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
     * Datos para imprimir.
     */
    private transient TransaccionCelda respImprimir;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_confirmar_venta_paquete_transito;
    }

    @Override
    protected final void consultarModelo() {
        final ConfirmarVentaMapaDatos datosConfirmar = service.getDatosConfirmar();
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
    private void mostrarDatos(final ConfirmarVentaMapaDatos datos) {
        confirmarSaldo.setText(getString(R.string.tu_saldo, datos.getSaldo()));
        if (datos.getMetodoPago() == null) {
            confirmarMetodoPagoLabel.setVisibility(View.GONE);
            confirmarMetodoPago.setVisibility(View.GONE);
        } else {
            confirmarMetodoPago.setText(datos.getMetodoPago().getDescripcion());
        }
        confirmarPaquete.setText(datos.getTarifa().getNombre());
        confirmarZona.setText(datos.getZona().getNombre());
        confirmarPlaca.setText(datos.getPlaca());
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
        final ConfigCliente config = configService.getConfig();
        final ConfirmarVentaMapaDatos datos = service.getDatosConfirmar();
        final Integer idCliente = getIdUsuario();

        if (isSesionActiva()) {
            final Tarifa tarifa = datos.getTarifa();
            final ZonaMapa zonaMapa = datos.getZona();
            final int idCelda = service.getIdCeldaSeleccionada();
            final Map<String, String> par = new HashMap<>();
            par.put("idVendedor", String.valueOf(idCliente));
            par.put("idCliente", String.valueOf(idCliente));
            par.put("placa", datos.getPlaca());
            par.put("idTarifa", String.valueOf(tarifa.getId()));
            par.put("idUnidadDeTiempo", String.valueOf(tarifa.getIdunidaddetiempo()));
            final int idTipoVehiculo;
            final TipoVehiculo tipoVehiculo = datos.getTipoVehiculo();
            if (tipoVehiculo == null) {
                idTipoVehiculo = tarifa.getIdtipovehiculo();
            } else {
                idTipoVehiculo = tipoVehiculo.getId();
            }
            par.put("idTipoVehiculo", String.valueOf(idTipoVehiculo));
            subscribe(apiZERService.registrar(zonaMapa.getIdPais(), zonaMapa.getIdDepartamento(),
                    zonaMapa.getIdMunicipio(), zonaMapa.getId(), idCelda, par), this::confirmarVentaRespuesta);
        }
    }

    /**
     * Procesa la respuesta del servidor.
     * @param resp la respuesta del servidor
     */
    private void confirmarVentaRespuesta(final TransaccionCelda resp) {
        final ConfirmarVentaMapaDatos datos = service.getDatosConfirmar();
        respImprimir = resp;
        service.setDatosConfirmar(null);
        mostrarMensaje(getString(R.string.msg_venta_paquete_exitoso, datos.getPlaca()));
        contenedorBotonVenta.setVisibility(View.GONE);
        contenedorBotonImpresion.setVisibility(View.VISIBLE);
        final double costoTotal = service.getCostoTotal(resp);
        final String auditoria = getString(
                R.string.msg_venta_paquete_auditoria,
                datos.getZona().getNombre(), datos.getTarifa().getNombre(),
                datos.getPlaca(), format.formatearDecimal(costoTotal));
        AppLog.debug(TAG, "Auditoría: %s", auditoria);
        final String pin = String.valueOf(resp.getId());
        subscribeSinProcesando(api.auditoria(getIdUsuario(), "Compra pin", "Pin de transito",
                pin, "App movil", auditoria, String.valueOf(datos.getZona().getId()),
                datos.getPlaca(), Constantes.AUDITORIA_VACIO, Constantes.AUDITORIA_VACIO),
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
    @OnClick(R.id.volver_inicio)
    public void volverInicio() {
        getNavegador().irInicio();
    }
}
