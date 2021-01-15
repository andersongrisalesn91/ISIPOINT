package co.com.mirecarga.core.api;

/**
 * POJO para la lista de municipios.
 */

public class Municipio extends DatosBoundingBox {
    /**
     * El identificador del departmaento.
     */
    private int id;
    /**
     * El identificador del país.
     */
    private int idpais;
    /**
     * El identificador del departamento.
     */
    private int iddepartamento;
    /**
     * El nombre del municipio.
     */
    private String municipio;

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
     * Regresa el campo idpais.
     *
     * @return el valor de idpais
     */
    public int getIdpais() {
        return idpais;
    }

    /**
     * Establece el valor del campo idpais.
     *
     * @param idpais el valor a establecer
     */
    public void setIdpais(final int idpais) {
        this.idpais = idpais;
    }

    /**
     * Regresa el campo iddepartamento.
     *
     * @return el valor de iddepartamento
     */
    public int getIddepartamento() {
        return iddepartamento;
    }

    /**
     * Establece el valor del campo iddepartamento.
     *
     * @param iddepartamento el valor a establecer
     */
    public void setIddepartamento(final int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    /**
     * Regresa el campo municipio.
     *
     * @return el valor de municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Establece el valor del campo municipio.
     *
     * @param municipio el valor a establecer
     */
    public void setMunicipio(final String municipio) {
        this.municipio = municipio;
    }
}
