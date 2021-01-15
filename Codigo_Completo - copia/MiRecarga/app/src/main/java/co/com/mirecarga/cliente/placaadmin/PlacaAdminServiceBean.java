package co.com.mirecarga.cliente.placaadmin;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio de administración de placas del cliente.
 */
@Singleton
public class PlacaAdminServiceBean implements PlacaAdminService {

    /**
     * El estado editando.
     */
    private boolean editando;

    /**
     * El identificador de la placa a editar.
     */
    private  int idPlacaEditar;

    @Override
    public final boolean isEditando() {
        return editando;
    }

    @Override
    public final void setEditando(final boolean editando) {
        this.editando = editando;
    }

    @Override
    public final int getIdPlacaEditar() {
        return idPlacaEditar;
    }

    @Override
    public final void setIdPlacaEditar(final int idPlacaEditar) {
        this.idPlacaEditar = idPlacaEditar;
    }

    /**
     * Constructor con las dependencias.
     */
    @Inject
    public PlacaAdminServiceBean() {
        // Constructor con las dependencias
    }
}
