package co.com.mirecarga.core.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mensaje estándar de respuesta de los servicios.
 */
public class RespuestaMensaje {
    /**
     * Mensaje para el usuario final.
     */
    private final transient List<String> mensajes = new ArrayList<>();

    /**
     * Mensaje para el usuario final.
     *
     * @return El valor de la propiedad.
     */
    public final String getMensaje() {
        return getMensaje("\n");
    }

    /**
     * Mensaje para el usuario final.
     * @param separador el separador para unir varios mensajes
     * @return El valor de la propiedad.
     */
    public final String getMensaje(final String separador) {
        return TextUtils.join(separador, mensajes);
    }

    /**
     * Mensaje para el usuario final.
     *
     * @param mensaje El valor de la propiedad.
     */
    public final void setMensaje(final String mensaje) {
        this.mensajes.clear();
        this.mensajes.add(mensaje);
    }

    /**
     * Constructor con todos los campos del objeto.
     *
     * @param mensaje Mensaje para el usuario final.
     */
    public RespuestaMensaje(final String mensaje) {
        super();
        setMensaje(mensaje);
    }

    /**
     * Indica si el resultado es sin errores.
     *
     * @return El valor de la propiedad.
     */
    public final boolean isOk() {
        return mensajes.isEmpty();
    }

    /**
     * Constructor vacío.
     *
     */
    public RespuestaMensaje() {
        super();
    }

    /**
     * Constructor para excepciones.
     * @param ex la Excepción
     */
    public RespuestaMensaje(final Exception ex) {
        super();
        setMensaje(ex.getMessage());
    }

    /**
     * Agrega el mensaje a la cadena actual.
     * @param mensaje el mensaje.
     */
    public final void agregarMensaje(final String mensaje) {
        if (!TextUtils.isEmpty(mensaje)) {
            mensajes.add(mensaje);
        }
    }

    /**
     * Agrega el mensaje a la cadena actual.
     * @param mensaje el mensaje.
     * @param args los argumentos para String.format.
     */
    public final void agregarMensaje(final String mensaje, final Object... args) {
        agregarMensaje(String.format(mensaje, args));
    }

    /**
     * Agrega el mensaje a la cadena actual.
     * @param mensaje el mensaje.
     */
    public final void agregarMensaje(final RespuestaMensaje mensaje) {
        agregarMensaje(mensaje.getMensaje());
    }

    /**
     * Agrega el mensaje obligatorio a la cadena actual.
     * @param campo el campo.
     */
    public final void agregarMensajeObligatorio(final String campo) {
        if (!TextUtils.isEmpty(campo)) {
            agregarMensaje("El campo %s es obligatorio", campo);
        }
    }

    /**
     * Regresa todos los mensajes, no lo hace con get para no serializar.
     * @return los mensajes
     */
    public final List<String> mensajesDetallado() {
        return mensajes;
    }
}
