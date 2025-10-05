package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.PuntoVerde;
import java.util.List;

public interface PuntoVerdeService {
    PuntoVerde crearPuntoVerde(PuntoVerde puntoVerde);
    PuntoVerde obtenerPorId(Long id);
    List<PuntoVerde> listarPuntosVerdes();
    void desactivarPuntoVerde(Long id);
    List<PuntoVerde> listarPuntosActivos();
}
