package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.model.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorUsername(String username);
    Usuario obtenerUsuarioPorEmail(String email);
    List<Usuario> obtenerUsuariosPorCelular(String celular);
    List<Usuario> listarUsuarios();
    List<Usuario> listarUsuariosPorRol(String rolNombre);
    List<Usuario> obtenerRankingUsuarios();
    void eliminarUsuario(Long id);
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
    Usuario cambiarContrasena(Long id, String nuevaContrasena);
    Usuario habilitarUsuario(Long id);
    Usuario deshabilitarUsuario(Long id);
    Usuario actualizarPerfil(Long id, String perfilUrl);
    UsuarioDTO convertirADTO(Usuario usuario);
    Usuario asignarRol(Long idUsuario, String nuevoRol);
}
