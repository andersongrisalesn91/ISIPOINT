package co.com.mirecarga.vendedor.ventamapa;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.reportes.AbstractReporteFragment;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.SimpleViewHolder;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiZERService;
import co.com.mirecarga.vendedor.app.ConfigVendedor;
import co.com.mirecarga.core.mapa.ZonaMapa;

/**
 * Fragmento con los datos de la página de histórico de ventas para el mapa.
 */
public class HistoricoVentaFragment extends AbstractReporteFragment<TransaccionCelda> {
    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient VentaMapaService service;
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
     * Indica que se debe mostrar el botón de salida.
     */
    private transient boolean mostrarBotonSalir;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_historico_venta;
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_historico_venta;
    }

    @Override
    protected int getIdFiltrar() {
        return R.id.filtrar;
    }

    @Override
    protected int getIdRegistros() {
        return R.id.registros;
    }

    @Override
    protected final boolean isItemFiltrado(final TransaccionCelda item, final String filtro) {
        return contiene(item.getCliente(), filtro) || contiene(item.getPlaca(), filtro);
    }

    @Override
    protected final void consultarModeloReporte() {
        mostrarBotonSalir = service.isFiltrarPendientes();
        final TextView titulo = getView().findViewById(R.id.titulo_busqueda);
        if (mostrarBotonSalir) {
            titulo.setText(getString(R.string.salida_de_vehiculo));
        } else {
            titulo.setText(getString(R.string.historial));
        }
        if (isSesionActiva()) {
            final ZonaMapa zonaMapa = service.getZonaSeleccionada();
            final int idCelda = service.getIdCeldaSeleccionada();
            final Map<String, String> datos = new HashMap<>();
            datos.put("pendientes", String.valueOf(mostrarBotonSalir));
            subscribe(apiZERService.historicoCelda(zonaMapa.getIdPais(),
                    zonaMapa.getIdDepartamento(), zonaMapa.getIdMunicipio(), zonaMapa.getId(),
                    idCelda, datos), this::mostrarRegistros);
        }
    }

    @Override
    protected void mostrarItem(final SimpleViewHolder<TransaccionCelda> holder) {
        final TransaccionCelda celda = holder.getItem();
        holder.setText(R.id.bubble_title, getString(R.string.celda_ocupada_celda, String.valueOf(celda.getId())));
        holder.setText(R.id.hora_inicio, getString(R.string.celda_ocupada_hora_inicio, format.formatearFechaHora(celda.getFechahoraentrada())));
        holder.setText(R.id.hora_vencimiento, getString(R.string.celda_ocupada_hora_vencimiento,
                format.formatearFechaHora(celda.getFechahoravigencia())));
        holder.setText(R.id.cliente, getString(R.string.celda_ocupada_cliente, celda.getCliente()));
        holder.setText(R.id.placa, getString(R.string.celda_ocupada_placa, celda.getPlaca()));
        holder.setText(R.id.tiempo_vencerse, getString(R.string.celda_ocupada_tiempo_vencerese, String.valueOf(celda.getHms())));

        final ZonaMapa zonaMapa = service.getZonaSeleccionada();
        final ImageView imprimir = holder.itemView.findViewById(R.id.imprimir);
        imprimir.setOnClickListener(v -> service.imprimirCelda(zonaMapa, celda, this));

        final ImageView salir = holder.itemView.findViewById(R.id.salir);
        salir.setOnClickListener(v -> {
            final int idCelda = service.getIdCeldaSeleccionada();
            final Map<String, String> datos = new HashMap<>();
            datos.put("placa", celda.getPlaca());
            subscribe(apiZERService.liberarCelda(zonaMapa.getIdPais(),
                    zonaMapa.getIdDepartamento(), zonaMapa.getIdMunicipio(), zonaMapa.getId(),
                    idCelda, datos), resp -> {
                mostrarMensaje("La celda se ha liberado");
                getNavegador().irAtras();
            });
        });
        if (!mostrarBotonSalir) {
            salir.setVisibility(View.GONE);
        }
    }
}
