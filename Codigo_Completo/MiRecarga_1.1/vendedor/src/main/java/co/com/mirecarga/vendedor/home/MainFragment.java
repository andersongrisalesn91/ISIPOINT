package co.com.mirecarga.vendedor.home;

import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import butterknife.BindView;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.mqtt.IReceivedMessageListener;
import co.com.mirecarga.vendedor.mqtt.MQTTService;
import co.com.mirecarga.vendedor.mqtt.ReceivedMessage;

/**
 * Fragmento con los datos de la página inicial.
 */
public class MainFragment extends AbstractAppFragment
        implements IReceivedMessageListener {

    private static final String TAG = MainFragment.class.getSimpleName();

//    /**
//     * Control de página.
//     */
//    @BindView(R.id.texto_usuario_main)
//    transient TextView homeNombreUsuario;

    /**
     * Control de página.
     */
    @BindView(R.id.grupos)
    transient RecyclerView recyclerView;

    /**
     * Servicio de la actividad principal.
     */
    @Inject
    transient HomeService homeService;

    /**
     * Manejador de MQTT.
     */
    @Inject
    transient MQTTService mqttService;

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected final void consultarModelo() {
        setTitulo(R.string.titulo_principal);
        final RespuestaInicio resp = homeService.consultarModelo();
//        homeNombreUsuario.setText(resp.getNombreCompleto());

        recyclerView.setAdapter(new MenuGrupoAdapter(resp.getMenu()));
//        mqttService.suscribir("isipoint/paises/1/departamentos/3/municipios/149/parqueaderosyzonas/1/eventos/sale");

//        final Handler handler = new Handler();
//        handler.postDelayed(() -> {
            mqttService.suscribir("isipoint/paises/1/departamentos/3/municipios/149/parqueaderosyzonas/1/eventos/sale");
//        }, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mqttService.removeReceivedMessageListner(this);
    }

    @Override
    public void onMessageReceived(ReceivedMessage message) {
        AppLog.debug(TAG, "MQTT Topico recibido '%s'", message.getTopic());
        if (message.getTopic().endsWith("/eventos/sale")) {
            final Gson gson = new GsonBuilder().create();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mqttService.addReceivedMessageListner(this);
    }
}
