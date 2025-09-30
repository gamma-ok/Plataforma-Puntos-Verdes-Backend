package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.model.Usuario;

import java.util.List;

public interface UsuarioService {

    // Crear usuario
    Usuario crearUsuario(Usuario usuario);

    // Obtener usuario
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorUsername(String username);

    // Listar usuarios
    List<Usuario> listarUsuarios();
    List<Usuario> listarUsuariosPorRol(String rolNombre);

    // Eliminar usuario
    void eliminarUsuario(Long id);

    // Actualizar datos generales
    Usuario actualizarUsuario(Long id, Usuario usuarioActualizado);

    // Operaciones especiales
    Usuario cambiarContrasena(Long id, String nuevaContrasena);
    Usuario habilitarUsuario(Long id);
    Usuario deshabilitarUsuario(Long id);
    Usuario actualizarPerfil(Long id, String perfilUrl);

    // Conversión a DTO (para evitar exponer password)
    UsuarioDTO convertirADTO(Usuario usuario);
}
