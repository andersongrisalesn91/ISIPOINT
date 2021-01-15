package co.com.mirecarga.core.home;

import androidx.fragment.app.Fragment;

/**
 * Contrato para la funcionalidad de navegar del MainActivity expuesto para los fragmentos.
 */
public interface NavegadorListener {
    /**
     * Establece el título de la página.
     * @param titulo el título de la página
     */
    void setTitulo(String titulo);

    /**
     * Muestra el fragmento indicado por la clase.
     * @param fragmentClass la clase del fragmento
     * @param titulo el título a usar en la página
     * @return el objeto creado
     */
    Fragment navegar(Class fragmentClass, String titulo);

    /**
     * Muestra el fragmento indicado por la instancia.
     * @param fragment la instancia del fragmento
     */
    void navegar(Fragment fragment);

    /**
     * Muestra el fragmento de acuerdo al id.
     * @param id el de la página
     */
    void navegar(int id);

    /**
     * Muestra el fragmento indicado por la instancia sin agregarlo a la pila.
     * @param fragment la instancia del fragmento
     */
    void remplazar(Fragment fragment);

    /**
     * Navega a la página anterior en la pila.
     */
    void irAtras();

    /**
     * Navega a la página inicial.
     */
    void irInicio();

    /**
     * Actualiza el estado debido a que se inició sesión.
     */
    void actualizarModelo();

    /**
     * Cierra la sesión actual porque venció el token.
     */
    void cerrarSesion();

    /**
     * Muestra el indicador de procesamiento.
     * @param mostrar indica si se debe mostrar u ocultar
     */
    void mostrarProcesando(boolean mostrar);

    /**
     * Indica si la página muestra el menú filtrar.
     *
     * @param permitirFiltrar el valor a establecer
     */
    void setPermitirFiltrar(boolean permitirFiltrar);
}
