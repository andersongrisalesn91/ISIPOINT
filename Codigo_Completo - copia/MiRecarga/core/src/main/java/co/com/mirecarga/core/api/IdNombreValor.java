package co.com.mirecarga.core.api;

/**
 * Datos de clase genérica retornados.
 */
public class IdNombreValor extends IdNombre {
    /**
     * Valor del operador.
     */
    private String valor;

    /**
     * Regresa el campo valor.
     *
     * @return el valor de valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor del campo valor.
     *
     * @param valor el valor a establecer
     */
    public void setValor(final String valor) {
        this.valor = valor;
    }
}
