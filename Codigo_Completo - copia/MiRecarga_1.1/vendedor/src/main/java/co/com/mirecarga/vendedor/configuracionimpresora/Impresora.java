package co.com.mirecarga.vendedor.configuracionimpresora;

/**
 * Contrato para la implementación de las impresoras.
 */
public interface Impresora {
    /**
     * Imprime el texto de acuerdo a la configuración de la impresora.
     * @param texto el texto a imprimir
     * @param qrcode el texto a imprimir en en QR
     */
    void imprimir(String texto, String qrcode);
}
