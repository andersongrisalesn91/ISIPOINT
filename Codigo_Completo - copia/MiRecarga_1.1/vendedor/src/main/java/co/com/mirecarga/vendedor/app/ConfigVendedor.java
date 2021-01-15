package co.com.mirecarga.vendedor.app;

import java.util.List;

import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.mapa.ConfigMapa;
import co.com.mirecarga.core.util.IdDescripcion;
import co.com.mirecarga.core.util.RangoValores;

/**
 * Datos de configuración de la aplicación.
 */
public class ConfigVendedor {
    /**
     * Configuración para la autenticación con WSO2.
     */
    private ConfigWso2 wso2;

    /**
     * Datos de configuración de productos.
     */
    private ConfigProducto productos;

    /**
     * Configuración de la recarga en línea.
     */
    private RangoValores recargaEnLinea;

    /**
     * Id del cliente a usar en transacciones de tipo anónimo.
     */
    private int idClienteAnonimo;

    /**
     * Lista de  métodos de pago.
     */
    private List<IdDescripcion> metodosPago;

    /**
     * Configuración de la recarga en línea.
     */
    private ConfigMapa mapa;

    /**
     * Regresa el campo wso2.
     *
     * @return el valor de wso2
     */
    public ConfigWso2 getWso2() {
        return wso2;
    }

    /**
     * Establece el valor del campo wso2.
     *
     * @param wso2 el valor a establecer
     */
    public void setWso2(final ConfigWso2 wso2) {
        this.wso2 = wso2;
    }

    /**
     * Regresa el campo productos.
     *
     * @return el valor de productos
     */
    public ConfigProducto getProductos() {
        return productos;
    }

    /**
     * Establece el valor del campo productos.
     *
     * @param productos el valor a establecer
     */
    public void setProductos(final ConfigProducto productos) {
        this.productos = productos;
    }

    /**
     * Regresa el campo recargaEnLinea.
     *
     * @return el valor de recargaEnLinea
     */
    public RangoValores getRecargaEnLinea() {
        return recargaEnLinea;
    }

    /**
     * Establece el valor del campo recargaEnLinea.
     *
     * @param recargaEnLinea el valor a establecer
     */
    public void setRecargaEnLinea(final RangoValores recargaEnLinea) {
        this.recargaEnLinea = recargaEnLinea;
    }

    /**
     * Regresa el campo idClienteAnonimo.
     *
     * @return el valor de idClienteAnonimo
     */
    public int getIdClienteAnonimo() {
        return idClienteAnonimo;
    }

    /**
     * Establece el valor del campo idClienteAnonimo.
     *
     * @param idClienteAnonimo el valor a establecer
     */
    public void setIdClienteAnonimo(final int idClienteAnonimo) {
        this.idClienteAnonimo = idClienteAnonimo;
    }

    /**
     * Regresa el campo metodosPago.
     *
     * @return el valor de metodosPago
     */
    public List<IdDescripcion> getMetodosPago() {
        return metodosPago;
    }

    /**
     * Establece el valor del campo metodosPago.
     *
     * @param metodosPago el valor a establecer
     */
    public void setMetodosPago(final List<IdDescripcion> metodosPago) {
        this.metodosPago = metodosPago;
    }

    /**
     * Regresa el campo mapa.
     *
     * @return el valor de mapa
     */
    public ConfigMapa getMapa() {
        return mapa;
    }

    /**
     * Establece el valor del campo mapa.
     *
     * @param mapa el valor a establecer
     */
    public void setMapa(final ConfigMapa mapa) {
        this.mapa = mapa;
    }
}
