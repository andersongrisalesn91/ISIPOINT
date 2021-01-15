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

import javax.inject.Inject;

import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppLog;

/**
 * Lógica de envío a impresora Bluetooth.
 */
public class ImpresoraZebra extends AbstractImpresora {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ImpresoraZebra";
    /**
     * La dirección MAC configurada.
     */
    private final transient String macAddress;

    /**
     * Constructor con las propiedades.
     * @param fragment  el fragmento para mostrar mensajes
     * @param macAddress la dirección MAC configurada
     */
    @Inject
    public ImpresoraZebra(final AbstractAppFragment fragment, final String macAddress) {
        super(fragment);
        this.macAddress = macAddress;
    }

    /**
     * Imprime el texto de acuerdo a la configuración de la impresora.
     * @param texto el texto a imprimir
     */
    @SuppressWarnings("PMD.CloseResource")
    @Override
    public final void imprimir(final String texto, final String qrcode) {
        //Looper.prepare();
        Connection connection = null;
        try {
            connection = getConnection();
            imprimir(connection, texto, qrcode);
            AppLog.warn(TAG, "Impresión finalizada...");
        } catch (final Exception e) {
            error(e);
        } finally {
            disconnect(connection);
        }
        //Looper.loop();
    }

    /**
     * Se conecta a la impresora.
     * @return la conexión
     * @throws ConnectionException si no se pudo conectar
     */
    private Connection getConnection() throws ConnectionException {
        final Connection connection = new BluetoothConnection(macAddress);
        informativo("Conectando a impresora...");
        connection.open();
        return connection;
    }

    /**
     * Busca la impresora.
     * @param connection la conexión
     * @return la impresora
     * @throws ConnectionException error de conexión
     */
    private ZebraPrinter getPrinter(final Connection connection)
            throws ConnectionException {
        ZebraPrinter printer = null;
        try {
            printer = ZebraPrinterFactory.getInstance(connection);
            informativo("Verificando impresora");
            final String pl = SGD.GET("device.languages", connection);
            informativo("Impresora tipo: " + pl);
        } catch (final ZebraPrinterLanguageUnknownException e) {
            error(e);
        }
        return printer;
    }

    /**
     * Se desconecta de la impresora.
     * @param connection la conexión
     */
    private void disconnect(final Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (final ConnectionException e) {
            error(e);
        }
    }

    /**
     * La impresora requiere \r\n como fin de línea.
     * @param texto el texto a imprimir
     * @return el texto homologado
     */
    private String textoImprimir(final String texto) {
        return texto.replaceAll("\r\n", "\n").replaceAll("\n", "\r\n");
    }

    /**
     * Envía el código QR a la impresora.
     * @param connection la conexión
     * @param qrcode el texto a imprimir en en QR
     * @throws ConnectionException error de conexión
     */
    private void imprimirQr(final Connection connection,  final String qrcode)
            throws ConnectionException {
        if (!TextUtils.isEmpty(qrcode)) {
            AppLog.debug(TAG, "Imprimiendo qrcode %s", qrcode);
            final StringBuilder buffer = new StringBuilder();
            buffer.append("! 0 200 200 460 1\r\nPAGE-WIDTH 360\r\nB QR 10 100 M 2 U 10\r\nMA,");
            buffer.append(qrcode);
            buffer.append("\r\nENDQR\r\nPRINT\r\n");
            connection.write(buffer.toString().getBytes(StandardCharsets.ISO_8859_1));
            informativo("Enviando QR");
        }
    }

    /**
     * Envía los datos efectivamente a la impresora.
     * @param connection la conexión
     * @param texto el texto a imprimir
     * @param qrcode el texto a imprimir en en QR
     * @throws ConnectionException error de conexión
     */
    private void imprimir(final Connection connection, final String texto, final String qrcode)
            throws ConnectionException {
        informativo("Imprimiendo");
        final ZebraPrinter printer = getPrinter(connection);
        AppLog.warn(TAG, "Lee impresora... %s", printer);
        if (printer == null) {
            error("No se encotró la impresora");
        } else {
            final ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);
            final PrinterStatus printerStatus;
            if (linkOsPrinter == null) {
                printerStatus = printer.getCurrentStatus();
            } else {
                printerStatus = linkOsPrinter.getCurrentStatus();
            }
            if (printerStatus.isReadyToPrint) {
                final byte[] bytes = textoImprimir(texto).getBytes(StandardCharsets.ISO_8859_1);
                connection.write(bytes);
                informativo("Enviando datos a impresora");
                imprimirQr(connection, qrcode);
            }
            if (printerStatus.isHeadOpen) {
                error("La impresora está abierta");
            } else if (printerStatus.isPaused) {
                error("La impresora está pausada");
            } else if (printerStatus.isPaperOut) {
                error("No hay papel");
            }
        }
    }

}
