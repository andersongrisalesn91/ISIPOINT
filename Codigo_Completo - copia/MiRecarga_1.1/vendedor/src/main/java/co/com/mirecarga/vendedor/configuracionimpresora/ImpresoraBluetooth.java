package co.com.mirecarga.vendedor.configuracionimpresora;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import android_serialport_api.DataUtils;
import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.util.AppException;
import co.com.mirecarga.core.util.AppLog;

/**
 * Lógica de envío a impresora Bluetooth.
 */
public class ImpresoraBluetooth extends AbstractImpresora {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ImpresoraBluetooth";
    /**
     * El nombre configurado.
     */
    private final transient String nombre;

    /**
     * Constructor con las propiedades.
     * @param fragment  el fragmento para mostrar mensajes
     * @param nombre el nombre configurado
     */
    @Inject
    public ImpresoraBluetooth(final AbstractAppFragment fragment, final String nombre) {
        super(fragment);
        this.nombre = nombre;
    }

    /**
     * Obtiene el dispositivo de la impresora.
     * @return el dispositivo
     */
    private BluetoothDevice getBluetoothDevice() {
        BluetoothDevice bluetoothDevice = null;
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if (pairedDevices.isEmpty())
            {
                error("No se encontraron impresoras");
            }
            else
            {
                for (BluetoothDevice device : pairedDevices)
                {
                    if (device.getName().equals(nombre))
                    {
                        bluetoothDevice = device;
                        break;
                    }
                }

                if (bluetoothDevice == null) {
                    error("No se encontró la impresora '%s'", nombre);
                }
            }
        } else {
            error("El Bluetooth no está activo");
        }
        return bluetoothDevice;
    }

    /**
     * Imprime el texto de acuerdo a la configuración de la impresora.
     * @param texto el texto a imprimir
     */
    @SuppressWarnings("PMD.CloseResource")
    @Override
    public final void imprimir(final String texto, final String qrcode) {
        try {
            BluetoothDevice bluetoothDevice = getBluetoothDevice();
            if (bluetoothDevice != null) {
                imprimir(bluetoothDevice, texto, qrcode);
            }
            AppLog.warn(TAG, "Impresión finalizada...");
        } catch (final Exception e) {
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
     * @param outputStream la conexión
     * @param qrcode el texto a imprimir en en QR
     * @throws IOException error de IO
     */
    private void imprimirQr(final OutputStream outputStream,  String qrcode)
            throws IOException {
        if (!TextUtils.isEmpty(qrcode)) {
            AppLog.debug(TAG, "Imprimiendo qrcode %s", qrcode);
            final byte[] contents = qrcode.getBytes(StandardCharsets.ISO_8859_1);
            byte[] formats  = {(byte) 0x1b, (byte) 0x5a, (byte) 0x00, (byte) 0x03, (byte) 0x08, (byte) contents.length, (byte) 0x00};
            outputStream.write(formats);
            outputStream.write(contents);
            //outputStream.write((byte) 0x00);

            informativo("Enviando QR");
        }
    }

    /**
     * Crea el socket.
     * @param bluetoothDevice el dispositivo
     * @return el socket
     */
    private BluetoothSocket getBluetoothSocket(final BluetoothDevice bluetoothDevice) {
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            BluetoothSocket socket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
            socket.connect();
            return socket;
        } catch (NoSuchMethodException | IllegalAccessException | IOException | InvocationTargetException e) {
            throw new AppException(e, "Error al obtener el socket de la impresora");
        }
    }

    /**
     * Envía los datos efectivamente a la impresora.
     * @param bluetoothDevice la conexión
     * @param texto el texto a imprimir
     * @param qrcode el texto a imprimir en en QR
     * @throws NoSuchMethodException error de conexión
     */
    private void imprimir(final BluetoothDevice bluetoothDevice, final String texto, final String qrcode) throws NoSuchMethodException {
        informativo("Imprimiendo");
        try (BluetoothSocket socket = getBluetoothSocket(bluetoothDevice);
             OutputStream outputStream = socket.getOutputStream()) {
            outputStream.write(textoImprimir(texto).getBytes(StandardCharsets.ISO_8859_1));
            informativo("Enviando datos a impresora");
            imprimirQr(outputStream, qrcode);
        } catch (IOException e) {
            throw new AppException(e, "Error al imprimir");
        }
    }

}
