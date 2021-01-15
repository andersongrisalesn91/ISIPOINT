package co.com.mirecarga.core.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Grupo de menú.
 */
public class MenuGrupo {
    /**
     * Nombre del grupo.
     */
    private String nombre;

    /**
     * Items del menú.
     */
    private List<MenuOpcion> items = new ArrayList<>();

    /**
     * Regresa el campo nombre.
     *
     * @return el valor de nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor del campo nombre.
     *
     * @param nombre el valor a establecer
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el campo items.
     *
     * @return el valor de items
     */
    public List<MenuOpcion> getItems() {
        return items;
    }

    /**
     * Establece el valor del campo items.
     *
     * @param items el valor a establecer
     */
    public void setItems(final List<MenuOpcion> items) {
        this.items = items;
    }

    /**
     * Constructor vacío.
     */
    public MenuGrupo() {
        super();
    }

    /**
     * Constructor con las propiedades.
     * @param nombre el Nombre del grupo
     */
    public MenuGrupo(final String nombre) {
        super();
        this.nombre = nombre;
    }
}
