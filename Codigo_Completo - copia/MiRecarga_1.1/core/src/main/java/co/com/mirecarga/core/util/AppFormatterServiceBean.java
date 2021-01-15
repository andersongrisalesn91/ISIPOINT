package co.com.mirecarga.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Servicio para dar formato a objetos.
 */
@Singleton
public class AppFormatterServiceBean implements AppFormatterService {
    /**
     * Formatter para fechas.
     */
    private final transient SimpleDateFormat dateFormatterFecha;
    /**
     * Formatter para fechas.
     */
    private final transient SimpleDateFormat dateFormatterFechaHora;
    /**
     * Formatter para números.
     */
    private final transient DecimalFormat decimalFormatter;

    /**
     * Constructor con las propiedades.
     * @param locale la localidad del usuario
     */
    @Inject
    public AppFormatterServiceBean(final Locale locale) {
        dateFormatterFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA, locale);
        dateFormatterFechaHora = new SimpleDateFormat(Constantes.FORMATO_FECHA_HORA, locale);
        decimalFormatter = new DecimalFormat();
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        decimalFormatter.setDecimalFormatSymbols(symbols);
    }

    @Override
    public final Date stringToDate(final String texto) {
        final Date resultado;
        if (texto != null && texto.length() > 0) {
            try {
                resultado = dateFormatterFecha.parse(texto);
            } catch (final ParseException e) {
                throw new AppException(e, "No fue posible convertir %s a Date", texto);
            }
        } else {
            resultado = null;
        }
        return resultado;
    }

    @Override
    public final String formatearFecha(final Date valor) {
        final String texto;
        if (valor == null) {
            texto = "";
        } else {
            texto = dateFormatterFecha.format(valor);
        }
        return texto;
    }

    @Override
    public final String formatearFechaHora(final Date date) {
        return dateFormatterFechaHora.format(date);
    }

    @Override
    public final String formatearFechaHora(final long valor) {
        final Date date = new Date(valor);
        return dateFormatterFechaHora.format(date);
    }

    @Override
    public final String formatearFechaHora(final Long valor) {
        String fechaFormateada = "";

        if (valor != null) {
            final Date date = new Date(valor);
            fechaFormateada = dateFormatterFechaHora.format(date);
        }

        return fechaFormateada;
    }

    @Override
    public final String formatearDecimal(final double valor) {
        return decimalFormatter.format(valor);
    }

}
