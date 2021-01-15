package co.com.mirecarga.vendedor.reportes;

import butterknife.OnClick;
import co.com.mirecarga.core.util.SimpleViewHolder;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ReporteItem;

/**
 * Fragmento con los datos de la página de reporte transacciones de un PIN.
 */
public class TransaccionesPinFragment extends AbstractReportesVendedorFragment<ReporteItem> {
    @Override
    protected int getIdLayout() {
        return R.layout.fragment_transacciones_pin;
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_transacciones_pin;
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
        if (isSesionActiva()) {
            subscribe(api.transaccionesPin(idVendedor,
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
        holder.setText(R.id.productoNombre, getString(R.string.pin_nombre_producto,
                item.getProductoNombre()));
        holder.setText(R.id.valorSaldoBalance, getString(R.string.pin_saldo_balance,
                format.formatearDecimal(item.getValorSaldoBalance())));
        holder.setText(R.id.valorNuevoSaldoBalance, getString(R.string.pin_nombre_producto,
                format.formatearDecimal(item.getValorNuevoSaldoBalance())));
        holder.setText(R.id.estado, getString(R.string.pin_estado, item.getEstado()));
        holder.setText(R.id.fechaCreacion, getString(R.string.pin_fecha_creacion,
                format.formatearFechaHora(item.getFechaCreacion())));
        holder.setText(R.id.establecimientoNombre, getString(R.string.pin_establecimiento,
                item.getEstablecimientoNombre()));
    }
}
