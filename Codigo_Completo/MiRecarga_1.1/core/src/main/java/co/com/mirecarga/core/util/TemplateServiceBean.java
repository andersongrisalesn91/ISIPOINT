package co.com.mirecarga.core.util;

import android.content.res.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;

/**
 * Lógica para remplazar tokens en una plantilla.
 */
public class TemplateServiceBean implements TemplateService {
    /**
     * El contexto de recursos de la aplicación.
     */
    private final transient Resources resources;

    /**
     * Inicializa las propiedades.
     * @param resources el contexto de recursos de la aplicación
     */
    @Inject
    public TemplateServiceBean(final Resources resources) {
        this.resources = resources;
    }

    /**
     * Remplaza todas las ocurrencias en el StringBuilder.
     * @param buffer el buffer con los datos
     * @param buscar la cadena a buscar
     * @param remplazar la cadena a remplazar
     */
    public static void replaceAll(final StringBuilder buffer, final String buscar,
                                  final  String remplazar) {
        int index = -1;
        do {
            index = buffer.indexOf(buscar, index);
            if (index != -1) {
                buffer.replace(index, index + buscar.length(), remplazar);
            }
        } while (index != -1);
    }

    @Override
    public final String remplazar(final int idRecurso, final Map<String, String> valores) {
        try (BufferedSource configSource =
                     Okio.buffer(Okio.source(resources.openRawResource(idRecurso)))) {
            final Buffer configData = new Buffer();
            configSource.readAll(configData);
            final StringBuilder buffer = new StringBuilder(configData.readString(StandardCharsets.UTF_8));
            for (final Entry<String, String> entry : valores.entrySet()) {
                replaceAll(buffer, '$' + entry.getKey(), entry.getValue());
            }
            return buffer.toString();
        } catch (final IOException ex) {
            throw new AppException(ex, "Error al leer el archivo %s", idRecurso);
        }
    }

}
