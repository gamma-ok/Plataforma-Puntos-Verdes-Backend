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
		// Verificar si ya existe el username
		Usuario usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
		if (usuarioExistente != null) {
			throw new RuntimeException("El usuario con username " + usuario.getUsername() + " ya existe.");
		}

		// Encriptar contraseña antes de guardar
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

		// Buscar rol "CIUDADANO"
		Rol rolCiudadano = rolRepository.findByRolNombre("CIUDADANO");
		if (rolCiudadano == null) {
			rolCiudadano = new Rol();
			rolCiudadano.setRolNombre("CIUDADANO");
			rolRepository.save(rolCiudadano);
		}

		// Crear relación UsuarioRol
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
}
