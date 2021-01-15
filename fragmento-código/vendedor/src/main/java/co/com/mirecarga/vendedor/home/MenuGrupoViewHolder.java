package co.com.mirecarga.vendedor.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import co.com.mirecarga.core.home.MenuGrupo;
import co.com.mirecarga.vendedor.R;

/**
 * Lógica de presentación de los grupos en la página inicial.
 */
public class MenuGrupoViewHolder extends RecyclerView.ViewHolder {
    /**
     * Constructor con las propiedades.
     * @param view la vista
     */
    public MenuGrupoViewHolder(final View view) {
        super(view);
    }

    /**
     * Muestra los datos del item.
     * @param item el item a mostrar
     */
    public final void mostrar(final MenuGrupo item) {
        final TextView title = itemView.findViewById(R.id.texto_grupo);
        final RecyclerView recyclerView = itemView.findViewById(R.id.opciones);
        title.setText(item.getNombre());
        recyclerView.setAdapter(new MenuOpcionAdapter(item.getItems()));
    }


}
