package co.com.mirecarga.cliente.api;

import co.com.mirecarga.core.api.Placa;

/**
 * Respuesta del método agregar placa.
 */
public class AgregarPlacaResponse {
    /**
     * La placa creada.
     */
    private Placa recurso;

    /**
     * El error.
     */
    private String error;

    /**
     * Regresa el campo recurso.
     *
     * @return el valor de recurso
     */
    public Placa getRecurso() {
        return recurso;
    }

    /**
     * Establece el valor del campo recurso.
     *
     * @param recurso el valor a establecer
     */
    public void setRecurso(final Placa recurso) {
        this.recurso = recurso;
    }

    /**
     * Regresa el campo error.
     *
     * @return el valor de error
     */
    public String getError() {
        return error;
    }

    /**
     * Establece el valor del campo error.
     *
     * @param error el valor a establecer
     */
    public void setError(final String error) {
        this.error = error;
    }
}
