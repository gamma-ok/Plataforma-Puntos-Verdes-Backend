package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.exception.UsuarioFoundException;
import pe.com.puntosverdes.exception.UsuarioNotFoundException;
import pe.com.puntosverdes.model.Rol;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.model.UsuarioRol;
import pe.com.puntosverdes.repository.RolRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.UsuarioService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            throw new UsuarioFoundException("El usuario con username " + usuario.getUsername() + " ya existe.");
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
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario obtenerUsuarioPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) throw new UsuarioNotFoundException("Usuario no encontrado con username: " + username);
        return usuario;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (usuarioActualizado.getNombre() != null) usuario.setNombre(usuarioActualizado.getNombre());
            if (usuarioActualizado.getApellido() != null) usuario.setApellido(usuarioActualizado.getApellido());
            if (usuarioActualizado.getEmail() != null) usuario.setEmail(usuarioActualizado.getEmail());
            if (usuarioActualizado.getCelular() != null) usuario.setCelular(usuarioActualizado.getCelular());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario cambiarContrasena(Long id, String nuevaContrasena) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario habilitarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEnabled(true);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario deshabilitarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEnabled(false);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
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
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public UsuarioDTO convertirADTO(Usuario usuario) {
        Set<String> roles = usuario.getUsuarioRoles().stream()
                .map(ur -> ur.getRol().getRolNombre())
                .collect(Collectors.toSet());

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.isEnabled(),
                roles
        );
    }
}
