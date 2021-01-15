package co.com.mirecarga.cliente.placaadmin;

/**
 * Contrato para el servicio de administración de placas del cliente.
 */
public interface PlacaAdminService {

    /**
     * Regresa el campo editando.
     *
     * @return el valor de editando
     */
    boolean isEditando();

    /**
     * Establece el valor del campo editando.
     *
     * @param editando el valor a establecer
     */
    void setEditando(boolean editando);

    /**
     * Regresa el campo idPlacaEditar.
     *
     * @return el valor de idPlacaEditar
     */
    int getIdPlacaEditar();
    /**
     * Establece el valor del campo idPlacaEditar.
     *
     * @param idPlacaEditar el valor a establecer
     */
    void setIdPlacaEditar(int idPlacaEditar);
}
