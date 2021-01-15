package co.com.mirecarga.vendedor.recargaenlinea;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.Validador;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;
import co.com.mirecarga.vendedor.api.Operador;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Fragmento con los datos de la página recarga en línea.
 */
public class RecargaEnLineaFragment extends AbstractAppFragment {
    /**
     * El saldo actual.
     */
    private transient String saldoActual;

    /**
     * Control de página.
     */
    @BindView(R.id.saldo)
    transient TextView saldo;

    /**
     * Control de página.
     */
    @BindView(R.id.numero_recargar)
    transient EditText numeroRecargar;

    /**
     * Control de página.
     */
    @BindView(R.id.operador)
    transient Spinner operador;

    /**
     * Control de página.
     */
    @BindView(R.id.valor_recargar)
    transient EditText valorRecargar;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient RecargaEnLineaService recargaEnLineaService;

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiVendedorService api;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Operador> listaOperador;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_recarga_en_linea;
    }

    @Override
    protected final void consultarModelo() {
        if (isSesionActiva()) {
            final ConfirmarRecargaEnLineaDatos datosConfirmar = recargaEnLineaService.getDatosConfirmar();
            final String nombreOperador;
            if (datosConfirmar == null) {
                // Debe borrar el contenido de los controles
                numeroRecargar.setText(null);
                valorRecargar.setText(null);
                nombreOperador = null;
            } else {
                numeroRecargar.setText(datosConfirmar.getNumeroRecargar());
                valorRecargar.setText(String.valueOf(datosConfirmar.getValorRecargar()));
                nombreOperador = datosConfirmar.getNombreOperador();
            }

            subscribe(api.getOperadores(), list -> {
                listaOperador = ListaSeleccion.iniciar(operador, list, getString(R.string.seleccione_operador), Operador::getNombre);
                listaOperador.setTextoSeleccionado(operador, nombreOperador);
            });

            subscribe(api.consultaSaldo(getIdUsuario(),
                    configService.getConfig().getProductos().getIdRecargaEnLinea()),
                    resp -> {
                        saldoActual = resp.getSaldo();
                        saldo.setText(getString(R.string.tu_saldo, saldoActual));
                    }
            );
        }
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.vender)
    public void confirmar() {
        final Validador validador = new Validador();
        validador.requerido(numeroRecargar, operador, valorRecargar);
        validador.celular(numeroRecargar);
        validador.rango(valorRecargar, configService.getConfig().getRecargaEnLinea());
        if (isSesionActiva()
                && validador.isValido()) {

            final Operador operadorSeleccionado = listaOperador.getItem(operador);

            final ConfirmarRecargaEnLineaDatos datosConfirmar = new ConfirmarRecargaEnLineaDatos();
            datosConfirmar.setIdOperador(operadorSeleccionado.getId());
            datosConfirmar.setNombreOperador(operadorSeleccionado.getNombre());
            datosConfirmar.setNumeroRecargar(numeroRecargar.getText().toString());
            datosConfirmar.setValorRecargar(Integer.parseInt(valorRecargar.getText().toString()));
            datosConfirmar.setSaldo(saldoActual);

            recargaEnLineaService.setDatosConfirmar(datosConfirmar);

            getNavegador().navegar(new ConfirmarRecargaEnLineaFragment());
        }
    }

}
