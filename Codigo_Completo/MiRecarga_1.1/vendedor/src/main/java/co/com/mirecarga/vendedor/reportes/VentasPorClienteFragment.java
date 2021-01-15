package co.com.mirecarga.vendedor.reportes;

import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.OnClick;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.SimpleViewHolder;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ReporteItem;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Fragmento con los datos de la página de reporte ventas por cliente.
 */
public class VentasPorClienteFragment extends AbstractReportesVendedorFragment<ReporteItem> {
    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_transacciones_pin;
    }

    @Override
    protected void consultarModeloReporteFechas() {
        buscar();
    }

    /**
     * Eventos para actualizar registros.
     */
    @OnClick(R.id.buscar)
    public void buscar() {
        final Integer idVendedor = getIdUsuario();
        final int idPinTransito = configService.getConfig().getProductos().getIdPinTransito();
        if (isSesionActiva()) {
            subscribe(api.ventasCliente(idVendedor, idPinTransito,
                    getFechaInicial().getTexto(), getFechaFinal().getTexto()),
                    this::mostrarRegistros);
        }
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_ventas_por_cliente;
    }

    @Override
    protected final boolean isItemFiltrado(final ReporteItem item, final String filtro) {
        return contiene(item.getClienteNombre(), filtro)
                || contiene(item.getKeyTwo(), filtro);
    }

    @Override
    protected final void mostrarItem(final SimpleViewHolder<ReporteItem> holder) {
        final ReporteItem item = holder.getItem();
        holder.setText(R.id.nombre, item.getClienteNombre());
        holder.setText(R.id.placa, item.getKeyTwo());
        holder.setText(R.id.fecha, format.formatearFechaHora(item.getFechaCreacion()));
        final ImageView detalle = holder.itemView.findViewById(R.id.detalle);
        detalle.setOnClickListener(v -> {
            final VerDetalleFragment verDetalleFragment = new VerDetalleFragment();
            verDetalleFragment.setItem(item);
            getNavegador().navegar(verDetalleFragment);
        });
    }
}
