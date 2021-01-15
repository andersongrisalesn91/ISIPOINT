package co.com.mirecarga.core.reportes;

import androidx.annotation.MainThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import co.com.mirecarga.core.R;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.SimpleViewAdapter;
import co.com.mirecarga.core.util.SimpleViewHolder;
import io.reactivex.Observable;

/**
 * Funcionalidad genérica de consulta de reportes.
 * @param <T> el tipo de dato de los registros
 */
public abstract class AbstractReporteFragment<T> extends AbstractAppFragment {
    /**
     * Control de página.
     */
    private transient SearchView filtrar;

    /**
     * Localidad del usuario.
     */
    @Inject
    transient Locale locale;

    /**
     * El adapter con los registros.
     */
    private transient SimpleViewAdapter<T> adapter;

    /**
     * Lista con los datos completos enviados por el servicio.
     */
    private final transient List<T> listaCompleta = new ArrayList<>();

    /**
     * Consulta el modelo específico para el reporte.
     */
    protected abstract void consultarModeloReporte();

    /**
     * Id del layout para los registros.
     * @return id id a utilizar
     */
    protected abstract int getIdLayoutRegistros();

    /**
     * Id del control para filtrar registros.
     * @return id id a utilizar
     */
    protected abstract int getIdFiltrar();

    /**
     * Id del control para mostrar los registros.
     * @return id id a utilizar
     */
    protected abstract int getIdRegistros();

    @Override
    protected final void consultarModelo() {
        final View view = getView();
        assert view != null;
        filtrar = view.findViewById(getIdFiltrar());
        final RecyclerView recyclerView = view.findViewById(getIdRegistros());
        adapter = new SimpleViewAdapter<>(listaCompleta, getIdLayoutRegistros(), this::mostrarItem);
        recyclerView.setAdapter(adapter);
        setupSearchView();
        consultarModeloReporte();
    }

    /**
     * Configura el control de búsqueda.
     */
    private void setupSearchView() {
        filtrar.setIconifiedByDefault(false);
        filtrar.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                mostrarRegistros();
                return true;
            }
        });
        filtrar.setSubmitButtonEnabled(true);
        filtrar.setQueryHint(getString(R.string.buscar_consultas));
    }

    /**
     * Muestra los datos según el criterio de búsqueda, usando la lista actual.
     */
    @MainThread
    protected final void mostrarRegistros() {
        if (TextUtils.isEmpty(filtrar.getQuery())) {
            enlazarLista(listaCompleta);
        } else {
            subscribe(Observable.fromCallable(this::getListaFiltrada),
                    this::enlazarLista);
        }
    }

    /**
     * Muestra los datos según el criterio de búsqueda, estableciendo una nueva lista.
     * @param lista el valor a establecer
     */
    @MainThread
    protected final void mostrarRegistros(final List<T> lista) {
        this.listaCompleta.clear();
        this.listaCompleta.addAll(lista);
        mostrarRegistros();
    }

    /**
     * Enlaza la lista al control que muestra los registros.
     * @param lista los datos a mostrar
     */
    private void enlazarLista(final List<T> lista) {
        adapter.setLista(lista);
    }

    /**
     * Filtro de búsqueda.
     * @return el filtro de búsqueda
     */
    protected final String getFiltro() {
        return filtrar.getQuery().toString();
    }

    /**
     * Filtra los registros en un hilo independiente al principal.
     * @return los registros filtrados
     */
    @MainThread
    protected final List<T> getListaFiltrada() {
        final List<T> listaFiltrada = new ArrayList<>();
        final String filtro = getFiltro().toLowerCase(locale);
        for (final T item : listaCompleta) {
            if (isItemFiltrado(item, filtro)) {
                listaFiltrada.add(item);
            }
        }
        return  listaFiltrada;
    }

    /**
     * Indica si el texto contiene el contenido. Si texto es null retorna false.
     * @param texto el texto a buscar
     * @param contenido el contenido a buscar
     * @return true si el texto contiene el contenido
     */
    protected final boolean contiene(final String texto, final CharSequence contenido) {
        return texto != null && texto.toLowerCase(locale).contains(contenido);
    }

    /**
     * Indica si el item cumple con el criterio de filtrado.
     * @param item el item a evaluar
     * @param filtro el filtro actual en minúscula
     * @return true si el item cumple con el criterio de filtrado
     */
    @MainThread
    protected abstract boolean isItemFiltrado(T item, String filtro);

    /**
     * Muestra el item en la lista.
     * @param holder el viewHolder
     */
    protected abstract void mostrarItem(SimpleViewHolder<T> holder);

    /**
     * Regresa el campo adapter.
     *
     * @return el valor de adapter
     */
    public SimpleViewAdapter<T> getAdapter() {
        return adapter;
    }

    /**
     * Establece el valor del campo adapter.
     *
     * @param adapter el valor a establecer
     */
    public void setAdapter(final SimpleViewAdapter<T> adapter) {
        this.adapter = adapter;
    }
}
