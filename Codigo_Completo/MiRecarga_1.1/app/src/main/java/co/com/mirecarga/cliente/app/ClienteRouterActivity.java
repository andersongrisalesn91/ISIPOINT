package co.com.mirecarga.cliente.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import co.com.mirecarga.core.util.AppLog;

/**
 * Activity para obtener la respuesta de CustomTabs.
 */
public class ClienteRouterActivity extends Activity {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ClienteRouterActivity";

    /**
     * Nombre del parámetro con el CustomTabsIntent para hacer el llamado.
     */
    public static final String EXTRA_CUSTOM_TABS_INTENT = "customTabsIntent";

    /**
     * Nombre del parámetro con el CustomTabsIntent para hacer el llamado.
     */
    public static final String KEY_PROCESO_INICIADO = "procesoIniciado";

    /**
     * El intent para abrir custom tabs.
     */
    private Intent customTabsIntent;

    /**
     * El indicador para conocer si ya se había iniciado el proceso.
     */
    private boolean procesoIniciado;

    /**
     * Lee los datos del estado del activity.
     * @param state el estado actual
     */
    private void extractState(final Bundle state) {
        if (state == null) {
            AppLog.warn(TAG, "No se recibió estado");
            finish();
        } else {
            customTabsIntent = state.getParcelable(EXTRA_CUSTOM_TABS_INTENT);
            procesoIniciado = state.getBoolean(KEY_PROCESO_INICIADO);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            extractState(getIntent().getExtras());
        } else {
            extractState(savedInstanceState);
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (procesoIniciado) {
            final Uri data = getIntent().getData();
            final Intent intent = new Intent();
            intent.setData(data);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            AppLog.debug(TAG, "Dirigiendo CustomTabs a %s", customTabsIntent.getData());
            startActivity(customTabsIntent);
            procesoIniciado = true;
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_PROCESO_INICIADO, procesoIniciado);
        outState.putParcelable(EXTRA_CUSTOM_TABS_INTENT, customTabsIntent);
    }

}
