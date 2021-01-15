package co.com.mirecarga.core.util;

/**
 * Configuración general de la aplicación.
 */
public final class Constantes {
    /**
     * Formato a usar en controles de fecha.
     */
    public static final String FORMATO_FECHA = "yyyy-MM-dd";

    /**
     * Formato a usar en fechas.
     */
    public static final String FORMATO_FECHA_HORA = "yyyy-MM-dd hh:mm a";

    /**
     * Texto a mostrar para valores fechas vacías.
     */
    public static final String FECHA_VACIA = "0000-00-00";

    /**
     * Texto para auditoría vacío.
     */
    public static final String AUDITORIA_VACIO = "null";

    /**
     * El nombre de las preferencias.
     */
    public static final String PREFERENCIAS = "preferencias";

    /**
     * Constructor privado para evitar instancias.
     */
    private Constantes() {
        super();
    }
}
