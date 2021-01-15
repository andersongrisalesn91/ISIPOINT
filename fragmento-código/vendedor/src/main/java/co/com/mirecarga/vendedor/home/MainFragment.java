package co.com.mirecarga.vendedor.home;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.vendedor.R;

/**
 * Fragmento con los datos de la página inicial.
 */
public class MainFragment extends AbstractAppFragment {
    /**
     * Control de página.
     */
    @BindView(R.id.texto_usuario_main)
    transient TextView homeNombreUsuario;

    /**
     * Control de página.
     */
    @BindView(R.id.grupos)
    transient RecyclerView recyclerView;

    /**
     * Servicio de la actividad principal.
     */
    @Inject
    transient HomeService homeService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected final void consultarModelo() {
        setTitulo(R.string.titulo_principal);
        final RespuestaInicio resp = homeService.consultarModelo();
        homeNombreUsuario.setText(resp.getNombreCompleto());

        recyclerView.setAdapter(new MenuGrupoAdapter(resp.getMenu()));
    }
}
