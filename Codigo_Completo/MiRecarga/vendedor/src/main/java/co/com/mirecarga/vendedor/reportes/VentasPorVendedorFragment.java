package co.com.mirecarga.vendedor.reportes;

import androidx.annotation.MainThread;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import co.com.mirecarga.core.api.IdNombre;
import co.com.mirecarga.core.api.Producto;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.core.util.SimpleViewHolder;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ReporteItem;
import co.com.mirecarga.vendedor.app.ConfigVendedor;

/**
 * Fragmento con los datos de la página de reporte ventas por vendedor.
 */
public class VentasPorVendedorFragment extends AbstractReportesVendedorFragment<VentasPorVendedorItem> {
    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<Producto> listaProducto;

    /**
     * Control de página.
     */
    @BindView(R.id.productos)
    transient Spinner productos;

    /**
     * Id del producto.
     */
    private transient int idRecargaEnLinea;

    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigVendedor> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_ventas_por_vendedor;
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_ventas_por_vendedor;
    }

    @Override
    protected void consultarModeloReporteFechas() {
        idRecargaEnLinea = configService.getConfig().getProductos().getIdRecargaEnLinea();
        if (isSesionActiva()) {
            subscribe(api.getProductos(getIdUsuario()),
                    list -> listaProducto = ListaSeleccion.iniciar(productos, list,
                            IdNombre::getNombre));
        }
    }

    /**
     * Eventos para actualizar registros.
     */
    @OnClick(R.id.buscar)
    @OnItemSelected(R.id.productos)
    public void buscar() {
        final Integer idVendedor = getIdUsuario();
        final int idProducto = listaProducto.getItem(productos).getId();
        if (isSesionActiva()) {
            subscribe(api.ventasVendedor(idVendedor, idProducto,
                    getFechaInicial().getTexto(), getFechaFinal().getTexto()),
                    this::actualizarRegistros);
        }
    }

    /**
     * Muestra los registros de la lista.
     * @param lista la lista con los registros
     */
    @MainThread
    private void actualizarRegistros(final List<ReporteItem> lista) {
        final List<VentasPorVendedorItem> items = new ArrayList<>(lista.size());
        items.clear();
        for (final ReporteItem reg : lista) {
            items.add(getVentasPorVendedorItem(reg));
        }
        mostrarRegistros(items);
    }

    /**
     * Convierte el registro del servicio al item a mostrar.
     * @param reg el registro del servicio
     * @return el item a mostrar
     */
    @MainThread
    private VentasPorVendedorItem getVentasPorVendedorItem(final ReporteItem reg) {
        final VentasPorVendedorItem item = new VentasPorVendedorItem();
        if (reg.getIdProducto() == idRecargaEnLinea) {
            item.setNombre(reg.getKeyOne());
            item.setCodigo(reg.getKeyTwo());
            item.setFecha(format.formatearDecimal(reg.getValorTotal()));
        } else {
            item.setNombre(reg.getClienteNombre());
            item.setCodigo(reg.getKeyTwo());
            item.setFecha(format.formatearFechaHora(reg.getFechaCreacion()));
        }
        return item;
    }

    @Override
    protected final boolean isItemFiltrado(final VentasPorVendedorItem item, final String filtro) {
        return contiene(item.getNombre(), filtro) || contiene(item.getCodigo(), filtro);
    }

    @Override
    protected final void mostrarItem(final SimpleViewHolder<VentasPorVendedorItem> holder) {
        final VentasPorVendedorItem item = holder.getItem();
        holder.setText(R.id.nombre, item.getNombre());
        holder.setText(R.id.placa, item.getCodigo());
        holder.setText(R.id.fecha, item.getFecha());
    }
}
