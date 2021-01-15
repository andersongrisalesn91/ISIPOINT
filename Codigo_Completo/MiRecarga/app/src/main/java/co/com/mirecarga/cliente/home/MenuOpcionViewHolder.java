package co.com.mirecarga.cliente.home;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.com.mirecarga.cliente.R;
import co.com.mirecarga.core.home.MenuOpcion;
import co.com.mirecarga.core.home.NavegadorListener;

/**
 * Lógica de presentación de los grupo en la página inicial.
 */
public class MenuOpcionViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    /**
     * El item asociado.
     */
    private transient MenuOpcion opcion;

    /**
     * Constructor con las propiedades.
     * @param view la vista
     */
    public MenuOpcionViewHolder(final View view) {
        super(view);
        view.setOnClickListener(this);
    }

    /**
     * Muestra los datos del item.
     * @param item el item a mostrar
     */
    public final void mostrar(final MenuOpcion item) {
        opcion = item;
        final TextView title = itemView.findViewById(R.id.texto_principal);
        final ImageView imageView = itemView.findViewById(R.id.icon);
        final TextView descripcion = itemView.findViewById(R.id.texto_secundario);

        title.setText(item.getNombre());
        final Context context = this.itemView.getContext();
        final int idIcono = context.getResources().getIdentifier(item.getIcono(), "drawable", context.getPackageName());
        imageView.setImageResource(idIcono);
        descripcion.setText(item.getDescripcion());
    }

    @Override
    public final void onClick(final View view) {
        final Context context = view.getContext();
        if (opcion != null && context instanceof NavegadorListener) {
            ((NavegadorListener) context).navegar(opcion.getId());
        }
    }
}
