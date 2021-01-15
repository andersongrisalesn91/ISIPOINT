package co.com.mirecarga.core.util;

import java.util.Map;

/**
 * Contrato para remplazar tokens en una plantilla.
 */
public interface TemplateService {
    /**
     * Remplaza las variables en la plantilla.
     * @param idRecurso el id del recurso que debe ser leído
     * @param valores los valores a remplazar
     * @return el texto al remplazar las variables
     */
    String remplazar(int idRecurso, Map<String, String> valores);
}
