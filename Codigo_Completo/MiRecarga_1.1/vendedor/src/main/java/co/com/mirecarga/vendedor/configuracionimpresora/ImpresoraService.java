package co.com.mirecarga.vendedor.configuracionimpresora;

import co.com.mirecarga.core.home.AbstractAppFragment;

/**
 * Contrato para la implementación de las impresoras.
 */
public interface ImpresoraService {
    /**
     * Imprime el texto de acuerdo a la configuración de la impresora.
     * @param fragment el fragmento actual para mensajes
     * @param texto el texto a imprimir
     * @param qrcode el texto a imprimir en en QR
     */
    void imprimir(AbstractAppFragment fragment, String texto, String qrcode);
}
