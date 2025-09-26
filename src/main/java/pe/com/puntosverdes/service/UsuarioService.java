package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Usuario;
import java.util.List;

public interface UsuarioService {
	
    Usuario guardarUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorId(Long id);
    Usuario obtenerUsuarioPorUsername(String username);
    List<Usuario> listarUsuarios();
    void eliminarUsuario(Long id);
}
