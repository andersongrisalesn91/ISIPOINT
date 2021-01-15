package co.com.mirecarga.core.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilidad para validación de campos.
 */
public class Validador {
    /**
     * Estado inicial de la validacíón.
     */
    private transient boolean valido = true;

    /**
     * Lista de validados previamente verificados.
     */
    private final transient Set<View> validados = new HashSet<>();

    /**
     * Patrón para verificar correos electrónicos.
     */
    private final transient Pattern correoPattern = Pattern
            .compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    /**
     * Patrón para verificar celulares.
     */
    private final transient Pattern celularPattern = Pattern .compile("^3\\d{9}$");

    /**
     * Regresa el campo valido.
     *
     * @return el valor de valido
     */
    public final boolean isValido() {
        return valido;
    }

    /**
     * Borra los mensajes del control.
     * @param control el control
     */
    public final void limpiar(final TextView control) {
        if (!validados.contains(control)) {
            control.setError(null);
        }
        validados.add(control);
    }

    /**
     * Verifica que sea un número de celular.
     * @param control el control
     * @return true si cumple la regla
     */
    public final boolean celular(final TextView control) {
        limpiar(control);
        boolean ok = true;
        if (!TextUtils.isEmpty(control.getText())) {
            final Matcher matcher = celularPattern.matcher(control.getText());
            ok = matcher.matches();
            if (!ok) {
                valido = false;
                control.setError("Debe ser un número de celular");
            }
        }
        return ok;
    }

    /**
     * Verifica que sea un número de celular.
     * @param control el control
     * @return true si cumple la regla
     */
    public final boolean correo(final TextView control) {
        limpiar(control);
        boolean ok = true;
        if (!TextUtils.isEmpty(control.getText())) {
            final Matcher matcher = correoPattern.matcher(control.getText());
            ok = matcher.matches();
            if (!ok) {
                valido = false;
                control.setError("Debe ser un correo válido");
            }
        }
        return ok;
    }

    /**
     * Verifica que el control tenga texto.
     * @param control el control
     * @return true si tiene datos
     */
    public final boolean requerido(final TextView control) {
        limpiar(control);
        final boolean ok = Requerido.verificar(control);
        if (!ok) {
            valido = false;
        }
        return ok;
    }

    /**
     * Verifica que el control tenga un elemento seleccionado.
     * @param control el control
     * @return true si tiene elemento seleccionado
     */
    public final boolean requerido(final Spinner control) {
        final boolean ok = Requerido.verificar(control);
        if (!ok) {
            valido = false;
        }
        return ok;
    }

    /**
     * Verifica una lista de validados.
     * @param controles la lista de validados
     * @return true si todos los validados tienen datos
     */
    public boolean requerido(final View... controles) {
        for (final View control : controles) {
            if (control instanceof TextView) {
                limpiar((TextView) control);
            }
        }
        final boolean ok = Requerido.verificar(controles);
        if (!ok) {
            valido = false;
        }
        return ok;
    }

    /**
     * Verifica que el control tenga texto.
     * @param control el control
     * @param rango el rango de valores que debe cumplir
     * @return true si cumple la regla
     */
    public final boolean rango(final TextView control, final RangoValores rango) {
        limpiar(control);
        boolean ok = true;
        if (!TextUtils.isEmpty(control.getText())) {
            try {
                final double valor = Integer.parseInt(control.getText().toString());
                if (valor < rango.getMinimo()) {
                    ok = false;
                    control.setError(String.format("Debe ser mínimo %s", rango.getMinimo()));
                }
                if (valor > rango.getMaximo()) {
                    ok = false;
                    control.setError(String.format("Debe ser máximo %s", rango.getMaximo()));
                }
            } catch (final NumberFormatException e) {
                ok = false;
                control.setError("Debe ser un número válido");
            }
            if (!ok) {
                valido = false;
            }
        }
        return ok;
    }
}
