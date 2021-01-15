package co.com.mirecarga.core.home;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import co.com.mirecarga.core.login.DatosIniciarSesion;

/**
 * Respuesta del servicio de inicio.
 */
public class RespuestaInicio extends DatosIniciarSesion {

    /**
     * Indica si el usuario está autenticado.
     */
    private boolean autenticado;

    /**
     * Representación del menú en grupos.
     */
    private List<MenuGrupo> menu = new ArrayList<>();

    /**
     * Índice con las opciones del menú.
     */
    private final transient SparseArray<MenuOpcion> indice = new SparseArray<>();

    /**
     * Regresa el campo menu.
     *
     * @return el valor de menu
     */
    public List<MenuGrupo> getMenu() {
        return menu;
    }

    /**
     * Establece el valor del campo menu.
     *
     * @param menu el valor a establecer
     */
    public void setMenu(final List<MenuGrupo> menu) {
        this.menu = menu;
    }

    /**
     * Regresa el campo autenticado.
     *
     * @return el valor de autenticado
     */
    public boolean isAutenticado() {
        return autenticado;
    }

    /**
     * Establece el valor del campo autenticado.
     *
     * @param autenticado el valor a establecer
     */
    public void setAutenticado(final boolean autenticado) {
        this.autenticado = autenticado;
    }

    /**
     * Agrega el grupo a la lista.
     * @param nombre el nombre del grupo
     * @return el grupo creado
     */
    public final MenuGrupo agregarGrupo(final String nombre) {
        final MenuGrupo grupo = new MenuGrupo(nombre);
        menu.add(grupo);
        return grupo;
    }

    /**
     * Obtiene la opción a partir del id.
     * @param id el id de la opción
     * @return la opción o null si no existe
     */
    public final MenuOpcion getOpcionPorId(final int id) {
        return indice.get(id);
    }

    /**
     * Calcula el índice de opciones.
     */
    public final void indexar() {
        indice.clear();
        for (final MenuGrupo grupo : menu) {
            for (final MenuOpcion opcion : grupo.getItems()) {
                indice.put(opcion.getId(), opcion);
            }
        }
    }
}
