package co.com.mirecarga.cliente.app;

import java.util.List;

import co.com.mirecarga.core.login.ConfigWso2;
import co.com.mirecarga.core.mapa.ConfigMapa;
import co.com.mirecarga.core.util.IdDescripcion;

/**
 * Datos de configuración de la aplicación.
 */
public class ConfigCliente {
    /**
     * Configuración para la autenticación con WSO2.
     */
    private ConfigWso2 wso2;

    /**
     * Datos de configuración de productos.
     */
    private ConfigProducto productos;

    /**
     * Estado para las tarjetas en uso.
     */
    private String estadoTarjetaEnUso;

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
     * Regresa el campo estadoTarjetaEnUso.
     *
     * @return el valor de estadoTarjetaEnUso
     */
    public String getEstadoTarjetaEnUso() {
        return estadoTarjetaEnUso;
    }

    /**
     * Establece el valor del campo estadoTarjetaEnUso.
     *
     * @param estadoTarjetaEnUso el valor a establecer
     */
    public void setEstadoTarjetaEnUso(final String estadoTarjetaEnUso) {
        this.estadoTarjetaEnUso = estadoTarjetaEnUso;
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
