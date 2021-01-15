package co.com.mirecarga.vendedor.ventapintransito;

import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.api.Paquete;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.ConfigService;
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
public class VentaPinTransitoFragment extends AbstractAppFragment {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VentaPinTransito";

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
    @BindView(R.id.paquete)
    transient Spinner paquete;

    /**
     * Control de página.
     */
    @BindView(R.id.cliente)
    transient AutoCompleteTextView cliente;

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
    transient VentaPinTransitoService service;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_venta_pin_transito;
    }

    @Override
    protected final void consultarModelo() {
        if (isSesionActiva()) {
            final ConfirmarVentaPinTransitoDatos datosConfirmar = service.getDatosConfirmar();
            final String nombreCliente;
            final String nombrePaquete;
            if (datosConfirmar == null) {
                // Debe borrar el contenido de los controles
                nombreCliente = null;
                nombrePaquete = null;
            } else {
                if (datosConfirmar.getCliente() == null) {
                    nombreCliente = null;
                } else {
                    nombreCliente = datosConfirmar.getCliente().getNombre();
                }
                nombrePaquete = datosConfirmar.getPaquete().getNombre();
            }

            final ConfigVendedor config = configService.getConfig();

            subscribe(api.getPaquetes(), list -> {
                listaPaquete = ListaSeleccion.iniciar(paquete, list, getString(R.string.seleccione_paquete), IdNombre::getNombre);
                listaPaquete.setTextoSeleccionado(paquete, nombrePaquete);
            });

            final Integer idVendedor = getIdUsuario();
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
        if (Requerido.verificar(cliente, paquete)) {
            final ConfirmarVentaPinTransitoDatos datosConfirmar = new ConfirmarVentaPinTransitoDatos();
            datosConfirmar.setSaldo(saldoActual);
            datosConfirmar.setPaquete(listaPaquete.getItem(paquete));
            datosConfirmar.setCliente(listaCliente.getItem(cliente));

            service.setDatosConfirmar(datosConfirmar);
            getNavegador().navegar(new ConfirmarVentaPinTransitoFragment());
        }
    }
}
