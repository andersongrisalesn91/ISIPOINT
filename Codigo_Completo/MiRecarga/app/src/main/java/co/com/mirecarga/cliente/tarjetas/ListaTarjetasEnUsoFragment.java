package co.com.mirecarga.cliente.tarjetas;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.cliente.reportes.TransaccionesPorPinFragment;
import co.com.mirecarga.cliente.reportes.TransaccionesPorPinService;
import co.com.mirecarga.cliente.venta.FinalizarUsoPinFragment;
import co.com.mirecarga.cliente.venta.FinalizarUsoPinService;
import co.com.mirecarga.core.api.Tarjeta;
import co.com.mirecarga.core.reportes.AbstractReporteFragment;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.SimpleViewHolder;

/**
 * Fragmento con los datos de la página de lista de tarjetas.
 */
public class ListaTarjetasEnUsoFragment extends AbstractReporteFragment<Tarjeta> {
    /**
     * Manejador de formato de la aplicación.
     */
    @Inject
    protected transient AppFormatterService format;
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
     * Servicio con la lógica de negocio de inicio uso PIN.
     */
    @Inject
    transient TransaccionesPorPinService transaccionesPorPinService;

    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient FinalizarUsoPinService finalizarUsoPinService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_lista_tarjetas_en_uso;
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_tarjetas_en_uso;
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
    protected boolean isItemFiltrado(final Tarjeta item, final String filtro) {
        return false;
    }

    @Override
    protected final void consultarModeloReporte() {
        if (isSesionActiva()) {
            buscar();
        }
    }

    /**
     * Actualiza la lista de tarjetas.
     */
    private void buscar() {
        final Integer idCliente = getIdUsuario();
        subscribe(api.getTarjetas(idCliente),
                this::filtraLista);
    }

    /**
     * Filtra los registros que no están en uso.
     * @param lista la lista
     */
    private void filtraLista(final List<Tarjeta> lista) {
        final List<Tarjeta> listaFiltrada = new ArrayList<>();
        final String estadoEnUso = configService.getConfig().getEstadoTarjetaEnUso();
        for (final Tarjeta item : lista) {
            if (item.getEstado().equals(estadoEnUso)) {
                listaFiltrada.add(item);
            }
        }
        mostrarRegistros(listaFiltrada);
    }

    @Override
    protected void mostrarItem(final SimpleViewHolder<Tarjeta> holder) {

        final Tarjeta item = holder.getItem();
        holder.setText(R.id.codigo, item.getCodigo());
        holder.setText(R.id.valorSaldo, format.formatearDecimal(item.getValorSaldo()));
        final ImageView transaccionesTarjeta = holder.itemView.findViewById(R.id.transaccionesTarjeta);
        transaccionesTarjeta.setOnClickListener(v -> {
            transaccionesPorPinService.setMetodoPago(item.getCodigo());
            getNavegador().navegar(new TransaccionesPorPinFragment());
        });

        final ImageView pausaPin = holder.itemView.findViewById(R.id.pausaPin);
        pausaPin.setOnClickListener(v -> {
            finalizarUsoPinService.setCodigoMetodoPago(item.getCodigo());
            getNavegador().navegar(new FinalizarUsoPinFragment());
        });

    }
}
