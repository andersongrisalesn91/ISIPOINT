package co.com.mirecarga.core.home;

import java.util.List;

/**
 * Propiedades de configuración del menú de la aplicación.
 */
public class ConfigMenu {
    /**
     * Representación del menú en grupos.
     */
    private List<MenuGrupo> menu;

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
}
