package co.com.mirecarga.vendedor.offline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import javax.inject.Inject;

import co.com.mirecarga.core.api.TransaccionCelda;
import co.com.mirecarga.core.offline.OfflineMessage;
import co.com.mirecarga.core.offline.OfflineRepository;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.api.ApiZERService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Servicio que efectúa lógica fuera de línea.
 */
public class VendedorOfflineServiceBean implements VendedorOfflineService {
    /**
     * El tag para el log.
     */
    private static final String TAG = "VendedorOfflineServiceB";
    /**
     * Repositorio de datos.
     */
    private final transient OfflineRepository offlineRepository;

    /**
     * Servicio rest.
     */
    private final transient ApiZERService apiZERService;

    /**
     * Para convertir json.
     */
    private final transient Gson gson;

    /**
     * Observadores que se deben liberar al destruir.
     */
    private final transient CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Constructor con las propiedades.
     * @param offlineRepository el valor de la propiedad
     * @param apiZERService el valor de la propiedad
     */
    @Inject
    public VendedorOfflineServiceBean(final OfflineRepository offlineRepository, final ApiZERService apiZERService) {
        this.offlineRepository = offlineRepository;
        this.apiZERService = apiZERService;
        gson = new GsonBuilder()
                .setPrettyPrinting().create();
    }

    @Override
    public final Observable<TransaccionCelda> registrarVenta(final VentaDatos datos) {
        Observable<TransaccionCelda> resp;
        if (offlineRepository.isConnected()) {
            resp = registrarVentaInterno(datos);
        } else {
            resp = Observable.just(registrarOffline("venta", datos));
        }
        return resp;
    }

    /**
     * Registra la transacción offline.
     * @param tipo el tipo de tx
     * @param datos los datos a almacenar
     * @return la respuesta
     */
    private TransaccionCelda registrarOffline(final String tipo, final Object datos) {
        final String texto = gson.toJson(datos);
        OfflineMessage mes = new OfflineMessage();
        mes.setTipo(tipo);
        mes.setTexto(texto);
        offlineRepository.guardar(mes);
        AppLog.debug(TAG, "Almacenado json %s", texto);
        return new TransaccionCelda();
    }

    /**
     * Registra la venta.
     * @param datos los datos para el registro
     * @return la respuesta del servicio
     */
    private Observable<TransaccionCelda> registrarVentaInterno(final VentaDatos datos) {
        return apiZERService.registrar(datos.getIdPais(), datos.getIdDepartamento(),
                datos.getIdMunicipio(), datos.getIdZonasYParqueaderos(),
                datos.getIdCelda(), datos.getDatos());
    }

    @Override
    public final void sincronizar() {
        List<OfflineMessage>  lista = offlineRepository.consultarPorTipo("venta");
        for (OfflineMessage item : lista) {
            try {
                VentaDatos datos = gson.fromJson(item.getTexto(), VentaDatos.class);
                subscribe(registrarVentaInterno(datos), resp ->
                        offlineRepository.eliminar(item.getId())
                );
            } catch (Exception ex) {
                AppLog.error(TAG, ex, "Error al leer json %s", item.getTexto());
                offlineRepository.eliminar(item.getId());
            }
        }
    }


    /**
     * Agrega el elmenento a la lista de objetos a liberar.
     * @param disposable el observable a destruir
     */
    private void disp(final Disposable disposable) {
        disposables.add(disposable);
    }

    /**
     * Utilidad genérica para facilitar el llamado asíncrono al API.
     * @param observable el resultado observable del API
     * @param onNext el método a llamar con los resultados
     * @param <T> el tipo de dato genérico
     */
    private <T> void subscribe(final Observable<T> observable, final Consumer<? super T> onNext) {
        disp(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> procesarRespuesta(onNext, resp),
                        this::handleError));
    }

    /**
     * Procesa el evento de error.
     * @param throwable la excepción generado
     */
    private void handleError(final Throwable throwable) {
        AppLog.error(TAG, throwable, "Ocurrió un error inesperado");
    }

    /**
     * Procesa la respuesta capturando la excepción en caso de error.
     * @param onNext la acción a ejecutar
     * @param resp la respuesta del servicio
     * @param <T> el tipo de dato
     */
    private <T> void procesarRespuesta(final Consumer<? super T> onNext, final T resp) {
        try {
            onNext.accept(resp);
        } catch (final Exception ex) {
            handleError(ex);
        }
    }

}
