package co.com.mirecarga.core.util;

/**
 * Contrato para el Servicio que lee la configuración de la aplicación desde un archivo Json.
 * @param <T> el tipo de datos de la configuración
 */
public interface ConfigService<T> {
    /**
     * Lee la configuración desde el origen.
     * @return los datos de la configuración
     */
    T getConfig();
}
