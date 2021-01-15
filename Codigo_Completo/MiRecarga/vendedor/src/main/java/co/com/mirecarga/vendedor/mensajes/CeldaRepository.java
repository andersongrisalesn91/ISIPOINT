package co.com.mirecarga.vendedor.mensajes;

public interface CeldaRepository {
    void actualizar(MensajeCelda celda);

    Integer getIdEstadoCelda(int idZona, int idCelda);
}
