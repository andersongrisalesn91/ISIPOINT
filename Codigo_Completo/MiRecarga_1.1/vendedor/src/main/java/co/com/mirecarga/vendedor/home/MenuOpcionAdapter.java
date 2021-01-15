package co.com.mirecarga.vendedor.home;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.com.mirecarga.core.home.MenuOpcion;
import co.com.mirecarga.vendedor.R;

/**
 * Adaptador para mostrar MenuOpcion en el RecyclerView.
 */
public class MenuOpcionAdapter extends RecyclerView.Adapter<MenuOpcionViewHolder> {
    /**
     * La lista con los item a mostrar.
     */
    private final transient List<MenuOpcion> lista;

    /**
     * Constructor con los datos de la lista.
     * @param lista la lista con los item a mostrar
     */
    public MenuOpcionAdapter(@NonNull  final List<MenuOpcion> lista) {
        this.lista = lista;
    }

    @Override
    public final MenuOpcionViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Normalmente en root se debe enviar parent pero al usar doble RecyclerView en menú, genera error
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_opcion, null);
        return new MenuOpcionViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final MenuOpcionViewHolder holder, final int position) {
        holder.mostrar(lista.get(position));
    }

    @Override
    public final int getItemCount() {
        return lista.size();
    }
}
