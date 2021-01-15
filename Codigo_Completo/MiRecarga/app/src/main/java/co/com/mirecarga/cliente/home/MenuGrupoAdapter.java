package co.com.mirecarga.cliente.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.com.mirecarga.cliente.R;
import co.com.mirecarga.core.home.MenuGrupo;

/**
 * Adaptador para mostrar MenuGrupo en el RecyclerView.
 */
public class MenuGrupoAdapter extends RecyclerView.Adapter<MenuGrupoViewHolder> {
    /**
     * La lista con los item a mostrar.
     */
    private final transient List<MenuGrupo> lista;

    /**
     * Constructor con los datos de la lista.
     * @param lista la lista con los item a mostrar
     */
    public MenuGrupoAdapter(@NonNull  final List<MenuGrupo> lista) {
        this.lista = lista;
    }

    @Override
    public final MenuGrupoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        // Para que match_parent funcione dentro del RecyclerView es necesario parent, false
        // https://stackoverflow.com/questions/30691150/match-parent-width-does-not-work-in-recyclerview
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_grupo, parent, false);
        return new MenuGrupoViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(final MenuGrupoViewHolder holder, final int position) {
        holder.mostrar(lista.get(position));
    }

    @Override
    public final int getItemCount() {
        return lista.size();
    }
}
