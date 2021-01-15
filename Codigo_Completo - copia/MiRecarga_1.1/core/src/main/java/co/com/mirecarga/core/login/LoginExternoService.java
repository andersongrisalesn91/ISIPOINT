package co.com.mirecarga.core.login;

import androidx.annotation.NonNull;

/**
 * Contrato para el servicio que recibe los datos de login externo.
 */
public interface LoginExternoService {
    /**
     * Obtiene el token de la aplicación.
     * @param datosExterno los datos del usuario
     */
    void iniciarSesion(@NonNull DatosIniciarSesionExterno datosExterno);
}
