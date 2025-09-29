package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorUsername(String username);
    List<Usuario> listarUsuarios();
    void eliminarUsuario(Long id);
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);
    Usuario cambiarContrasena(Long id, String nuevaContrasena);
    Usuario habilitarUsuario(Long id);
    Usuario deshabilitarUsuario(Long id);
    List<Usuario> listarUsuariosPorRol(String rolNombre); 
    Usuario actualizarPerfil(Long id, String perfilUrl);
}
