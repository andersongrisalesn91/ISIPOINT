package co.com.mirecarga.cliente.placaadmin;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.api.AgregarPlacaResponse;
import co.com.mirecarga.cliente.api.ApiClienteService;
import co.com.mirecarga.core.api.Placa;
import co.com.mirecarga.core.reportes.AbstractReporteFragment;
import co.com.mirecarga.core.util.SimpleViewHolder;
import co.com.mirecarga.core.util.Validador;

/**
 * Fragmento con los datos de la página de administración de placas cliente.
 */
public class PlacaAdminFragment extends AbstractReporteFragment<Placa> {

    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiClienteService api;

    /**
     * Control de página.
     */
    @BindView(R.id.agregar_placa)
    transient ImageView agregarPlaca;

    /**
     * Control de página.
     */
    @BindView(R.id.editar_placa)
    transient ImageView editarPlaca;

    /**
     * Control de página.
     */
    @BindView(R.id.cancelar_placa)
    transient ImageView cancelarPlaca;

    /**
     * Control de página.
     */
    @BindView(R.id.ingresar_placa)
    transient EditText ingresarPlaca;
    /**
     * Servicio con la lógica de negocio.
     */
    @Inject
    transient PlacaAdminService service;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_placa_admin;
    }

    @Override
    protected int getIdLayoutRegistros() {
        return R.layout.adapter_placa_admin;
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
    protected final void consultarModeloReporte() {

        mostrarControlesEditar(service.isEditando());
        buscar();
    }

    /**
     * Actualiza la lista de placas.
     */
    private void buscar() {
            final Integer idCliente = getIdUsuario();
            subscribe(api.getPlacas(idCliente),
                    this::mostrarRegistros);
    }

    @Override
    protected boolean isItemFiltrado(final Placa item, final String filtro) {
        return contiene(item.getCodigo(), filtro);
    }

    @Override
    protected void mostrarItem(final SimpleViewHolder<Placa> holder) {
        final Placa item = holder.getItem();
        holder.setText(R.id.placa, item.getCodigo());
        final ImageView editar = holder.itemView.findViewById(R.id.editar);
        editar.setOnClickListener(v -> {
            mostrarControlesEditar(true);
            ingresarPlaca.setText(item.getCodigo());
            service.setIdPlacaEditar(item.getId());
        });
        final ImageView eliminar = holder.itemView.findViewById(R.id.eliminar);
        eliminar.setOnClickListener(v -> eliminarPlaca(item.getId()));
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.cancelar_placa)
    public void cancelar() {
        mostrarControlesEditar(false);
        ingresarPlaca.setText(null);
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.editar_placa)
    public void editarPlacaClick() {
        final Validador validador = new Validador();
        validador.requerido(ingresarPlaca);
        if (isSesionActiva()
                && validador.isValido()) {
            final Integer idCliente = getIdUsuario();
            final String placa = ingresarPlaca.getText().toString();
            subscribe(api.editarPlaca(idCliente, service.getIdPlacaEditar(), placa), this::editarRespuesta);
        }
    }

    /**
     * Procesa la respuesta de editar placa.
     * @param resp la respuesta del servicio
     */
    private void editarRespuesta(final Boolean resp) {
        if (resp) {
            cancelar();
            buscar();
            mostrarMensaje(getString(R.string.msg_editar_placa_ok));
        } else {
            mostrarMensaje(getString(R.string.msg_editar_placa_error));
        }
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.agregar_placa)
    public void agregarPlacaClick() {
        final Validador validador = new Validador();
        validador.requerido(ingresarPlaca);
        if (isSesionActiva()
                && validador.isValido()) {
            final Integer idCliente = getIdUsuario();
            final String placa = ingresarPlaca.getText().toString();
            subscribe(api.agregarPlaca(idCliente, placa), this::agregarPlacaRespuesta);
        }
    }

    /**
     * Procesa la respuesta de agregar placa.
     * @param resp la respuesta del servicio
     */
    private void agregarPlacaRespuesta(final AgregarPlacaResponse resp) {
        if (TextUtils.isEmpty(resp.getError())) {
            cancelar();
            buscar();
            mostrarMensaje(getString(R.string.msg_agregar_placa_ok));
        } else {
            mostrarMensaje(resp.getError());
        }
    }

    /**
     * Elimina placa.
     * @param idPlaca el identificador de la placa.
     */
    public void eliminarPlaca(final int idPlaca) {
            final Integer idCliente = getIdUsuario();
            subscribe(api.eliminarPlaca(idCliente, idPlaca), this::eliminarPlacaRespuesta);
    }

    /**
     * Procesa la respuesta de eliminar placa.
     * @param resp la respuesta del servicio
     */
    private void eliminarPlacaRespuesta(final Boolean resp) {
        if (resp) {
            buscar();
            mostrarMensaje(getString(R.string.msg_eliminar_placa_ok));
        } else {
            mostrarMensaje(getString(R.string.msg_eliminar_placa_error));
        }
    }

    /**
     * Muestra u oculta los controles de edición.
     * @param mostrar el estado de mostrar
     */
    private void mostrarControlesEditar(final boolean mostrar) {
        if (mostrar) {
            agregarPlaca.setVisibility(View.GONE);
            editarPlaca.setVisibility(View.VISIBLE);
            cancelarPlaca.setVisibility(View.VISIBLE);
        } else {
            agregarPlaca.setVisibility(View.VISIBLE);
            editarPlaca.setVisibility(View.GONE);
            cancelarPlaca.setVisibility(View.GONE);
        }
        service.setEditando(mostrar);
    }
}
