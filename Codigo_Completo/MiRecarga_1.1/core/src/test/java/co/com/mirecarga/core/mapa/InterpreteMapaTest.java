package co.com.mirecarga.core.mapa;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Prueba el intérprete del mapa.
 */
public class InterpreteMapaTest {
    /**
     * Description a usar en las pruebas.
     */
    private String description = "<h4>parqueaderos_y_zonas_poi_2</h4>\n" +
            "\n" +
            "<ul class=\"textattributes\">\n" +
            "  <li><strong><span class=\"atr-name\">id</span>:</strong> <span class=\"atr-value\">2</span></li>\n" +
            "  <li><strong><span class=\"atr-name\">zonas</span>:</strong> <span class=\"atr-value\">Usaquen 2</span></li>\n" +
            "  <li><strong><span class=\"atr-name\">id_pais</span>:</strong> <span class=\"atr-value\">1</span></li>\n" +
            "  <li><strong><span class=\"atr-name\">id_departamento</span>:</strong> <span class=\"atr-value\">3</span></li>\n" +
            "  <li><strong><span class=\"atr-name\">id_municipio</span>:</strong> <span class=\"atr-value\">149</span></li>\n" +
            "  <li><strong><span class=\"atr-name\">id_estado</span>:</strong> <span class=\"atr-value\">1</span></li>\n" +
            "  \n" +
            "</ul>";

    @Test
    public void getValorTagInicial() {
        final InterpreteMapa bean = new InterpreteMapa();
        final String valor = bean.getValor(description, "id");
        assertEquals("2", valor);
    }

    @Test
    public void getValorTagIntermedio() {
        final InterpreteMapa bean = new InterpreteMapa();
        final String valor = bean.getValor(description, "zonas");
        assertEquals("Usaquen 2", valor);
    }

    @Test
    public void getValorTagFinal() {
        final InterpreteMapa bean = new InterpreteMapa();
        final String valor = bean.getValor(description, "id_estado");
        assertEquals("1", valor);
    }

    @Test
    public void getValorTagNotExiste() {
        final InterpreteMapa bean = new InterpreteMapa();
        final String valor = bean.getValor(description, "id_estado2");
        assertEquals("", valor);
    }
}