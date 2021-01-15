package co.com.mirecarga.core.util;

import java.util.Date;

/**
 * Contrato del servicio para dar formato a objetos.
 */
public interface AppFormatterService {
    /**
     * Convierte una cadena a Date usando el formato del sistema.
     *
     * @param texto El valor de la propiedad.
     * @return valor convertido.
     */
    Date stringToDate(String texto);

    /**
     * Da formato de presentación a la fecha.
     * @param valor el valor a dar formato
     * @return el texto de la fecha
     */
    String formatearFecha(Date valor);

    /**
     * Da formato de presentación a la fecha.
     * @param date el valor a dar formato
     * @return el texto de la fecha
     */
    String formatearFechaHora(Date date);

    /**
     * Da formato de presentación a la fecha.  Se utiliza long porque el API lo utiliza.
     * @param valor el valor a dar formato
     * @return el texto de la fecha
     */
    String formatearFechaHora(long valor);

    /**
     * Da formato de presentación a la fecha.  Se utiliza long porque el API lo utiliza.
     * @param valor el valor a dar formato
     * @return el texto de la fecha
     */
    String formatearFechaHora(Long valor);

    /**
     * Da formato al valor.
     * @param valor el valor a formatear
     * @return el valor con formato
     */
    String formatearDecimal(double valor);
}
