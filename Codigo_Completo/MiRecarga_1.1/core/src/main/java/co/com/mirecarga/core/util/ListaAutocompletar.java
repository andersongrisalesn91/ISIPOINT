package co.com.mirecarga.core.util;

import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para facilitar el manejo de listas de autocompletar.
 * @param <T> el tipo de dato genérico
 */
public class ListaAutocompletar<T> {
    /**
     * Los items originales.
     */
    private transient List<T> items;

    /**
     * Adapter utilizado para los textos.
     */
    private transient List<String> textos;

    /**
     * Indica si el primer item es texto de seleccione.
     */
    private transient boolean tieneSeleccione;

    /**
     * El item de la lista.
     * @param control el control de lista enlazado
     * @return el item de la lista
     */
    public final T getItem(final AutoCompleteTextView control) {
        return getItem(control.getText().toString());
    }

    /**
     * El item de la lista.
     * @param texto el texto mostrado
     * @return el item de la lista
     */
    public final T getItem(final String texto) {
        final T item;
        int index = textos.indexOf(texto);
        if (tieneSeleccione) {
            index--;
        }
        if (index >= 0) {
            item = items.get(index);
        } else {
            item = null;
        }
        return item;
    }

    /**
     * Indica si hay algún item seleccionado.
     * @param control el control de lista enlazado
     * @return true si hay algún item seleccionado
     */
    public final boolean isItemSeleccionado(final AutoCompleteTextView control) {
        return getItem(control) != null;
    }

    /**
     * Establece el item seleccionado para el control a partir del texto mostrado.
     * @param control el control de lista enlazado
     * @param texto el texto a seleccionar
     */
    public final void setTextoSeleccionado(final AutoCompleteTextView control, final String texto) {
        if (TextUtils.isEmpty(texto)) {
            control.setText(null);
        } else {
            control.setText(texto);
        }
    }

    /**
     * Función para obetner el texto.
     * @param <T> el tipo de dato original
     */
    @FunctionalInterface
    public interface TextGetter<T> {
        /**
         * Función para obetner el texto.
         * @param item el tipo de dato original
         * @return el texto a usar
         */
        String getText(T item);
    }

    /**
     * Genera la lista para mostrar en el autoCompleteTextView y la asocia al control.
     * @param control el control de lista enlazado
     * @param items la lista original
     * @param getter el lambda para obtener el texto
     * @param <T> el tipo de dato
     * @return la lista para facilitar el uso del autoCompleteTextView
     */
    public static <T> ListaAutocompletar<T> iniciar(final AutoCompleteTextView control, final List<T> items, final TextGetter<T> getter) {
        return iniciar(control, items, null, getter);
    }

    /**
     * Genera la lista para mostrar en el autoCompleteTextView y la asocia al control.
     * @param control el control de lista enlazado
     * @param items la lista original
     * @param seleccione el texto a mostrar para seleccionar
     * @param getter el lambda para obtener el texto
     * @param <T> el tipo de dato
     * @return la lista para facilitar el uso del autoCompleteTextView
     */
    public static <T> ListaAutocompletar<T> iniciar(final AutoCompleteTextView control,
                                                    final List<T> items,
                                                    final String seleccione,
                                                    final TextGetter<T> getter) {
        // Genera la lista de textos a mostrar
        final List<String> textos = new ArrayList<>(items.size());
        final boolean tieneSeleccione = seleccione != null;
        if (tieneSeleccione) {
            textos.add(seleccione);
        }
        for (final T item : items) {
            textos.add(getter.getText(item));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(control.getContext(), android.R.layout.simple_list_item_1, textos);
        control.setAdapter(adapter);
        final ListaAutocompletar<T> resp = new ListaAutocompletar<>();
        resp.items = items;
        resp.textos = textos;
        resp.tieneSeleccione = tieneSeleccione;
        return resp;
    }

}
