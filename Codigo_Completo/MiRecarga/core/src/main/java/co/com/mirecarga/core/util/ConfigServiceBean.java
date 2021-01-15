package co.com.mirecarga.core.util;

import android.content.res.Resources;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;

/**
 * Servicio que lee la configuración de la aplicación desde un archivo Json.
 * @param <T> el tipo de datos de la configuración
 */
public class ConfigServiceBean<T> implements ConfigService<T> {
    /**
     * El tag para el log.
     */
    private static final String TAG = "ConfigServiceBean";

    /**
     * El tipo de dato de la configuración.
     */
    private final transient Class<T> tipo;

    /**
     * El contexto de recursos de la aplicación.
     */
    private final transient Resources resources;

    /**
     * El id del recurso que debe ser leído.
     */
    private final transient int idRecurso;

    /**
     * Configuración en memoria.
     */
    private transient T config;

    /**
     * Inicializa las propiedades.
     * @param tipo el tipo de dato de la configuración
     * @param resources el contexto de recursos de la aplicación
     * @param idRecurso el id del recurso que debe ser leído
     */
    public ConfigServiceBean(final Class<T> tipo, final Resources resources, final int idRecurso) {
        this.tipo = tipo;
        this.resources = resources;
        this.idRecurso = idRecurso;
    }

    /**
     * Lee la configuración desde el archivo Json.
     * @return los datos de la configuración
     */
    private T leerConfig() {
        AppLog.debug(TAG, "Leyendo configuración desde archivo %s", idRecurso);
        try (BufferedSource configSource =
                     Okio.buffer(Okio.source(resources.openRawResource(idRecurso)))) {
            final Buffer configData = new Buffer();
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Uri.class, new UriTypeAdapter());
            final Gson gson = gsonBuilder.create();
            configSource.readAll(configData);
            final String texto = configData.readString(StandardCharsets.UTF_8);
            return gson.fromJson(texto, tipo);
        } catch (final IOException ex) {
            throw new AppException(ex, "Error al leer la configuración");
        }
    }

    @Override
    public final T getConfig() {
        if (config == null) {
            config = leerConfig();
        }
        return config;
    }
}
