package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.UsuarioRol;
import pe.com.puntosverdes.repository.UsuarioRolRepository;
import pe.com.puntosverdes.service.UsuarioRolService;

import java.util.List;

@Service
public class UsuarioRolServiceImpl implements UsuarioRolService {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Override
    public UsuarioRol asignarRolUsuario(UsuarioRol usuarioRol) {
        return usuarioRolRepository.save(usuarioRol);
    }

    @Override
    public void eliminarRolUsuario(Long id) {
        usuarioRolRepository.deleteById(id);
    }

    @Override
    public List<UsuarioRol> listarRolesDeUsuarios() {
        return usuarioRolRepository.findAll();
    }
}
