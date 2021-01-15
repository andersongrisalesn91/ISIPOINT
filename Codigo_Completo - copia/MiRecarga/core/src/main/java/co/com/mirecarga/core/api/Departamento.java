package co.com.mirecarga.core.api;

/**
 * POJO para la lista de departamentos.
 */

public class Departamento extends DatosBoundingBox {
    /**
     * El identificador del departmaento.
     */
    private int id;
    /**
     * El identificador del país.
     */
    private int idpais;
    /**
     * El nombre del departamento.
     */
    private String departamento;

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
     * Regresa el campo departamento.
     *
     * @return el valor de departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Establece el valor del campo departamento.
     *
     * @param departamento el valor a establecer
     */
    public void setDepartamento(final String departamento) {
        this.departamento = departamento;
    }
}
