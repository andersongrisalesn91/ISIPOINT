package co.com.mirecarga.core.util;

import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para facilitar el manejo de listas de selección (Spinner).
 * @param <T> el tipo de dato genérico
 */
public class ListaSeleccion<T> {
    /**
     * Los items originales.
     */
    private transient List<T> items;

    /**
     * Adapter utilizado para los textos.
     */
    private transient ArrayAdapter<String> adapter;

    /**
     * Indica si el primer item es texto de seleccione.
     */
    private transient boolean tieneSeleccione;

    /**
     * Regresa el campo items.
     *
     * @return el valor de items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * El item de la lista.
     * @param control el control de lista enlazado
     * @return el item de la lista
     */
    public final T getItem(final Spinner control) {
        final int pos = control.getSelectedItemPosition();
        final T item;
        if (tieneSeleccione) {
            if (pos == 0) {
                item = null;
            } else {
                item = items.get(pos - 1);
            }
        } else {
            item = items.get(pos);
        }
        return item;
    }

    /**
     * Indica si hay algún item seleccionado.
     * @param control el control de lista enlazado
     * @return true si hay algún item seleccionado
     */
    public final boolean isItemSeleccionado(final Spinner control) {
        return control.getSelectedItemPosition() > 0;
    }

    /**
     * Establece el item seleccionado para el control a partir del texto mostrado.
     * @param control el control de lista enlazado
     * @param texto el texto a seleccionar
     */
    public final void setTextoSeleccionado(final Spinner control, final String texto) {
        if (TextUtils.isEmpty(texto)) {
            control.setSelection(0);
        } else {
            control.setSelection(adapter.getPosition(texto));
        }
    }

    /**
     * Indica si la lista contiene el texto.
     * @param texto el texto a seleccionar
     * @return true si contiene el texto
     */
    public final boolean contiene(final String texto) {
        return adapter.getPosition(texto) >= 0;
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
     * Genera la lista para mostrar en el spinner y la asocia al control.
     * @param control el control de lista enlazado
     * @param items la lista original
     * @param getter el lambda para obtener el texto
     * @param <T> el tipo de dato
     * @return la lista para facilitar el uso del spinner
     */
    public static <T> ListaSeleccion<T> iniciar(final Spinner control, final List<T> items, final TextGetter<T> getter) {
        return iniciar(control, items, null, getter);
    }

    /**
     * Genera la lista para mostrar en el spinner y la asocia al control.
     * @param control el control de lista enlazado
     * @param items la lista original
     * @param seleccione el texto a mostrar para seleccionar
     * @param getter el lambda para obtener el texto
     * @param <T> el tipo de dato
     * @return la lista para facilitar el uso del spinner
     */
    public static <T> ListaSeleccion<T> iniciar(final Spinner control, final List<T> items, final String seleccione, final TextGetter<T> getter) {
        final List<T> itemsLocal;
        if (items == null) {
            itemsLocal = new ArrayList<>();
        } else {
            itemsLocal = items;
        }
        // Genera la lista de textos a mostrar
        final List<String> textos = new ArrayList<>(itemsLocal.size());
        final boolean tieneSeleccione = seleccione != null;
        if (tieneSeleccione) {
            textos.add(seleccione);
        }
        for (final T item : itemsLocal) {
            textos.add(getter.getText(item));
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(control.getContext(), android.R.layout.simple_spinner_item, textos);
        control.setAdapter(adapter);
        final ListaSeleccion<T> resp = new ListaSeleccion<>();
        resp.items = itemsLocal;
        resp.adapter = adapter;
        resp.tieneSeleccione = tieneSeleccione;
        return resp;
    }

}
