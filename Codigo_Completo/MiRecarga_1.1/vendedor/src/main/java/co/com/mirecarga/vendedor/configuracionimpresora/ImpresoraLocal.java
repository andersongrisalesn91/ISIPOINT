package co.com.mirecarga.vendedor.configuracionimpresora;

import android.text.TextUtils;

import javax.inject.Inject;

import android_serialport_api.PrinterAPI;
import android_serialport_api.PrinterAPI.PrinterStatusListener;
import android_serialport_api.SerialPortManager;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;

/**
 * Lógica de envío a impresora local.
 */
public class ImpresoraLocal extends AbstractImpresora implements PrinterStatusListener {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ImpresoraLocal";

    /**
     * Constructor con las propiedades.
     * @param fragment  el fragmento para mostrar mensajes
     */
    @Inject
    public ImpresoraLocal(final AbstractAppFragment fragment) {
        super(fragment);
    }

    @Override
    public final void imprimir(final String texto, final String qrcode) {
        try {
            //Looper.prepare();
            AppLog.debug(TAG, "Identificando impresora local");
            final SerialPortManager instance = SerialPortManager.getInstance();
            AppLog.debug(TAG, "Identificando impresora local isPrintOpen");
            boolean printOpen = instance.isPrintOpen();
            if (!printOpen) {
                AppLog.debug(TAG, "Identificando impresora local openSerialPortPrinter");
                printOpen = instance.openSerialPortPrinter();
                if (!printOpen) {
                    informativo("No se pudo abrir la impresora local...");
                }
            }
            if (printOpen) {
                AppLog.debug(TAG, "Iniciando impresora local");
                final PrinterAPI api = new PrinterAPI();
                AppLog.debug(TAG, "Iniciando impresora local2");
                api.initPrint(this);
                AppLog.debug(TAG, "Iniciando impresora local2 largo");
                final int len = texto.getBytes("GBK").length;
                AppLog.debug(TAG, "Imprimendo longitud %s", len);
                api.printPaper(texto, this);
                if (!TextUtils.isEmpty(qrcode)) {
                    AppLog.debug(TAG, "Imprimiendo qrcode %s", qrcode);
                    api.printQrcode1(qrcode, 1, this);
                }
                AppLog.debug(TAG, "Impresión local finalizada...");
            }
        } catch (final Exception e) {
            error(e);
        }
        //Looper.loop();
    }

    @Override
    public final void work() {

    }

    @Override
    public final void hot() {

    }

    @Override
    public final void noPaper() {
        informativo("No hay papel en la impresora");
    }

    @Override
    public final void end() {

    }
}
