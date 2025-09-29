package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Rol;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.model.UsuarioRol;
import pe.com.puntosverdes.repository.RolRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.UsuarioService;

import java.util.HashSet;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistente != null) {
            throw new RuntimeException("El usuario con username " + usuario.getUsername() + " ya existe.");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Rol rolCiudadano = rolRepository.findByRolNombre("CIUDADANO");
        if (rolCiudadano == null) {
            rolCiudadano = new Rol();
            rolCiudadano.setRolNombre("CIUDADANO");
            rolRepository.save(rolCiudadano);
        }

        UsuarioRol usuarioRol = new UsuarioRol(usuario, rolCiudadano);

        if (usuario.getUsuarioRoles() == null) {
            usuario.setUsuarioRoles(new HashSet<>());
        }
        usuario.getUsuarioRoles().add(usuarioRol);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void eliminarUsuarioPorUsername(String username) {
        usuarioRepository.deleteByUsername(username);
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setCelular(usuarioActualizado.getCelular());
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @Override
    public Usuario cambiarContrasena(Long id, String nuevaContrasena) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @Override
    public Usuario habilitarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEnabled(true);
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @Override
    public Usuario deshabilitarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEnabled(false);
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @Override
    public List<Usuario> listarUsuariosPorRol(String rolNombre) {
        return usuarioRepository.findByUsuarioRoles_Rol_RolNombre(rolNombre);
    }
    
    @Override
    public Usuario actualizarPerfil(Long id, String perfilUrl) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setPerfil(perfilUrl);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
}
