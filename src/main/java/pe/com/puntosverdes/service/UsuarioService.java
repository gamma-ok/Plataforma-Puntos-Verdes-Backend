package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.model.Usuario;
import java.util.List;
import java.util.Map;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorUsername(String username);
    Usuario obtenerUsuarioPorEmail(String email);
    List<Usuario> obtenerUsuariosPorCelular(String celular);
    List<Usuario> listarUsuarios();
    List<Usuario> listarUsuariosPorRol(String rolNombre);
    List<Usuario> listarUsuariosPorEstado(boolean estado);
    List<Usuario> obtenerRankingUsuarios();
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
    Usuario cambiarContrasena(Long id, String nuevaContrasena);
    Usuario actualizarPerfil(Long id, String perfilUrl);
    Usuario actualizarEstado(Long id, boolean activo);
    void eliminarUsuario(Long id);
    Usuario asignarRol(Long idUsuario, String nuevoRol);
    UsuarioDTO convertirADTO(Usuario usuario);
    Map<String, Object> obtenerEstadisticasUsuarios();
}
