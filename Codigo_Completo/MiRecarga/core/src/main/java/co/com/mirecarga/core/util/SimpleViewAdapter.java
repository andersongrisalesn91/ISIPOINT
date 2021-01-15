package co.com.mirecarga.core.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para callback MenuGrupo en el RecyclerView.
 * @param <T> el tipo de dato de la lista
 */
public class SimpleViewAdapter<T> extends RecyclerView.Adapter<SimpleViewHolder<T>> {
    /**
     * La lista con todos los item a callback.
     */
    private List<T> lista;

    /**
     * El id del recurso para callback los registros.
     */
    private final transient int idRecurso;

    /**
     * La función para callback el registro.
     */
    private final transient Mostrar<T> callback;

    /**
     * Función para obetner el texto.
     * @param <T> el tipo de dato del item
     */
    @FunctionalInterface
    public interface Mostrar<T> {
        /**
         * Función para callback el item.
         * @param viewHolder el elemento a mostrar
         */
        void mostrar(SimpleViewHolder<T> viewHolder);
    }

    /**
     * Constructor con los datos de la lista.
     * @param lista la lista con los item a callback
     * @param idRecurso el id del recurso para callback los registros
     * @param callback la función para callback el registro
     */
    public SimpleViewAdapter(@NonNull final List<T> lista, final int idRecurso,
                             final Mostrar<T> callback) {
        this.lista = lista;
        this.idRecurso = idRecurso;
        this.callback = callback;
    }

    /**
     * Constructor con los datos de la lista.
     * @param idRecurso el id del recurso para callback los registros
     * @param callback la función para callback el registro
     */
    public SimpleViewAdapter(final int idRecurso,
                             final Mostrar<T> callback) {
        this(new ArrayList<>(), idRecurso, callback);
    }

    @Override
    public final SimpleViewHolder<T> onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Para que match_parent funcione dentro del RecyclerView es necesario parent, false
        // https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        final View view = LayoutInflater.from(parent.getContext()).inflate(idRecurso, parent, false);
        return new SimpleViewHolder<>(view);
    }

    @Override
    public final void onBindViewHolder(final SimpleViewHolder<T> holder, final int position) {
        holder.setItem(lista.get(position));
        callback.mostrar(holder);
    }

    @Override
    public final int getItemCount() {
        return lista.size();
    }

    /**
     * Regresa el campo lista.
     *
     * @return el valor de lista
     */
    public final List<T> getLista() {
        return lista;
    }

    /**
     * Establece el valor del campo lista.
     *
     * @param lista el valor a establecer
     */
    public final void setLista(final List<T> lista) {
        this.lista = lista;
        notifyDataSetChanged();
    }
}
