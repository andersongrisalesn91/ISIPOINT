package co.com.mirecarga.vendedor.reportes;

import javax.inject.Inject;

import co.com.mirecarga.core.reportes.AbstractReporteFechasFragment;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.api.ApiVendedorService;

/**
 * Funcionalidad genérica de consulta de reportes.
 * @param <T> el tipo de dato de los registros
 */
public abstract class AbstractReportesVendedorFragment<T> extends AbstractReporteFechasFragment<T> {
    /**
     * Servicio de acceso al API.
     */
    @Inject
    transient ApiVendedorService api;

    @Override
    protected int getIdDatePickerStyle() {
        return R.style.AppDatePickerStyle;
    }

    @Override
    protected int getIdFechaInicial() {
        return R.id.fechaInicial;
    }

    @Override
    protected int getIdFechaInicialSelector() {
        return R.id.fechaInicialSelector;
    }

    @Override
    protected int getIdFechaFinal() {
        return R.id.fechaFinal;
    }

    @Override
    protected int getIdFechaFinalSelector() {
        return R.id.fechaFinalSelector;
    }

    @Override
    protected int getIdFiltrar() {
        return R.id.filtrar;
    }

    @Override
    protected int getIdRegistros() {
        return R.id.registros;
    }
}
