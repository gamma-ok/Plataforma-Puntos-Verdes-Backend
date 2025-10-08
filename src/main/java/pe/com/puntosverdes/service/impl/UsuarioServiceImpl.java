package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.exception.UsuarioFoundException;
import pe.com.puntosverdes.exception.UsuarioNotFoundException;
import pe.com.puntosverdes.model.AjustePuntos;
import pe.com.puntosverdes.model.Rol;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.model.UsuarioRol;
import pe.com.puntosverdes.repository.AjustePuntosRepository;
import pe.com.puntosverdes.repository.RolRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AjustePuntosRepository ajustePuntosRepository;

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
		if (usuario == null)
			throw new UsuarioNotFoundException("Usuario no encontrado con username: " + username);
		return usuario;
	}

	@Override
	public Usuario obtenerUsuarioPorEmail(String email) {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con email: " + email));
	}
	

	@Override
	public List<Usuario> obtenerUsuariosPorCelular(String celular) {
		return usuarioRepository.findByCelular(celular);
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public List<Usuario> listarUsuariosPorEstado(boolean estado) {
		return usuarioRepository.findAll().stream().filter(u -> u.isEnabled() == estado).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> listarUsuariosPorRol(String rolNombre) {
		return usuarioRepository.findByUsuarioRoles_Rol_RolNombre(rolNombre);
	}

	@Override
	public List<Usuario> obtenerRankingUsuarios() {
		return usuarioRepository.findAllByOrderByPuntosAcumuladosDesc();
	}

	@Override
	public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
		return usuarioRepository.findById(id).map(usuario -> {
			if (usuarioActualizado.getNombre() != null)
				usuario.setNombre(usuarioActualizado.getNombre());
			if (usuarioActualizado.getApellido() != null)
				usuario.setApellido(usuarioActualizado.getApellido());
			if (usuarioActualizado.getEmail() != null)
				usuario.setEmail(usuarioActualizado.getEmail());
			if (usuarioActualizado.getCelular() != null)
				usuario.setCelular(usuarioActualizado.getCelular());
			if (usuarioActualizado.getPerfil() != null)
				usuario.setPerfil(usuarioActualizado.getPerfil());
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
	public Usuario actualizarPerfil(Long id, String perfilUrl) {
		return usuarioRepository.findById(id).map(usuario -> {
			usuario.setPerfil(perfilUrl);
			return usuarioRepository.save(usuario);
		}).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
	}

	@Override
	public Usuario actualizarEstado(Long id, boolean activo) {
		return usuarioRepository.findById(id).map(usuario -> {
			usuario.setEnabled(activo);
			return usuarioRepository.save(usuario);
		}).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
	}

	@Override
	public void eliminarUsuario(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new UsuarioNotFoundException("Usuario no encontrado con id: " + id);
		}
		usuarioRepository.deleteById(id);
	}

	@Override
	public Usuario asignarRol(Long idUsuario, String nuevoRol) {
		return usuarioRepository.findById(idUsuario).map(usuario -> {
			Rol rol = rolRepository.findByRolNombre(nuevoRol.toUpperCase());
			if (rol == null) {
				rol = new Rol();
				rol.setRolNombre(nuevoRol.toUpperCase());
				rolRepository.save(rol);
			}

			usuario.getUsuarioRoles().clear();
			usuario.getUsuarioRoles().add(new UsuarioRol(usuario, rol));

			return usuarioRepository.save(usuario);
		}).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + idUsuario));
	}

	@Override
	public UsuarioDTO convertirADTO(Usuario usuario) {
		Set<String> roles = usuario.getUsuarioRoles().stream().map(ur -> ur.getRol().getRolNombre())
				.collect(Collectors.toSet());

		return new UsuarioDTO(usuario.getId(), usuario.getUsername(), usuario.getNombre(), usuario.getApellido(),
				usuario.getEmail(), usuario.getCelular(), usuario.isEnabled(), roles);
	}

	@Override
	public Map<String, Object> obtenerEstadisticasUsuarios() {
		Map<String, Object> stats = new HashMap<>();

		List<Usuario> todos = usuarioRepository.findAll();
		long total = todos.size();
		long activos = todos.stream().filter(Usuario::isEnabled).count();
		long inactivos = total - activos;

		Map<String, Long> porRol = todos.stream().flatMap(u -> u.getUsuarioRoles().stream())
				.collect(Collectors.groupingBy(ur -> ur.getRol().getRolNombre(), Collectors.counting()));

		stats.put("totalUsuarios", total);
		stats.put("activos", activos);
		stats.put("inactivos", inactivos);
		stats.put("porRol", porRol);

		return stats;
	}

	@Override
	public Usuario ajustarPuntos(Long idUsuario, String accion, int cantidad, String motivo, String realizadoPor) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + idUsuario));

		int puntosActuales = usuario.getPuntosAcumulados();
		int nuevosPuntos = puntosActuales;

		switch (accion.toUpperCase()) {
		case "SUMAR":
			nuevosPuntos += cantidad;
			break;
		case "RESTAR":
			nuevosPuntos -= cantidad;
			if (nuevosPuntos < 0)
				nuevosPuntos = 0;
			break;
		case "ESTABLECER":
			nuevosPuntos = cantidad;
			break;
		default:
			throw new IllegalArgumentException("Acción no valida. Usa: SUMAR, RESTAR o ESTABLECER.");
		}

		usuario.setPuntosAcumulados(nuevosPuntos);
		usuarioRepository.save(usuario);

		AjustePuntos registro = new AjustePuntos(idUsuario, realizadoPor, accion, cantidad, motivo);
		ajustePuntosRepository.save(registro);

		return usuario;
	}
}
