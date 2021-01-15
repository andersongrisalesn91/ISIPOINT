package co.com.mirecarga.vendedor.configuracionimpresora;

import android.text.TextUtils;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppFormatterService;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.TemplateService;

/**
 * Lógica de envío a impresora Bluetooth.
 */
public class ImpresoraServiceBean  implements ImpresoraService {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ImpresoraServiceBean";

    /**
     * El servicio de configuración de impresoras.
     */
    private final transient ConfiguracionImpresoraService configuracionImpresoraService;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    private final transient ExecutorService executor;

    /**
     * Constructor con las dependencias.
     * @param configuracionImpresoraService el servicio de configuración de impresoras
     * @param executor el Servicio de ejecución de código asíncrono
     */
    @Inject
    public ImpresoraServiceBean(final ConfiguracionImpresoraService configuracionImpresoraService,
                                final ExecutorService executor) {
        this.configuracionImpresoraService = configuracionImpresoraService;
        this.executor = executor;
    }

    @Override
    public final void imprimir(final AbstractAppFragment fragment, final String texto, final String qrcode) {
        final RespuestaConfiguracionImpresora configImpresora = configuracionImpresoraService.consultarModelo();
        AppLog.debug(TAG, "Impresora local qrcode %s", qrcode);
        if (configImpresora.isImpresoraLocal()) {
            AppLog.debug(TAG, "Impresora local");
            final ImpresoraLocal impLocal = new ImpresoraLocal(fragment);
            impLocal.imprimir(texto, qrcode);
        } else if (configImpresora.isImpresoraBluetooth()) {
            final String nombre = configImpresora.getNombreImpresoraBluetooth();
            AppLog.debug(TAG, "Impresora bluetooth %s", nombre);
            final ImpresoraBluetooth imp = new ImpresoraBluetooth(fragment, nombre);
            executor.submit(() -> imp.imprimir(texto, qrcode));
        } else if (configImpresora.isImpresoraZebra()) {
            final String mac = configImpresora.getMacImpresoraBluetooth();
            AppLog.debug(TAG, "Impresora zebra %s", mac);
            final ImpresoraZebra imp = new ImpresoraZebra(fragment, mac);
            executor.submit(() -> imp.imprimir(texto, qrcode));
        }
    }
}
