package co.com.mirecarga.vendedor.api;

/**
 * Datos de la tarjeta.
 */
public class TarjetaPrepago {
    /**
     * El id de la tarjeta.
     */
    private int id;
    /**
     * El id del establecimiento.
     */
    private int idec;
    /**
     * El id del producto.
     */
    private int idproducto;
    /**
     * El nombre del producto.
     */
    private String producto;
    /**
     * El serial.
     */
    private String serial;

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
     * Regresa el campo idec.
     *
     * @return el valor de idec
     */
    public int getIdec() {
        return idec;
    }

    /**
     * Establece el valor del campo idec.
     *
     * @param idec el valor a establecer
     */
    public void setIdec(final int idec) {
        this.idec = idec;
    }

    /**
     * Regresa el campo idproducto.
     *
     * @return el valor de idproducto
     */
    public int getIdproducto() {
        return idproducto;
    }

    /**
     * Establece el valor del campo idproducto.
     *
     * @param idproducto el valor a establecer
     */
    public void setIdproducto(final int idproducto) {
        this.idproducto = idproducto;
    }

    /**
     * Regresa el campo producto.
     *
     * @return el valor de producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Establece el valor del campo producto.
     *
     * @param producto el valor a establecer
     */
    public void setProducto(final String producto) {
        this.producto = producto;
    }

    /**
     * Regresa el campo serial.
     *
     * @return el valor de serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Establece el valor del campo serial.
     *
     * @param serial el valor a establecer
     */
    public void setSerial(final String serial) {
        this.serial = serial;
    }
}
