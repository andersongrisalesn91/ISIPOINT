package co.com.mirecarga.core.util;

import android.net.Uri;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Conversor de String a URI para Gson.
 */
public class UriTypeAdapter extends TypeAdapter<Uri> {
    @Override
    public final void write(final JsonWriter out, final Uri value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public final Uri read(final JsonReader in) throws IOException {
        return Uri.parse(in.nextString());
    }
}
