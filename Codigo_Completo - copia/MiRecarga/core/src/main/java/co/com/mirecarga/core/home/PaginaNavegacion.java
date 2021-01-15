package co.com.mirecarga.core.home;

/**
 * Contrato para los objetos que implementan la página de navegación.
 */
public interface PaginaNavegacion {
    /**
     * Obtiene el título de la página.
     * @return el título de la página
     */
    String getTitulo();

    /**
     * Establece el título de la página.
     * @param titulo el título de la página
     */
    void setTitulo(String titulo);

    /**
     * Indica si la página muestra el menú filtrar.
     * @return true si muestra el menú filtrar
     */
    boolean isPermitirFiltrar();
}
