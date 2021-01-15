package co.com.mirecarga.cliente.reportes;

import javax.inject.Inject;

import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.ReporteItem;
import co.com.mirecarga.core.util.SimpleViewHolder;

/**
 * Fragmento con los datos de la página de reporte transacciones de un PIN.
 */
public class TransaccionesPorPinFragment extends AbstractReportesClienteFragment<ReporteItem> {
    /**
     * Servicio con la lógica de negocio de inicio uso PIN.
     */
    @Inject
    transient TransaccionesPorPinService service;
    @Override
    protected int getIdLayout() {
        return R.layout.fragment_transacciones_por_pin;
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_transacciones_por_pin;
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
        final Integer idCliente = getIdUsuario();
        if (isSesionActiva()) {
            final String metodoPago = service.getMetodoPago();
            setTitulo(getString(R.string.titulo_transacciones_por_pin, metodoPago));
            subscribe(api.transaccionesPin(idCliente, metodoPago,
                    getFechaInicial().getTexto(), getFechaFinal().getTexto()),
                    this::mostrarRegistros);
        }
    }

    @Override
    protected final boolean isItemFiltrado(final ReporteItem item, final String filtro) {
        return contiene(item.getEntidadNombre(), filtro)
                || contiene(item.getProductoNombre(), filtro)
                || contiene(item.getEstado(), filtro)
                || contiene(item.getEstablecimientoNombre(), filtro);
    }

    @Override
    protected final void mostrarItem(final SimpleViewHolder<ReporteItem> holder) {
        final ReporteItem item = holder.getItem();
        holder.setText(R.id.entidadNombre, item.getEntidadNombre());
        holder.setText(R.id.valorSaldoBalance, format.formatearDecimal(item.getValorSaldoBalance()));
        holder.setText(R.id.valorNuevoSaldoBalance, format.formatearDecimal(item.getValorNuevoSaldoBalance()));
        holder.setText(R.id.estado, item.getEstado());
        holder.setText(R.id.fecha, format.formatearFechaHora(item.getFechaCreacion()));
        holder.setText(R.id.establecimiento, item.getEstablecimientoNombre());
        holder.setText(R.id.productoNombre, item.getProductoNombre());
    }
}
