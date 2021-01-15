package co.com.mirecarga.core.util;

/**
 * Excepción general para el sistema.
 *
 * @author IT Agil
 */
public class AppException extends RuntimeException {
    /**
     * Identficador de versión para la clase serializable.
     */
    private static final long serialVersionUID = -9123991775015779892L;

    /**
     * Construye una nueva excepción con el mensaje especificado.
     *
     * @param msg el mensaje
     */
    public AppException(final String msg) {
        super(msg);
    }

    /**
     * Construye una nueva excepción con el mensaje especificado y los argumentos variables se pasan
     * a un String.format.
     *
     * @param msg el mensaje
     * @param args argumentos variables para String.format.
     */
    public AppException(final String msg, final Object... args) {
        super(String.format(msg, args));
    }

    /**
     * Construye una nueva excepción con el mensaje y causa especificados.
     *
     * @param cause la causa
     * @param msg el mensaje
     */
    public AppException(final Throwable cause, final String msg) {
        super(msg, cause);
    }

    /**
     * Construye una nueva excepción con el mensaje y causa especificados. Los argumentos variables
     * se pasan a un String.format.
     *
     * @param cause la causa
     * @param msg el mensaje
     * @param args argumentos variables para String.format.
     */
    public AppException(final Throwable cause, final String msg, final Object... args) {
        super(String.format(msg, args), cause);
    }

}
