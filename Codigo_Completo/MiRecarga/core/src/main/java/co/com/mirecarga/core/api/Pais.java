package co.com.mirecarga.core.api;

/**
 * Pojo para la lista de paises.
 */

public class Pais {
    /**
     * El identificador del país.
     */
    private int id;
    /**
     * El nombre del país.
     */
    private String pais;

    /**
     * Regresa el campo id.
     *
     * @return el valor de id
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el valor del campo id.
     *
     * @param id el valor a establecer
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Regresa el campo pais.
     *
     * @return el valor de pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el valor del campo pais.
     *
     * @param pais el valor a establecer
     */
    public void setPais(final String pais) {
        this.pais = pais;
    }
}
