package co.com.mirecarga.vendedor.persistence;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Provides
    public PersistenceMVP.Presenter provideNotificacionMVPPresenter(PersistenceMVP.Model model) {
        return new PersistencePresenter(model);
    }

    @Provides
    public PersistenceMVP.Model provideNotificacionMVPModel(PersistenceInterface persistenceNotificacion) {
        return new PersistenceModel(persistenceNotificacion);
    }

    @Provides
    public PersistenceInterface providePersistenceInterface(Context context) {
        return new Persistence_Notificacion(context);
    }
}
