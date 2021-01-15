package co.com.mirecarga.core.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Utilidad para validar campos requeridos.
 */
public final class Requerido {
    /**
     * Mensaje para textos requeridos.
     */
    public static final String MSG_REQUERIDO = "Requerido";

    /**
     * Constructor privado para evitar instancias.
     */
    private Requerido() {
        super();
    }

    /**
     * Muestra el error o lo restablece en el control.
     * @param control el control
     * @param ok false si se debe mostrar el mensaje
     */
    private static void setError(final TextView control, final boolean ok) {
        if (ok) {
            control.setError(null);
        } else {
            control.setError(MSG_REQUERIDO);
        }
    }

    /**
     * Verifica que el control tenga texto.
     * @param control el control
     * @return true si tiene datos
     */
    public static boolean verificar(final TextView control) {
        final boolean ok;
        if (control.getVisibility() == View.VISIBLE) {
            ok = !TextUtils.isEmpty(control.getText());
            setError(control, ok);
        } else {
            ok = true;
        }
        return ok;
    }

    /**
     * Verifica que el control tenga un elemento seleccionado.
     * @param control el control
     * @return true si tiene elemento seleccionado
     */
    public static boolean verificar(final Spinner control) {
        final boolean ok;
        if (control.getVisibility() == View.VISIBLE) {
            ok = control.getSelectedItemPosition() > 0;
            setError((TextView) control.getSelectedView(), ok);
        } else {
            ok = true;
        }
        return ok;
    }

    /**
     * Verifica una lista de controles.
     * @param controles la lista de controles
     * @return true si todos los controles tienen datos
     */
    public static boolean verificar(final View... controles) {
        boolean ok = true;
        for (final View control : controles) {
            if (control instanceof Spinner) {
                if (!verificar((Spinner) control)) {
                    ok = false;
                }
            } else if (control instanceof TextView && !verificar((TextView) control)) {
                ok = false;
            }
        }
        return ok;
    }
}
