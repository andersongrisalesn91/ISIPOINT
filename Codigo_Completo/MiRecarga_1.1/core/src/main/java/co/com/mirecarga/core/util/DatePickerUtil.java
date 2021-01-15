package co.com.mirecarga.core.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Utilidad para facilitar el manejo de controles de fecha.
 */
public class DatePickerUtil implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    /**
     * Manejador de formato de fechas.
     */
    private final transient AppFormatterService format;
    /**
     * El control de texto con el valor.
     */
    private final transient TextView textView;
    /**
     * Fecha máxima para el control.
     */
    private Date maxDate;

    /**
     * El id del tema a usar.
     */
    private final transient int idTheme;

    /**
     * Constructor con las propiedades.
     * @param format el formateador de fechas
     * @param idTheme el id del tema a usar
     * @param imagen la imagen a asociar
     * @param texto el control de texto con el valor
     * @param fecha la fecha inicial para el control
     */
    public DatePickerUtil(final AppFormatterService format, final int idTheme, final ImageView imagen,
                          final TextView texto, final Date fecha) {
        this.format = format;
        this.idTheme = idTheme;
        this.textView = texto;
        imagen.setOnClickListener(this);
        texto.setOnClickListener(this);
        if (TextUtils.isEmpty(getTexto())) {
            setDate(fecha);
        }
    }

    /**
     * Regresa el texto mostrado en el control.
     * @return el texto mostrado
     */
    public final String getTexto() {
        return textView.getText().toString();
    }

    /**
     * Regresa la fecha mostrada en el control.
     * @return la fecha mostrada
     */
    public final Date getDate() {
        final Date date;
        final String texto = getTexto();
        if (TextUtils.isEmpty(texto) || TextUtils.equals(texto, Constantes.FECHA_VACIA)) {
            date = null;
        } else {
            date = format.stringToDate(texto);
        }
        return  date;
    }

    /**
     * Establece la fecha.
     * @param fecha la fecha a establecer
     */
    public final void setDate(final Date fecha) {
        final String texto;
        if (fecha == null) {
            texto = Constantes.FECHA_VACIA;
        } else {
            texto = format.formatearFecha(fecha);
        }
        textView.setText(texto);
    }

    /**
     * Regresa el campo maxDate.
     *
     * @return el valor de maxDate
     */
    public final Date getMaxDate() {
        return maxDate;
    }

    /**
     * Establece el valor del campo maxDate.
     *
     * @param maxDate el valor a establecer
     */
    public final void setMaxDate(final Date maxDate) {
        this.maxDate = maxDate;
    }

    /**
     * Utilidad para obtener el primer día del mes.
     * @return el primer día del mes actual
     */
    public static Date getPrimerDiaMes() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    @Override
    public final void onDateSet(final DatePicker datePicker, final int year, final int month, final int day) {
        final Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        setDate(cal.getTime());
    }

    @Override
    public final void onClick(final View view) {
        final Context context = view.getContext();
        final Date date = getDate();
        final Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog dialog = new DatePickerDialog(context, idTheme, this, year, month, day);
        if (maxDate != null) {
            dialog.getDatePicker().setMaxDate(maxDate.getTime());
        }
        dialog.show();
    }
}
