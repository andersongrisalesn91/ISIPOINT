package co.com.mirecarga.cliente.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import co.com.mirecarga.core.util.AppLog;

/**
 * Activity para recibir la redirección desde CustomTabs como en Pagos.
 */
public class ClienteReceiverActivity extends Activity {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ClienteReceiverActivity";

    @Override
    public void onCreate(final Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        // while this does not appear to be achieving much, handling the redirect in this way
        // ensures that we can remove the browser tab from the back stack. See the documentation
        // on AuthorizationManagementActivity for more details.
        AppLog.debug(TAG, "Redirect Data %s", getIntent().getData());
        final Intent intent = new Intent(this, ClienteRouterActivity.class);
        intent.setData(getIntent().getData());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}
