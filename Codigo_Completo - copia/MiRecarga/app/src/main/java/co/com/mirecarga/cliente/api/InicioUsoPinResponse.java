package co.com.mirecarga.cliente.api;

/**
 * Respuesta del método inicioUsoPin.
 */

public class InicioUsoPinResponse extends MiRecargaResponse {

    /**
     * Los minutos disponibles.
     */
    private String minutosDisponibles;
    /**
     * El valor del minuto.
     */
    private String valorMinuto;

    /**
     * Regresa el campo minutosDisponibles.
     *
     * @return el valor de minutosDisponibles
     */
    public String getMinutosDisponibles() {
        return minutosDisponibles;
    }

    /**
     * Establece el valor del campo minutosDisponibles.
     *
     * @param minutosDisponibles el valor a establecer
     */
    public void setMinutosDisponibles(final String minutosDisponibles) {
        this.minutosDisponibles = minutosDisponibles;
    }

    /**
     * Regresa el campo valorMinuto.
     *
     * @return el valor de valorMinuto
     */
    public String getValorMinuto() {
        return valorMinuto;
    }

    /**
     * Establece el valor del campo valorMinuto.
     *
     * @param valorMinuto el valor a establecer
     */
    public void setValorMinuto(final String valorMinuto) {
        this.valorMinuto = valorMinuto;
    }
}
