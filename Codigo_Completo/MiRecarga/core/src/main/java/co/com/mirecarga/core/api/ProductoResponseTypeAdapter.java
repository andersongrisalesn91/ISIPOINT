package co.com.mirecarga.core.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import static com.google.gson.stream.JsonToken.END_OBJECT;
import static com.google.gson.stream.JsonToken.NULL;

/**
 * Conversor de String a ProductoResponse para Gson, debido a que la respuesta del API es un map.
 */
public class ProductoResponseTypeAdapter extends TypeAdapter<ProductoResponse> {
    @Override
    public final void write(final JsonWriter out, final ProductoResponse value) throws IOException {
        if (value == null) {
            // nulls must be written
            out.nullValue();
        } else {
            out.beginObject();
            for (final Producto producto : value) {
                out.name(String.valueOf(producto.getId()));
                out.value(producto.getNombre());
                out.endObject();
            }
            out.endObject();
        }
    }

    /**
     * Lee el producto a partir de la entrada.
     * @param in la entrada
     * @return los datos del producto
     * @throws IOException si ocurrió error al leer
     */
    private Producto getProducto(final JsonReader in) throws IOException {
        final Producto producto = new Producto();
        final String id = in.nextName();
        final String nombre = in.nextString();
        producto.setId(Integer.valueOf(id));
        producto.setNombre(nombre);
        return producto;
    }

    @Override
    public final ProductoResponse read(final JsonReader in) throws IOException {
        final ProductoResponse resp;
        if (in.peek() == NULL) {
            resp = null;
        } else {
            resp = new ProductoResponse();
            in.beginObject();
            while (in.peek() != END_OBJECT) {
                resp.add(getProducto(in));
            }
            in.endObject();
        }
        return resp;
    }
}
