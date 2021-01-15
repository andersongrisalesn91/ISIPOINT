package co.com.mirecarga.core.mapa;

/**
 * Llave de la zona.
 */
public class ZonaPk {
    /**
     * Id del país.
     */
    private int idPais;
    /**
     * Id del Departamento.
     */
    private int idDepartamento;
    /**
     * Id del Municipio.
     */
    private int idMunicipio;
    /**
     * Id de la Zona.
     */
    private int id;

    /**
     * Regresa el campo idPais.
     *
     * @return el valor de idPais
     */
    public int getIdPais() {
        return idPais;
    }

    /**
     * Establece el valor del campo idPais.
     *
     * @param idPais el valor a establecer
     */
    public void setIdPais(final int idPais) {
        this.idPais = idPais;
    }

    /**
     * Regresa el campo idDepartamento.
     *
     * @return el valor de idDepartamento
     */
    public int getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * Establece el valor del campo idDepartamento.
     *
     * @param idDepartamento el valor a establecer
     */
    public void setIdDepartamento(final int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * Regresa el campo idMunicipio.
     *
     * @return el valor de idMunicipio
     */
    public int getIdMunicipio() {
        return idMunicipio;
    }

    /**
     * Establece el valor del campo idMunicipio.
     *
     * @param idMunicipio el valor a establecer
     */
    public void setIdMunicipio(final int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

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
}
