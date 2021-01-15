package co.com.mirecarga.cliente.reportes;

import javax.inject.Inject;

import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.TransaccionesRealizadasResponse;
import co.com.mirecarga.cliente.app.ConfigCliente;
import co.com.mirecarga.core.util.ConfigService;
import co.com.mirecarga.core.util.SimpleViewHolder;

/**
 * Fragmento con los datos de la página de reporte transacciones realizadas.
 */
public class TransaccionesRealizadasFragment extends AbstractReportesClienteFragment<TransaccionesRealizadasResponse> {
    /**
     * Servicio de configuración.
     */
    @Inject
    transient ConfigService<ConfigCliente> configService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_transacciones_realizadas;
    }

    @Override
    protected void consultarModeloReporteFechas() {
        if (isSesionActiva()) {
            buscar();
        }
    }

    /**
     * Eventos para actualizar registros.
     */
    @OnClick(R.id.buscar)
    public void buscar() {
        final Integer idCliente = getIdUsuario();
        if (isSesionActiva()) {
            subscribe(api.transaccionesRealizadas(idCliente,
                    getFechaInicial().getTexto(), getFechaFinal().getTexto()),
                    this::mostrarRegistros);
        }
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_transacciones_realizadas;
    }

    @Override
    protected final boolean isItemFiltrado(final TransaccionesRealizadasResponse item, final String filtro) {
        return contiene(item.getOperacion(), filtro)
                || contiene(item.getMensaje(), filtro)
                || contiene(item.getUsuarioCreacion(), filtro)
                || contiene(item.getOrigen(), filtro)
                || contiene(item.getDato1(), filtro)
                || contiene(item.getDato2(), filtro);
    }

    @Override
    protected final void mostrarItem(final SimpleViewHolder<TransaccionesRealizadasResponse> holder) {
        final TransaccionesRealizadasResponse item = holder.getItem();
        holder.setText(R.id.operacion, item.getOperacion());
        holder.setText(R.id.mensaje, item.getMensaje());
        holder.setText(R.id.usuarioCreacion, item.getUsuarioCreacion());
        holder.setText(R.id.origen, item.getOrigen());
        holder.setText(R.id.dato1, item.getDato1());
        holder.setText(R.id.dato2, item.getDato2());
        holder.setText(R.id.fechaCreacion, format.formatearFechaHora(item.getFechaCreacion()));

    }
}
