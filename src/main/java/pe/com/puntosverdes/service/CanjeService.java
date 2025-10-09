package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import java.util.List;

public interface CanjeService {
    Canje realizarCanje(Usuario usuario, Recompensa recompensa);
    Canje obtenerPorId(Long id);
    List<Canje> listarCanjes();
    List<Canje> listarPorUsuario(Long usuarioId);
    List<Canje> listarPorNombreUsuario(String nombre);
    List<Canje> listarPorRolUsuario(String rol);
    void eliminarCanje(Long id);
}
