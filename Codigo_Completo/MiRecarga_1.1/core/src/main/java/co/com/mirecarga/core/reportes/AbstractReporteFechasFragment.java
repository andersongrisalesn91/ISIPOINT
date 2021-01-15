package co.com.mirecarga.core.reportes;

import android.view.View;

import java.util.Date;

import javax.inject.Inject;

import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.DatePickerUtil;

/**
 * Funcionalidad genérica de consulta de reportes.
 * @param <T> el tipo de dato de los registros
 */
public abstract class AbstractReporteFechasFragment<T> extends AbstractReporteFragment<T> {
    /**
     * Utilidad para el manejo de fechas.
     */
    private transient DatePickerUtil fechaInicial;

    /**
     * Utilidad para el manejo de fechas.
     */
    private transient DatePickerUtil fechaFinal;

    /**
     * Manejador de formato de la aplicación.
     */
    @Inject
    protected transient AppFormatterService format;

    /**
     * Id del estilo para el control datepicker.
     * @return id id a utilizar
     */
    protected abstract int getIdDatePickerStyle();

    /**
     * Id del control para fechaInicial.
     * @return id id a utilizar
     */
    protected abstract int getIdFechaInicial();

    /**
     * Id del control para fechaInicialSelector.
     * @return id id a utilizar
     */
    protected abstract int getIdFechaInicialSelector();

    /**
     * Id del control para fechaFinal.
     * @return id id a utilizar
     */
    protected abstract int getIdFechaFinal();

    /**
     * Id del control para fechaFinalSelector.
     * @return id id a utilizar
     */
    protected abstract int getIdFechaFinalSelector();

    /**
     * Consulta el modelo específico para el reporte.
     */
    protected abstract void consultarModeloReporteFechas();

    @Override
    protected final void consultarModeloReporte() {
        final View view = getView();
        assert view != null;
        final Date hoy = new Date();
        fechaInicial = new DatePickerUtil(format, getIdDatePickerStyle(),
                view.findViewById(getIdFechaInicialSelector()),
                view.findViewById(getIdFechaInicial()),
                DatePickerUtil.getPrimerDiaMes());
        fechaInicial.setMaxDate(hoy);
        fechaFinal = new DatePickerUtil(format, getIdDatePickerStyle(),
                view.findViewById(getIdFechaFinalSelector()),
                view.findViewById(getIdFechaFinal()),
                hoy);
        fechaFinal.setMaxDate(hoy);
        consultarModeloReporteFechas();
    }

    /**
     * Regresa el campo fechaInicial.
     *
     * @return el valor de fechaInicial
     */
    public final DatePickerUtil getFechaInicial() {
        return fechaInicial;
    }

    /**
     * Regresa el campo fechaFinal.
     *
     * @return el valor de fechaFinal
     */
    public final DatePickerUtil getFechaFinal() {
        return fechaFinal;
    }
}
