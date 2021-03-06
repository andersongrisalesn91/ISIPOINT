package co.com.mirecarga.vendedor.ventamapa;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.api.Tarifa;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.IdDescripcion;
import co.com.mirecarga.core.util.ListaAutocompletar;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.Requerido;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.api.ApiZERService;
import co.com.mirecarga.vendedor.api.Cliente;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.core.mapa.ZonaMapa;

/**
 * Fragmento con los datos de la página de venta en el mapa.
 */
public class VentaMapaFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VentaMapaFragment";

    /**
     * El saldo actual.
     */
    private transient String saldoActual;

    /**
     * Control de página.
     */
    @BindView(R.id.saldoVentaPaquete)
    transient TextView saldo;

    /**
     * Control de página.
     */
    @BindView(R.id.tipo_vehiculo)
    transient Spinner tipoVehiculo;

    /**
     * Control de página.
     */
    @BindView(R.id.metodo_pago)
    transient Spinner metodoPago;

    /**
     * Control de página.
     */
    @BindView(R.id.tarifa)
    transient Spinner tarifa;

    /**
     * Control de página.
     */
    @BindView(R.id.zona)
    transient TextView zona;

    /**
     * Control de página.
     */
    @BindView(R.id.placa)
    transient AutoCompleteTextView placa;

    /**
     * Control de página.
     */
    @BindView(R.id.cliente)
    transient AutoCompleteTextView cliente;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<TipoVehiculo> listaTipoVehiculo;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<IdDescripcion> listaMetodoPago;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Tarifa> listaTarifa;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaAutocompletar<Cliente> listaCliente;

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
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient VentaMapaService service;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_venta_mapa;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected final void consultarModelo() {
        cliente.setOnItemClickListener((adapterView, view, i, l) -> {
            final String selected = (String) adapterView.getItemAtPosition(i);
            final Cliente item = listaCliente.getItem(selected);
            if (item == null) {
                AppLog.debug(TAG, "Cliente seleccionado NULL");
                actualizarControlPlacas(null);
            } else {
                AppLog.debug(TAG, "Cliente seleccionado %s %s", item.getId(), item.getNombre());
                actualizarControlPlacas(item.getId());
            }
        });
        placa.setOnTouchListener((v, event) -> {
            placa.showDropDown();
            return false;
        });
        if (isSesionActiva()) {
            final ConfirmarVentaMapaDatos datosConfirmar = service.getDatosConfirmar();
            // Las variables final se requieren así más adelante para usar en lambda
            final String nombreCliente;
            final String nombreTarifa;
            String nombreMetodoPago = null;
            String nombreTipoVehiculo = null;
            if (datosConfirmar == null) {
                // Debe borrar el contenido de los controles
                placa.setText("");
                nombreTarifa = null;
                nombreCliente = null;
            } else {
                if (datosConfirmar.getCliente() == null) {
                    nombreCliente = null;
                } else {
                    nombreCliente = datosConfirmar.getCliente().getNombre();
                }
                placa.setText(datosConfirmar.getPlaca());
                nombreTarifa = datosConfirmar.getTarifa().getNombre();
                if (datosConfirmar.getMetodoPago() != null) {
                    nombreMetodoPago = datosConfirmar.getMetodoPago().getDescripcion();
                }
                if (datosConfirmar.getTipoVehiculo() != null) {
                    nombreTipoVehiculo = datosConfirmar.getTipoVehiculo().getTipovehiculo();
                }
            }

            final ZonaMapa zonaMapa = service.getZonaSeleccionada();
            listaTipoVehiculo = ListaSeleccion.iniciar(tipoVehiculo, zonaMapa.getTiposVehiculoCeldaMultiple(),
                    getString(R.string.seleccione_tipo_vehiculo), TipoVehiculo::getTipovehiculo);
            listaTipoVehiculo.setTextoSeleccionado(tipoVehiculo, nombreTipoVehiculo);
            if (service.isCeldaMultiple()) {
                tipoVehiculo.setVisibility(View.VISIBLE);
            } else {
                tipoVehiculo.setVisibility(View.GONE);
            }

            final ConfigVendedor config = configService.getConfig();
            listaMetodoPago = ListaSeleccion.iniciar(metodoPago, config.getMetodosPago(),
                    getString(R.string.seleccione_metodo_pago), IdDescripcion::getDescripcion);
            listaMetodoPago.setTextoSeleccionado(metodoPago, nombreMetodoPago);
            // Se acordó ocultar hasta se cuente con un API en que dependa del cliente seleccionado
            // Si es anónimo, no se debe mostrar
            metodoPago.setVisibility(View.GONE);

            final int idCelda = service.getIdCeldaSeleccionada();
            subscribe(apiZERService.getTarifas(zonaMapa.getIdPais(), zonaMapa.getIdDepartamento(),
                    zonaMapa.getIdMunicipio(), zonaMapa.getId(), idCelda, zonaMapa.isPrepago()), list -> {
                listaTarifa = ListaSeleccion.iniciar(tarifa, list, getString(R.string.seleccione_tarifa), IdNombre::getNombre);
                listaTarifa.setTextoSeleccionado(tarifa, nombreTarifa);
            });


            final Integer idVendedor = getIdUsuario();
            this.zona.setText(zonaMapa.getNombre());

            subscribe(api.consultaSaldo(idVendedor,
                    configService.getConfig().getProductos().getIdPinTransito()),
                    resp -> {
                        saldoActual = resp.getSaldo();
                        saldo.setText(getString(R.string.tu_saldo, saldoActual));
                    }
            );

            actualizarControlPlacas(null);

            subscribe(api.getClientes(idVendedor), list -> {
                listaCliente = ListaAutocompletar.iniciar(cliente, list, getString(R.string.anonimo), IdNombre::getNombre);
                listaCliente.setTextoSeleccionado(cliente, nombreCliente);
            });
        }
    }

    /**
     * Actualiza el control de placas según el usuario seleccionado.
     * @param idCliente el identificador del usuario
     */
    private void actualizarControlPlacas(final Integer idCliente) {
        if (idCliente == null) {
            placa.setAdapter(null);
        } else if (isSesionActiva()) {
            subscribe(api.getPlacas(idCliente),
                    list -> ListaAutocompletar.iniciar(placa, list, IdNombre::getNombre));
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
    public void confirmar() {
        if (Requerido.verificar(tipoVehiculo, placa, zona, metodoPago, tarifa)) {
            final ConfirmarVentaMapaDatos datosConfirmar = new ConfirmarVentaMapaDatos();
            datosConfirmar.setSaldo(saldoActual);
            datosConfirmar.setTipoVehiculo(listaTipoVehiculo.getItem(tipoVehiculo));
            datosConfirmar.setPlaca(placa.getText().toString());
            datosConfirmar.setMetodoPago(listaMetodoPago.getItem(metodoPago));
            datosConfirmar.setTarifa(listaTarifa.getItem(tarifa));
            datosConfirmar.setZona(service.getZonaSeleccionada());
            datosConfirmar.setCliente(listaCliente.getItem(cliente));

            service.setDatosConfirmar(datosConfirmar);
            getNavegador().navegar(new ConfirmarVentaMapaFragment());
        }
    }
}
