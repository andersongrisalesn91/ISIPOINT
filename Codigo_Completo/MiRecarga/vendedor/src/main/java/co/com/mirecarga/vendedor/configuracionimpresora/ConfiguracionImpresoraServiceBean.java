package co.com.mirecarga.vendedor.configuracionimpresora;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import co.com.mirecarga.core.util.Constantes;
import co.com.mirecarga.core.util.RespuestaMensaje;

/**
 * Servicio de configuración de impresora para el vendedor.
 */
public class ConfiguracionImpresoraServiceBean implements ConfiguracionImpresoraService {
    /**
     * Preferencia tipo impresora.
     */
    public static final String TIPO_IMPRESORA_PREFERENCIA = "tipoImpresora";

    /**
     * Preferencia dirección MAC.
     */
    public static final String IMPRESORA_DIRECCION_MAC_PREFERENCIA = "macImpresoraBluetooth";

    /**
     * Preferencia dirección MAC.
     */
    public static final String IMPRESORA_NOMBRE_PREFERENCIA = "nombreImpresoraBluetooth";

    /**
     * Impresora local.
     */
    public static final String TIPO_IMPRESORA_LOCAL = "Local";
    /**
     * Impresora bluetooth.
     */
    public static final String TIPO_IMPRESORA_BLUETOOTH = "Bluetooth";
    /**
     * Impresora zebra.
     */
    public static final String TIPO_IMPRESORA_ZEBRA = "Zebra";

    /**
     * El contexto de la aplicación.
     */
    private final transient Context context;

    /**
     * Constructor con las dependencias.
     * @param context el contexto de la aplicación
     */
    @Inject
    public ConfiguracionImpresoraServiceBean(final Context context) {
        this.context = context;
    }

    @Override
    public final RespuestaConfiguracionImpresora consultarModelo() {
        final RespuestaConfiguracionImpresora resp = new RespuestaConfiguracionImpresora();
        final SharedPreferences preferencias = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE);
        if (preferencias != null) {
            final String tipoImpresora = preferencias.getString(TIPO_IMPRESORA_PREFERENCIA, "");
            final String macImpresoraBluetooth = preferencias.getString(IMPRESORA_DIRECCION_MAC_PREFERENCIA, "");
            final String nombreImpresoraBluetooth = preferencias.getString(IMPRESORA_NOMBRE_PREFERENCIA, "BlueTooth Printer");
            resp.setTipoImpresora(tipoImpresora);
            resp.setMacImpresoraBluetooth(macImpresoraBluetooth);
            resp.setNombreImpresoraBluetooth(nombreImpresoraBluetooth);
        }
        return resp;
    }

    @Override
    public final RespuestaMensaje actualizarPreferencia(final String llave, final String valor) {
        final RespuestaMensaje resp = new RespuestaMensaje();
        final SharedPreferences preferencias = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(llave, valor);
        editor.apply();
        return resp;
    }
}
