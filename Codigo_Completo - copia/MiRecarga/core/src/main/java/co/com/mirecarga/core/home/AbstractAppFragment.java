package co.com.mirecarga.core.home;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.com.mirecarga.core.R;
import co.com.mirecarga.core.util.AppException;
import co.com.mirecarga.core.util.AppLog;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Lógica compartida por los fragmentos navegables de la aplicación.
 */
public abstract class AbstractAppFragment extends Fragment implements PaginaNavegacion {
    /**
     * El tag para el log.
     */
    private static final String TAG = "AbstractAppFragment";

    /**
     * Instancia del navegador.
     */
    private transient NavegadorListener navegador;

    /**
     * El título de la página.
     */
    private String titulo;

    /**
     * Libera butterknife en fragmentos.
     */
    private transient Unbinder unbinder;

    /**
     * Observadores que se deben liberar al destruir.
     */
    private final transient CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Número de items en procesamiento.
     */
    private transient int cantidadProcesando;

    /**
     * Servicio con los datos de la sesión.
     */
    @Inject
    transient HomeService homeService;

    /**
     * Id del layout para el fragmento.
     * @return id id a utilizar
     */
    protected abstract int getIdLayout();

    /**
     * Indica si la página muestra el menú filtrar.
     */
    private boolean permitirFiltrar;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(getIdLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            titulo = savedInstanceState.getString("titulo");
            AppLog.debug(TAG, "Recuperando en onActivityCreated %s %s", titulo, this);
        }
        AppLog.debug(TAG, "consultarModelo en %s", this);
        consultarModelo();
        navegador.setTitulo(titulo);
        navegador.setPermitirFiltrar(permitirFiltrar);
    }

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof NavegadorListener) {
            navegador = (NavegadorListener) context;
        } else {
            throw new AppException("%s debe implementar NavegadorListener", context);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
        cantidadProcesando = 0;
        finalizarProcesandoMainThread();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        AppLog.debug(TAG, "onSaveInstanceState %s %s", titulo, this);
        outState.putString("titulo", titulo);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navegador = null;
    }

    /**
     * Regresa el campo navegador.
     *
     * @return el valor de navegador
     */
    public NavegadorListener getNavegador() {
        return navegador;
    }

    /**
     * Carga los datos iniciales de la página.
     */
    protected abstract void consultarModelo();

    @Override
    public final String getTitulo() {
        return  titulo;
    }

    @Override
    public final void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * Establece el título de la página.
     * @param idTitulo el id del string con el título de la página
     */
    public final void setTitulo(final int idTitulo) {
        setTitulo(getContext().getResources().getString(idTitulo));
    }

    /**
     * Muestra un mensaje breve al usuario de forma estándar.
     * @param mensaje el mensaje a mostrar
     * @param args argumentos variables para String.format.
     */
    public final void mostrarMensaje(final String mensaje, final Object... args) {
        AppLog.debug(TAG, mensaje, args);
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                String.format(mensaje, args), Snackbar.LENGTH_LONG).show();
        finalizarProcesando();
    }

    /**
     * Agrega el elmenento a la lista de objetos a liberar.
     * @param disposable el observable a destruir
     */
    public final void disp(final Disposable disposable) {
        disposables.add(disposable);
    }

    /**
     * Procesa el evento de error.
     * @param throwable la excepción generado
     */
    public final void handleError(final Throwable throwable) {
        AppLog.error(TAG, throwable, "Ocurrió un error inesperado");
        mostrarMensaje(getString(R.string.msg_error_inesperado));
    }

    /**
     * Procesa el evento de error.
     * @param throwable la excepción generado
     */
    public final void handleErrorSinMensaje(final Throwable throwable) {
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

    /**
     * Utilidad genérica para facilitar el llamado asíncrono al API.
     * @param observable el resultado observable del API
     * @param onNext el método a llamar con los resultados
     * @param <T> el tipo de dato genérico
     */
    public final <T> void subscribe(final Observable<T> observable, final Consumer<? super T> onNext) {
        mostrarProcesando();
        disp(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> procesarRespuesta(onNext, resp),
                        this::handleError, this::finalizarProcesando));
    }

    /**
     * Utilidad genérica para facilitar el llamado asíncrono al API.
     * @param observable el resultado observable del API
     * @param onNext el método a llamar con los resultados
     * @param <T> el tipo de dato genérico
     */
    public final <T> void subscribeSinProcesando(final Observable<T> observable, final Consumer<? super T> onNext) {
        disp(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> procesarRespuesta(onNext, resp),
                        this::handleErrorSinMensaje, () -> { }));
    }

    /**
     * Muestra el indicador de procesamiento.
     */
    public final void mostrarProcesando() {
        getActivity().runOnUiThread(() -> {
            cantidadProcesando++;
            navegador.mostrarProcesando(true);
        });
    }

    /**
     * Detecta cuando terminó una tarea asíncrona.
     */
    public final void finalizarProcesandoMainThread() {
        cantidadProcesando--;
        if (cantidadProcesando <= 0) {
            navegador.mostrarProcesando(false);
        }
    }

    /**
     * Detecta cuando terminó una tarea asíncrona.
     */
    public final void finalizarProcesando() {
        getActivity().runOnUiThread(this::finalizarProcesandoMainThread);
    }

    /**
     * Id del usuario autenticado.
     * @return el id del usuario autenticado o null si no está autenticado.
     */
    public final Integer getIdUsuario() {
        return homeService.consultarModelo().getIdUsuario();
    }

    /**
     * Indica si la sesión está activa con una validación de la vigencia del token.
     * @return true si el token está vigente
     */
    public final boolean isSesionActiva() {
        final boolean sesionActiva = homeService.isSesionActiva();
        if (!sesionActiva) {
            navegador.cerrarSesion();
        }
        return sesionActiva;
    }

    @Override
    public boolean isPermitirFiltrar() {
        return permitirFiltrar;
    }

    /**
     * Establece el valor del campo permitirFiltrar.
     *
     * @param permitirFiltrar el valor a establecer
     */
    public void setPermitirFiltrar(final boolean permitirFiltrar) {
        this.permitirFiltrar = permitirFiltrar;
    }

    /**
     * Regresa el campo homeService.
     *
     * @return el valor de homeService
     */
    public HomeService getHomeService() {
        return homeService;
    }
}
