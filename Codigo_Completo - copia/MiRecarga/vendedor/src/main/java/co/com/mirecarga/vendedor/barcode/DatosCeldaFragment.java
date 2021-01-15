package co.com.mirecarga.vendedor.barcode;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.osmdroid.views.overlay.infowindow.InfoWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.EstadoCelda;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.api.TransaccionCeldaDetalle;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.mapa.ZonaMapa;
import co.com.mirecarga.core.mapa.ZonaPk;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiZERService;
import co.com.mirecarga.vendedor.mapa.FiltroMapa;
import co.com.mirecarga.vendedor.mapa.MapaVendedorService;
import co.com.mirecarga.vendedor.ventamapa.PostpagoFragment;
import co.com.mirecarga.vendedor.ventamapa.VentaMapaService;

/**
 * Fragmento con los datos de la celda.
 */
public class DatosCeldaFragment extends AbstractAppFragment {
    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient MapaVendedorService mapaVendedorServiceService;
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiZERService apiZERService;
    /**
     * Manejador de formato de la aplicación.
     */
    @Inject
    protected transient AppFormatterService format;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient VentaMapaService ventaMapaService;

    /**
     * Control de página.
     */
    @BindView(R.id.imprimir)
    transient ImageView imprimir;

    /**
     * Control de página.
     */
    @BindView(R.id.salir)
    transient ImageView salir;

    /**
     * Control de página.
     */
    @BindView(R.id.hora_inicio)
    transient TextView horaInicio;

    /**
     * Control de página.
     */
    @BindView(R.id.hora_vencimiento)
    transient TextView horaVencimiento;

    /**
     * Control de página.
     */
    @BindView(R.id.cliente)
    transient TextView cliente;

    /**
     * Control de página.
     */
    @BindView(R.id.placa)
    transient TextView placa;

    /**
     * Control de página.
     */
    @BindView(R.id.tiempo_vencerse)
    transient TextView tiempoVencerse;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<EstadoCelda> listaEstado;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<TipoVehiculo> listaTipoVehiculo;

    /**
     * Datos de la llave de la zona.
     */
    private final ZonaPk zonaPk = new ZonaPk();

    /**
     * Id de la celda de la celda a abrir.
     */
    private int idCelda;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_datos_celda;
    }

    /**
     * Lee los datos desde el código QR.
     * @param qrcode el código QR.
     */
    public void leerQrCode(final String qrcode) {
        final String[] split = TextUtils.split(qrcode, ";");
        for (String texto : split) {
            final String[] split2 = TextUtils.split(texto, ":");
            if (split2.length == 2) {
                final String key = split2[0];
                final String valor = split2[1];
                switch (key) {
                    case "c":
                        idCelda = Integer.valueOf(valor);
                        break;
                    case "z":
                        zonaPk.setId(Integer.valueOf(valor));
                        break;
                    case "m":
                        zonaPk.setIdMunicipio(Integer.valueOf(valor));
                        break;
                    case "d":
                        zonaPk.setIdDepartamento(Integer.valueOf(valor));
                        break;
                    case "p":
                        zonaPk.setIdPais(Integer.valueOf(valor));
                        break;
                }
            }
        }
    }

    @Override
    protected final void consultarModelo() {
        setTitulo(getString(R.string.celda_ocupada_celda, String.valueOf(idCelda)));
        imprimir.setVisibility(View.GONE);
        salir.setVisibility(View.GONE);
        subscribe(apiZERService.getTransaccionesPorCelda(zonaPk.getIdPais(), zonaPk.getIdDepartamento(),
                zonaPk.getIdMunicipio(), zonaPk.getId(), idCelda), resp -> {
            if (!resp.isEmpty()) {
                final TransaccionCelda celda = resp.get(0);
                horaInicio.setText(getString(R.string.celda_ocupada_hora_inicio, format.formatearFechaHora(celda.getFechahoraentrada())));
                horaVencimiento.setText(getString(R.string.celda_ocupada_hora_vencimiento, format.formatearFechaHora(celda.getFechahoravigencia())));
                cliente.setText(getString(R.string.celda_ocupada_cliente, celda.getCliente()));
                placa.setText(getString(R.string.celda_ocupada_placa, celda.getPlaca()));
                tiempoVencerse.setText(getString(R.string.celda_ocupada_tiempo_vencerese, String.valueOf(celda.getHms())));

                imprimir.setOnClickListener(view -> imprimirCelda(celda));
                salir.setOnClickListener(view -> salirCelda(celda));
                imprimir.setVisibility(View.VISIBLE);
                salir.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Procesa el evento del botón imprimirCelda.
     * @param celda los datos de la celda a imprimir
     */
    private void imprimirCelda(final TransaccionCelda celda) {
        ventaMapaService.imprimirCelda(zonaPk, celda, this);
    }

    /**
     * Procesa la respuesta al liberar la celda.
     * @param resp la respuesta del servidor
     */
    private void procesarRespuestaSalirCelda(final TransaccionCelda resp) {
        if (resp.isPagopendiente()) {
            // Busca el pago pendiente
            for (TransaccionCeldaDetalle detalle : resp.getDetalles()) {
                if (!detalle.isPagado()) {
                    final PostpagoFragment fragment = new PostpagoFragment();
                    fragment.inicializar(resp, detalle);
                    getNavegador().navegar(fragment);
                    break;
                }
            }
        } else {
            mostrarMensaje("La celda se ha liberado");
            getNavegador().irAtras();
        }
    }

    /**
     * Envía la orden para liberar la celda.
     * @param celda la celda actual
     */
    public final void salirCelda(final TransaccionCelda celda) {
        final Map<String, String> datos = new HashMap<>();
        datos.put("placa", celda.getPlaca());
        subscribe(apiZERService.liberarCelda(zonaPk.getIdPais(), zonaPk.getIdDepartamento(),
                zonaPk.getIdMunicipio(), zonaPk.getId(), idCelda, datos),
                this::procesarRespuestaSalirCelda);
    }

}
