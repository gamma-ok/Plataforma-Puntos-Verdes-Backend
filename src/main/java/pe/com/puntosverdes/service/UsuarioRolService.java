package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.UsuarioRol;

import java.util.List;

public interface UsuarioRolService {
	
    UsuarioRol asignarRolUsuario(UsuarioRol usuarioRol);
    void eliminarRolUsuario(Long id);
    List<UsuarioRol> listarRolesDeUsuarios();
}
