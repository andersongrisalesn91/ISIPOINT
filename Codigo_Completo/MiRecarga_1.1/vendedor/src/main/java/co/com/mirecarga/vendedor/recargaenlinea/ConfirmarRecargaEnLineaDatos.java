package co.com.mirecarga.vendedor.recargaenlinea;

/**
 * Datos para la funcionalidad de confirmar recarga en línea.
 */
public class ConfirmarRecargaEnLineaDatos {
    /**
     * Id del operdor.
     */
    private int idOperador;
    /**
     * Nombre del operador.
     */
    private String nombreOperador;
    /**
     * Número a recargar.
     */
    private String numeroRecargar;
    /**
     * valor a recargar.
     */
    private int valorRecargar;
    /**
     * El saldo actual disponible para recargas.
     */
    private String saldo;

    /**
     * Regresa el campo idOperador.
     *
     * @return el valor de idOperador
     */
    public int getIdOperador() {
        return idOperador;
    }

    /**
     * Establece el valor del campo idOperador.
     *
     * @param idOperador el valor a establecer
     */
    public void setIdOperador(final int idOperador) {
        this.idOperador = idOperador;
    }

    /**
     * Regresa el campo nombreOperador.
     *
     * @return el valor de nombreOperador
     */
    public String getNombreOperador() {
        return nombreOperador;
    }

    /**
     * Establece el valor del campo nombreOperador.
     *
     * @param nombreOperador el valor a establecer
     */
    public void setNombreOperador(final String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    /**
     * Regresa el campo numeroRecargar.
     *
     * @return el valor de numeroRecargar
     */
    public String getNumeroRecargar() {
        return numeroRecargar;
    }

    /**
     * Establece el valor del campo numeroRecargar.
     *
     * @param numeroRecargar el valor a establecer
     */
    public void setNumeroRecargar(final String numeroRecargar) {
        this.numeroRecargar = numeroRecargar;
    }

    /**
     * Regresa el campo valorRecargar.
     *
     * @return el valor de valorRecargar
     */
    public int getValorRecargar() {
        return valorRecargar;
    }

    /**
     * Establece el valor del campo valorRecargar.
     *
     * @param valorRecargar el valor a establecer
     */
    public void setValorRecargar(final int valorRecargar) {
        this.valorRecargar = valorRecargar;
    }

    /**
     * Regresa el campo saldo.
     *
     * @return el valor de saldo
     */
    public String getSaldo() {
        return saldo;
    }

    /**
     * Establece el valor del campo saldo.
     *
     * @param saldo el valor a establecer
     */
    public void setSaldo(final String saldo) {
        this.saldo = saldo;
    }
}
