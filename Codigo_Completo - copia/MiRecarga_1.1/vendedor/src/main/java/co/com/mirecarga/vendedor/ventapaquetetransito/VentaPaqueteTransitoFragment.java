package co.com.mirecarga.vendedor.ventapaquetetransito;

import android.annotation.SuppressLint;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.api.Paquete;
import co.com.mirecarga.core.api.Zona;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.IdDescripcion;
import co.com.mirecarga.core.util.ListaAutocompletar;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.Requerido;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.api.Cliente;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Fragmento con los datos de la página de venta de paquetes de transito.
 */
public class VentaPaqueteTransitoFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VentaPaqueteTransito";

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
    @BindView(R.id.metodo_pago)
    transient Spinner metodoPago;

    /**
     * Control de página.
     */
    @BindView(R.id.paquete)
    transient Spinner paquete;

    /**
     * Control de página.
     */
    @BindView(R.id.zona)
    transient Spinner zona;

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
    private transient ListaSeleccion<Zona> listaZona;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<IdDescripcion> listaMetodoPago;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Paquete> listaPaquete;

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
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient VentaPaqueteTransitoService service;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_venta_paquete_transito;
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
            final ConfirmarVentaPaqueteTransitoDatos datosConfirmar = service.getDatosConfirmar();
            final String nombreCliente;
            final String nombreZona;
            final String nombrePaquete;
            final String nombreMetodoPago;
            if (datosConfirmar == null) {
                // Debe borrar el contenido de los controles
                nombreCliente = null;
                placa.setText("");
                nombreZona = null;
                nombrePaquete = null;
                nombreMetodoPago = null;
            } else {
                if (datosConfirmar.getCliente() == null) {
                    nombreCliente = null;
                } else {
                    nombreCliente = datosConfirmar.getCliente().getNombre();
                }
                placa.setText(datosConfirmar.getPlaca());
                nombreZona = datosConfirmar.getZona().getNombre();
                nombrePaquete = datosConfirmar.getPaquete().getNombre();
                nombreMetodoPago = datosConfirmar.getMetodoPago().getDescripcion();
            }

            final ConfigVendedor config = configService.getConfig();
            listaMetodoPago = ListaSeleccion.iniciar(metodoPago, config.getMetodosPago(),
                    getString(R.string.seleccione_metodo_pago), IdDescripcion::getDescripcion);
            listaMetodoPago.setTextoSeleccionado(metodoPago, nombreMetodoPago);

            subscribe(api.getPaquetes(), list -> {
                listaPaquete = ListaSeleccion.iniciar(paquete, list, getString(R.string.seleccione_paquete), IdNombre::getNombre);
                listaPaquete.setTextoSeleccionado(paquete, nombrePaquete);
            });


            final Integer idVendedor = getIdUsuario();
            subscribe(api.getZonas(idVendedor), list -> {
                listaZona = ListaSeleccion.iniciar(zona, list, getString(R.string.seleccione_zona), IdNombre::getNombre);
                listaZona.setTextoSeleccionado(zona, nombreZona);
            });

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
        if (Requerido.verificar(placa, zona, metodoPago, paquete)) {

            final ConfirmarVentaPaqueteTransitoDatos datosConfirmar = new ConfirmarVentaPaqueteTransitoDatos();
            datosConfirmar.setSaldo(saldoActual);
            datosConfirmar.setPlaca(placa.getText().toString());
            datosConfirmar.setMetodoPago(listaMetodoPago.getItem(metodoPago));
            datosConfirmar.setPaquete(listaPaquete.getItem(paquete));
            datosConfirmar.setZona(listaZona.getItem(zona));
            datosConfirmar.setCliente(listaCliente.getItem(cliente));

            service.setDatosConfirmar(datosConfirmar);
            getNavegador().navegar(new ConfirmarVentaPaqueteTransitoFragment());
        }
    }
}
