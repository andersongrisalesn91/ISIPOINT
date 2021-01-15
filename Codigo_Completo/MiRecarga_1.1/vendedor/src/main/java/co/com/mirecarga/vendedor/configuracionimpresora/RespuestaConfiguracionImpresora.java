package co.com.mirecarga.vendedor.configuracionimpresora;

/**
 * Mensaje estándar de respuesta de los servicios.
 */
public class RespuestaConfiguracionImpresora {

    /**
     * Tipo de impresora.
     */
    private String tipoImpresora;
    /**
     * Dirección MAC impresora bluetooth.
     */
    private String macImpresoraBluetooth;
    /**
     * Dirección MAC impresora bluetooth.
     */
    private String nombreImpresoraBluetooth;

    /**
     * Regresa el campo tipoImpresora.
     *
     * @return el valor de tipoImpresora
     */
    public final String getTipoImpresora() {
        return tipoImpresora;
    }

    /**
     * Establece el valor del campo tipoImpresora.
     *
     * @param tipoImpresora el valor a establecer
     */
    public final void setTipoImpresora(final String tipoImpresora) {
        this.tipoImpresora = tipoImpresora;
    }

    /**
     * Regresa el campo macImpresoraBluetooth.
     *
     * @return el valor de macImpresoraBluetooth
     */
    public final String getMacImpresoraBluetooth() {
        return macImpresoraBluetooth;
    }

    /**
     * Establece el valor del campo macImpresoraBluetooth.
     *
     * @param macImpresoraBluetooth el valor a establecer
     */
    public final void setMacImpresoraBluetooth(final String macImpresoraBluetooth) {
        this.macImpresoraBluetooth = macImpresoraBluetooth;
    }

    /**
     * Regresa el campo nombreImpresoraBluetooth.
     *
     * @return el valor de nombreImpresoraBluetooth
     */
    public String getNombreImpresoraBluetooth() {
        return nombreImpresoraBluetooth;
    }

    /**
     * Establece el valor del campo nombreImpresoraBluetooth.
     *
     * @param nombreImpresoraBluetooth el valor a establecer
     */
    public void setNombreImpresoraBluetooth(final String nombreImpresoraBluetooth) {
        this.nombreImpresoraBluetooth = nombreImpresoraBluetooth;
    }

    /**
     * Indica que se trata de impresora local.
     * @return true si es impresora local
     */
    public final boolean isImpresoraLocal() {
        return tipoImpresora.equals(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_LOCAL);
    }

    /**
     * Indica que se trata de impresora Bluetooth.
     * @return true si es impresora Bluetooth
     */
    public final boolean isImpresoraBluetooth() {
        return tipoImpresora.equals(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_BLUETOOTH);
    }

    /**
     * Indica que se trata de impresora Bluetooth.
     * @return true si es impresora Bluetooth
     */
    public final boolean isImpresoraZebra() {
        return tipoImpresora.equals(ConfiguracionImpresoraServiceBean.TIPO_IMPRESORA_ZEBRA);
    }
}
