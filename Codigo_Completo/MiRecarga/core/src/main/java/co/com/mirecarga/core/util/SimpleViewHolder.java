package co.com.mirecarga.core.util;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * ViewHolder genérico para consultas.
 * @param <T> el tipo de dato de la lista
 */
public class SimpleViewHolder<T> extends RecyclerView.ViewHolder {
    /**
     * El item asociado.
     */
    private T item;

    /**
     * Constructor con las propiedades.
     * @param view la vista
     */
    public SimpleViewHolder(final View view) {
        super(view);
    }

    /**
     * Establece el valor del control.
     * @param idRecurso el id del recurso
     * @param texto el texto a mostrar
     */
    public final void setText(final int idRecurso, final String texto) {
        final TextView textView = itemView.findViewById(idRecurso);
        textView.setText(texto);
    }

    /**
     * Regresa el campo item.
     *
     * @return el valor de item
     */
    public T getItem() {
        return item;
    }

    /**
     * Establece el valor del campo item.
     *
     * @param item el valor a establecer
     */
    public void setItem(final T item) {
        this.item = item;
    }
}
