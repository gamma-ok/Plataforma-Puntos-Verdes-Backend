package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.PuntoVerde;
import java.util.List;

public interface PuntoVerdeService {
    PuntoVerde crearPuntoVerde(PuntoVerde puntoVerde);
    PuntoVerde actualizarPuntoVerde(Long id, PuntoVerde datos);
    PuntoVerde obtenerPorId(Long id);
    List<PuntoVerde> listarPuntosVerdes();
    List<PuntoVerde> listarPorEstado(boolean activo);
    List<PuntoVerde> buscarPorNombre(String nombre);
    PuntoVerde cambiarEstado(Long id, boolean activo);
}
