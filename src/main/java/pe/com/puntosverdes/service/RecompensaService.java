package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.RecompensaDTO;
import pe.com.puntosverdes.model.Recompensa;
import java.util.List;
import java.util.Map;

public interface RecompensaService {
    Recompensa crearRecompensa(Recompensa recompensa);
    Recompensa actualizarRecompensa(Long id, Recompensa datos);
    List<Recompensa> listarRecompensas();
    List<Recompensa> listarPorEstado(boolean activo);
    Recompensa obtenerPorId(Long id);
    void eliminarRecompensa(Long id);
    List<Recompensa> buscarPorNombre(String nombre);
    List<Recompensa> listarPorCreadorNombre(String nombreCompleto);
    List<Recompensa> listarPorRolCreador(String rolNombre);
    RecompensaDTO convertirADTO(Recompensa recompensa);
    Map<String, Object> obtenerEstadisticas();
}
