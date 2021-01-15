package co.com.mirecarga.core.mapa;

/**
 * Código con la capacidad para interpretar los datos del mapa.
 */
public class InterpreteMapa {
    /**
     * Texto a buscar para identificar valores.
     */
    private static final String TOKEN_VALOR_INICIAL = "atr-value\">";

    /**
     * Texto a buscar para identificar valores.
     */
    private static final String TOKEN_VALOR_FINAL = "</span>";

    /**
     * Obtiene los datos a partir del elemento de descripción.
     * @param description los datos contenidos en la descripción
     * @param tag el tag a buscar
     * @return el valor a encontrar o cadena vacía si no se encuentra
     */
    public final String getValor(final String description, final String tag) {
        String valor = "";
        final int pos = description.indexOf(">" + tag + "<");
        if (pos >= 0) {
            final int posIni = description.indexOf(TOKEN_VALOR_INICIAL, pos);
            if (posIni >= 0) {
                final int posFin = description.indexOf(TOKEN_VALOR_FINAL, posIni);
                valor = description.substring(posIni + TOKEN_VALOR_INICIAL.length(), posFin);
            }
        }
        return valor;
    }
}
