package co.com.mirecarga.vendedor.offline;

import java.util.Map;

import co.com.mirecarga.vendedor.ventamapa.ConfirmarVentaMapaDatos;

/**
 * Datos para enviar al servicio ApiZERService.registrar.
 */
public class VentaDatos {
    /**
     * Dato para enviar al servicio.
     */
    private int idPais;
    /**
     * Dato para enviar al servicio.
     */
    private int idDepartamento;
    /**
     * Dato para enviar al servicio.
     */
    private int idMunicipio;
    /**
     * Dato para enviar al servicio.
     */
    private int idZonasYParqueaderos;
    /**
     * Dato para enviar al servicio.
     */
    private int idCelda;
    /**
     * Dato para enviar al servicio.
     */
    private Map<String, String> datos;

    /**
     * Los datos para auditoría.
     */
    private ConfirmarVentaMapaDatos confirmar;


    /**
     * Constructor con las propiedades.
     * @param idPais el valor de la propiedad
     * @param idDepartamento el valor de la propiedad
     * @param idMunicipio el valor de la propiedad
     * @param idZonasYParqueaderos el valor de la propiedad
     * @param idCelda el valor de la propiedad
     * @param datos el valor de la propiedad
     * @param confirmar el valor de la propiedad
     * @return el objeto
     */
    public static VentaDatos from(final int idPais, final int idDepartamento, final int idMunicipio,
                      final int idZonasYParqueaderos, final int idCelda,
                      final Map<String, String> datos, final ConfirmarVentaMapaDatos confirmar) {
        VentaDatos dto = new VentaDatos();
        dto.idPais = idPais;
        dto.idDepartamento = idDepartamento;
        dto.idMunicipio = idMunicipio;
        dto.idZonasYParqueaderos = idZonasYParqueaderos;
        dto.idCelda = idCelda;
        dto.datos = datos;
        dto.confirmar = confirmar;
        return dto;
    }

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
     * Regresa el campo idZonasYParqueaderos.
     *
     * @return el valor de idZonasYParqueaderos
     */
    public int getIdZonasYParqueaderos() {
        return idZonasYParqueaderos;
    }

    /**
     * Establece el valor del campo idZonasYParqueaderos.
     *
     * @param idZonasYParqueaderos el valor a establecer
     */
    public void setIdZonasYParqueaderos(final int idZonasYParqueaderos) {
        this.idZonasYParqueaderos = idZonasYParqueaderos;
    }

    /**
     * Regresa el campo idCelda.
     *
     * @return el valor de idCelda
     */
    public int getIdCelda() {
        return idCelda;
    }

    /**
     * Establece el valor del campo idCelda.
     *
     * @param idCelda el valor a establecer
     */
    public void setIdCelda(final int idCelda) {
        this.idCelda = idCelda;
    }

    /**
     * Regresa el campo datos.
     *
     * @return el valor de datos
     */
    public Map<String, String> getDatos() {
        return datos;
    }

    /**
     * Establece el valor del campo datos.
     *
     * @param datos el valor a establecer
     */
    public void setDatos(final Map<String, String> datos) {
        this.datos = datos;
    }

    /**
     * Regresa el campo confirmar.
     *
     * @return el valor de confirmar
     */
    public ConfirmarVentaMapaDatos getConfirmar() {
        return confirmar;
    }

    /**
     * Establece el valor del campo confirmar.
     *
     * @param confirmar el valor a establecer
     */
    public void setConfirmar(final ConfirmarVentaMapaDatos confirmar) {
        this.confirmar = confirmar;
    }
}
