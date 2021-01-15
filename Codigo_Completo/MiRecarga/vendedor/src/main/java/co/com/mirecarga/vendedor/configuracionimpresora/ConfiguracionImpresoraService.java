package co.com.mirecarga.vendedor.configuracionimpresora;

import co.com.mirecarga.core.util.RespuestaMensaje;

/**
 * Contrato para el servicio de configuración de impresora para el vendedor.
 */
public interface ConfiguracionImpresoraService {
    /**
     * Consulta los datos para la página.
     * @return la respuesta con los datos
     */
    RespuestaConfiguracionImpresora consultarModelo();

    /**
     * Actualiza la preferencia de la impresora.
     * @param llave la llave de la preferencia
     * @param valor el valor
     * @return la respuesta
     */
    RespuestaMensaje actualizarPreferencia(String llave, String valor);
}
