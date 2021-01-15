package co.com.mirecarga.cliente.venta;

/**
 * Contrato para el servicio de inicio de uso de un PIN.
 */
public interface InicioUsoPinService {

    /**
     * Regresa el campo datosConfirmar.
     *
     * @return el valor de datosConfirmar
     */
    InicioUsoPinDatos getDatosConfirmar();

    /**
     * Establece el valor del campo datosConfirmar.
     *
     * @param datosConfirmar el valor a establecer
     */
    void setDatosConfirmar(InicioUsoPinDatos datosConfirmar);
}
