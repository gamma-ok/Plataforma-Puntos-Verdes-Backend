package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.dto.UsuarioPerfilDTO;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	// Registrar usuario
	@PostMapping("/registrar")
	public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody Usuario usuario) {
		usuario.setPerfil("default.png");
		Usuario creado = usuarioService.crearUsuario(usuario);
		return ResponseEntity.ok(usuarioService.convertirADTO(creado));
	}

	// Listar
	@GetMapping("/listar")
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		return ResponseEntity.ok(usuarioService.listarUsuarios().stream().map(usuarioService::convertirADTO)
				.collect(Collectors.toList()));
	}

	// Listar por estado del usuario (true = activo, false = inactivo)
	@GetMapping("/listar/{estado}")
	public ResponseEntity<List<UsuarioDTO>> listarPorEstado(@PathVariable boolean estadoUsuario) {
	    List<UsuarioDTO> usuarios = usuarioService.listarUsuariosPorEstado(estadoUsuario).stream()
	            .map(usuarioService::convertirADTO)
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(usuarios);
	}

	// Estadísticas
	@GetMapping("/estadisticas")
	public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
		return ResponseEntity.ok(usuarioService.obtenerEstadisticasUsuarios());
	}

	// ✅ Ranking
	@GetMapping("/ranking")
	public ResponseEntity<List<Usuario>> ranking() {
		return ResponseEntity.ok(usuarioService.obtenerRankingUsuarios());
	}

	// ✅ Estado activo/inactivo
	@PutMapping("/{id}/estado/{activo}")
	public ResponseEntity<UsuarioDTO> cambiarEstado(@PathVariable Long id, @PathVariable boolean activo) {
		Usuario actualizado = usuarioService.actualizarEstado(id, activo);
		return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
	}

	// Buscar por rol
	@GetMapping("/listar/rol/{rol}")
	public ResponseEntity<List<UsuarioDTO>> listarPorRol(@PathVariable String rol) {
		return ResponseEntity.ok(usuarioService.listarUsuariosPorRol(rol).stream().map(usuarioService::convertirADTO)
				.collect(Collectors.toList()));
	}

	// Obtener mi perfil (JWT)
	@GetMapping("/perfil/mi")
	public ResponseEntity<UsuarioPerfilDTO> miPerfil(Authentication auth) {
		Usuario usuario = usuarioService.obtenerUsuarioPorUsername(auth.getName());

		Set<String> roles = usuario.getUsuarioRoles().stream().map(ur -> ur.getRol().getRolNombre())
				.collect(Collectors.toSet());

		UsuarioPerfilDTO dto = new UsuarioPerfilDTO(usuario.getUsername(), usuario.getNombre(), usuario.getApellido(),
				usuario.getEmail(), usuario.getCelular(), usuario.getPerfil(), roles, usuario.getPuntosAcumulados(),
				usuario.getFechaRegistro());
		return ResponseEntity.ok(dto);
	}
}
