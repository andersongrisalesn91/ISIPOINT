package co.com.mirecarga.core.util;

import org.osmdroid.util.BoundingBox;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilidades para IO.
 */
public final class IOUtil {

    /**
     * Constructor privado para evitar instancias.
     */
    private IOUtil() {
        // Constructor privado para evitar instancias
    }

    /**
     * Convierte el inputstream a bytes.
     * @param is el inputstream
     * @return los bytes leídos
     * @throws IOException en caso de error
     */
    public static byte[] toByteArray(final InputStream is)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = is.read();

        while (reads != -1){
            baos.write(reads);
            reads = is.read();
        }

        return baos.toByteArray();
    }

    /**
     * Copia el stream a un archivo.
     * @param inputStream el stream
     * @param file el archivo
     * @throws IOException si hubo errores
     */
    public static void copyInputStreamToFile(final InputStream inputStream, final File file)
            throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }
}
