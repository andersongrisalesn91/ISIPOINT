package co.com.mirecarga.vendedor.recargaenlinea;

import java.util.List;

import co.com.mirecarga.core.util.RespuestaMensaje;
import co.com.mirecarga.vendedor.api.Operador;

/**
 * Mensaje estándar de respuesta de los servicios.
 */
public class RespuestaRecargaEnLinea extends RespuestaMensaje {

    /**
     * El saldo actual disponible para recargas.
     */
    private String saldo;

    /**
     * Lista de operadores.
     */
    private List<Operador> listaOperador;

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

    /**
     * Regresa el campo listaOperador.
     *
     * @return el valor de listaOperador
     */
    public List<Operador> getListaOperador() {
        return listaOperador;
    }

    /**
     * Establece el valor del campo listaOperador.
     *
     * @param listaOperador el valor a establecer
     */
    public void setListaOperador(final List<Operador> listaOperador) {
        this.listaOperador = listaOperador;
    }
}
