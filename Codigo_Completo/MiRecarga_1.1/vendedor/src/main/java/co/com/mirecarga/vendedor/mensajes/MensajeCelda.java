package co.com.mirecarga.vendedor.mensajes;

/**
 * Datos recibidos de la celda.
 */
public class MensajeCelda {

    /**
     * El id de la zona.
     */
    private int idZona;

    /**
     * El id de la celda.
     */
    private int idCelda;

    /**
     * El id del estado actual.
     */
    private int idEstado;

    /**
     * Regresa el campo idZona.
     *
     * @return el valor de idZona
     */
    public int getIdZona() {
        return idZona;
    }

    /**
     * Establece el valor del campo idZona.
     *
     * @param idZona el valor a establecer
     */
    public void setIdZona(final int idZona) {
        this.idZona = idZona;
    }

    /**
     * Regresa el campo idCelda.
     *
     * @return el valor de idCelda
     */
    public int getIdCelda() {
        return idCelda;
    }

    /**
     * Establece el valor del campo idCelda.
     *
     * @param idCelda el valor a establecer
     */
    public void setIdCelda(final int idCelda) {
        this.idCelda = idCelda;
    }

    /**
     * Regresa el campo idEstado.
     *
     * @return el valor de idEstado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Establece el valor del campo idEstado.
     *
     * @param idEstado el valor a establecer
     */
    public void setIdEstado(final int idEstado) {
        this.idEstado = idEstado;
    }
}
