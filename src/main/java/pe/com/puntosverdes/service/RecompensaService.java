package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Recompensa;
import java.util.List;

public interface RecompensaService {
    Recompensa crearRecompensa(Recompensa recompensa);
    List<Recompensa> listarRecompensas();
    Recompensa obtenerPorId(Long id);
    void actualizarEstado(Long id, boolean activo);
    void eliminarRecompensa(Long id);
}
