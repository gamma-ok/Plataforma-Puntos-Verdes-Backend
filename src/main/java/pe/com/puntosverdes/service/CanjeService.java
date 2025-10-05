package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import java.util.List;

public interface CanjeService {
    Canje crearCanje(Usuario usuario, Recompensa recompensa);
    Canje aprobarCanje(Long canjeId, String respuestaAdmin);
    Canje rechazarCanje(Long canjeId, String motivo, String respuestaAdmin);
    Canje obtenerPorId(Long id);
    List<Canje> listarCanjes();
    List<Canje> listarPorUsuario(Long usuarioId);
}
