package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Canje;
import java.util.List;

public interface CanjeService {
    Canje registrarCanje(Canje canje);
    List<Canje> listarCanjesPorUsuario(Long usuarioId);
    Canje obtenerPorId(Long id);
    List<Canje> listarTodos();
}
