package co.com.mirecarga.vendedor.ventatarjeta;

import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.ListaAutocompletar;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.Requerido;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.api.ApiZERService;
import co.com.mirecarga.vendedor.api.Cliente;
import co.com.mirecarga.vendedor.api.TarjetaPrepago;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Fragmento con los datos de la página de venta de tarjetaPrepagos de transito.
 */
public class VentaTarjetaFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VentaTarjetaFragment";

    /**
     * Id temporal definido por Luis Fernando 4/8/2018 mientras se agrega al método /perfil.
     */
    private static final int ID_EC = 1820;

    /**
     * El saldo actual.
     */
    private transient String saldoActual;

    /**
     * Control de página.
     */
    @BindView(R.id.saldoVentaTarjetaPrepago)
    transient TextView saldo;

    /**
     * Control de página.
     */
    @BindView(R.id.tarjetaPrepago)
    transient Spinner tarjetaPrepago;

    /**
     * Control de página.
     */
    @BindView(R.id.cliente)
    transient AutoCompleteTextView cliente;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<TarjetaPrepago> listaTarjetaPrepago;

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
    transient VentaTarjetaService service;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_venta_tarjeta;
    }

    @Override
    protected final void consultarModelo() {
        if (isSesionActiva()) {
            final ConfirmarVentaTarjetaDatos datosConfirmar = service.getDatosConfirmar();
            final String nombreCliente;
            final String nombreTarjetaPrepago;
            if (datosConfirmar == null) {
                // Debe borrar el contenido de los controles
                nombreCliente = null;
                nombreTarjetaPrepago = null;
            } else {
                if (datosConfirmar.getCliente() == null) {
                    nombreCliente = null;
                } else {
                    nombreCliente = datosConfirmar.getCliente().getNombre();
                }
                nombreTarjetaPrepago = datosConfirmar.getTarjetaPrepago().getSerial();
            }

            final Integer idVendedor = getIdUsuario();
            subscribe(apiZERService.getTarjetas(idVendedor, ID_EC), list -> {
                listaTarjetaPrepago = ListaSeleccion.iniciar(tarjetaPrepago, list,
                        getString(R.string.seleccione_tarjeta_prepago), TarjetaPrepago::getSerial);
                listaTarjetaPrepago.setTextoSeleccionado(tarjetaPrepago, nombreTarjetaPrepago);
            });

            subscribe(api.consultaSaldo(idVendedor,
                    configService.getConfig().getProductos().getIdPinTransito()),
                    resp -> {
                        saldoActual = resp.getSaldo();
                        saldo.setText(getString(R.string.tu_saldo, saldoActual));
                    }
            );

            subscribe(api.getClientes(idVendedor), list -> {
                listaCliente = ListaAutocompletar.iniciar(cliente, list, getString(R.string.seleccione_cliente), IdNombre::getNombre);
                listaCliente.setTextoSeleccionado(cliente, nombreCliente);
            });
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
        if (Requerido.verificar(cliente, tarjetaPrepago)) {
            final ConfirmarVentaTarjetaDatos datosConfirmar = new ConfirmarVentaTarjetaDatos();
            datosConfirmar.setSaldo(saldoActual);
            datosConfirmar.setTarjetaPrepago(listaTarjetaPrepago.getItem(tarjetaPrepago));
            datosConfirmar.setCliente(listaCliente.getItem(cliente));

            service.setDatosConfirmar(datosConfirmar);
            getNavegador().navegar(new ConfirmarVentaTarjetaFragment());
        }
    }
}
