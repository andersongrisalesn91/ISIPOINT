package co.com.mirecarga.vendedor.mapa;

import android.text.TextUtils;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.com.mirecarga.core.api.EstadoCelda;
import co.com.mirecarga.core.api.TipoVehiculo;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.ListaSeleccion;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiZERService;

/**
 * Fragmento con los filtros aplicados al mapa.
 */
public class FiltroMapaFragment extends AbstractAppFragment {
    /**
     * Control de página.
     */
    @BindView(R.id.estado)
    transient Spinner estado;

    /**
     * Control de página.
     */
    @BindView(R.id.tipo_vehiculo)
    transient Spinner tipoVehiculo;

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
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<EstadoCelda> listaEstado;

    /**
     * Lista mostrada en el spinner.
     */
    private transient ListaSeleccion<TipoVehiculo> listaTipoVehiculo;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_filtro_mapa;
    }

    @Override
    protected final void consultarModelo() {
        final FiltroMapa filtroMapa = mapaVendedorServiceService.getFiltroMapa();
        if (filtroMapa.getCacheEstado() == null) {
            subscribe(apiZERService.getEstadosCelda(), this::cargarListaEstado);
        } else {
            cargarListaEstado(filtroMapa.getCacheEstado());
        }

        if (filtroMapa.getCacheTipoVehiculo() == null) {
            subscribe(apiZERService.getTiposCelda(), this::cargarListaTipoVehiculo);
        } else {
            cargarListaTipoVehiculo(filtroMapa.getCacheTipoVehiculo());
        }
    }

    /**
     * Carga el combo a partir de la lista.
     * @param list los datos del caché
     */
    private void cargarListaEstado(final List<EstadoCelda> list) {
        final FiltroMapa filtroMapa = mapaVendedorServiceService.getFiltroMapa();
        if (filtroMapa.getCacheEstado() == null) {
            filtroMapa.setCacheEstado(list);
        }
        listaEstado = ListaSeleccion.iniciar(estado, list, getString(R.string.todos_estados), EstadoCelda::getEstado);
        final EstadoCelda estadoCeldaFiltrar = filtroMapa.getEstadoCeldaFiltrar();
        if (estadoCeldaFiltrar != null) {
            listaEstado.setTextoSeleccionado(estado, estadoCeldaFiltrar.getEstado());
        }
    }

    /**
     * Carga el combo a partir de la lista.
     * @param list los datos del caché
     */
    private void cargarListaTipoVehiculo(final List<TipoVehiculo> list) {
        final FiltroMapa filtroMapa = mapaVendedorServiceService.getFiltroMapa();
        if (filtroMapa.getCacheTipoVehiculo() == null) {
            filtroMapa.setCacheTipoVehiculo(list);
        }
        listaTipoVehiculo = ListaSeleccion.iniciar(tipoVehiculo, list, getString(R.string.todos_tipos_celda), TipoVehiculo::getTipovehiculo);
        final TipoVehiculo tipoVehiculoFiltrar = filtroMapa.getTipoVehiculoFiltrar();
        if (tipoVehiculoFiltrar != null) {
            listaTipoVehiculo.setTextoSeleccionado(tipoVehiculo, tipoVehiculoFiltrar.getTipovehiculo());
        }
    }

    /**
     * Construye el filtro para las celdas del mapa.
     */
    private void calcularFiltroCql() {
        final FiltroMapa filtroMapa = mapaVendedorServiceService.getFiltroMapa();
        final List<String> filtros = new ArrayList<>(2);
        if (filtroMapa.getEstadoCeldaFiltrar() != null) {
            filtros.add("id_estado=" + filtroMapa.getEstadoCeldaFiltrar().getId());
        }
        if (filtroMapa.getTipoVehiculoFiltrar() != null) {
            filtros.add("id_tipo_celda=" + filtroMapa.getTipoVehiculoFiltrar().getId());
        }
        final String filtro;
        if (filtros.isEmpty()) {
            filtro = null;
        } else {
            filtro = TextUtils.join(" and ", filtros);
        }
        filtroMapa.setFiltroCql(filtro);
    }

    /**
     * Captura el evento del botón.
     */
    @OnClick(R.id.filtrar)
    public void filtrar() {
        final FiltroMapa filtroMapa = mapaVendedorServiceService.getFiltroMapa();
        final EstadoCelda estadoCeldaFiltrar = listaEstado.getItem(estado);
        filtroMapa.setEstadoCeldaFiltrar(estadoCeldaFiltrar);
        final TipoVehiculo tipoVehiculoFiltrar = listaTipoVehiculo.getItem(this.tipoVehiculo);
        filtroMapa.setTipoVehiculoFiltrar(tipoVehiculoFiltrar);
        calcularFiltroCql();
        getNavegador().irAtras();
    }

}
