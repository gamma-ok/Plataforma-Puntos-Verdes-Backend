package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.RecompensaDTO;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.RecompensaService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recompensas")
@CrossOrigin("*")
public class RecompensaController {

	@Autowired
	private RecompensaService recompensaService;

	@Autowired
	private UsuarioService usuarioService;

	// Crear recompensa (ADMIN / MUNICIPALIDAD)
	@PostMapping("/registrar")
	public ResponseEntity<RecompensaDTO> crearRecompensa(@RequestBody Recompensa recompensa, Authentication auth) {
		Usuario creador = usuarioService.obtenerUsuarioPorUsername(auth.getName());
		recompensa.setCreadoPor(creador);

		Recompensa creada = recompensaService.crearRecompensa(recompensa);
		return ResponseEntity.ok(recompensaService.convertirADTO(creada));
	}

	// Actualizar recompensa (ADMIN / MUNICIPALIDAD)
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<RecompensaDTO> actualizarRecompensa(@PathVariable Long id, @RequestBody Recompensa datos) {
		Recompensa actualizada = recompensaService.actualizarRecompensa(id, datos);
		return ResponseEntity.ok(recompensaService.convertirADTO(actualizada));
	}

	// Listar todas las recompensas
	@GetMapping("/listar")
	public ResponseEntity<List<RecompensaDTO>> listar() {
		List<RecompensaDTO> lista = recompensaService.listarRecompensas().stream().map(recompensaService::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	// Listar recompensas por estado (true / false)
	@GetMapping("/listar/estado/{activo}")
	public ResponseEntity<List<RecompensaDTO>> listarPorEstado(@PathVariable boolean activo) {
		List<RecompensaDTO> lista = recompensaService.listarPorEstado(activo).stream()
				.map(recompensaService::convertirADTO).collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	// Obtener recompensa por ID
	@GetMapping("/buscar/{id}")
	public ResponseEntity<RecompensaDTO> obtenerPorId(@PathVariable Long id) {
		Recompensa r = recompensaService.obtenerPorId(id);
		return ResponseEntity.ok(recompensaService.convertirADTO(r));
	}

	// Buscar recompensa por nombre (coincidencia parcial)
	@GetMapping("/buscar/nombre/{nombre}")
	public ResponseEntity<List<RecompensaDTO>> buscarPorNombre(@PathVariable String nombre) {
		List<RecompensaDTO> res = recompensaService.buscarPorNombre(nombre).stream()
				.map(recompensaService::convertirADTO).collect(Collectors.toList());
		return ResponseEntity.ok(res);
	}

	// Eliminar recompensa
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		recompensaService.eliminarRecompensa(id);
		return ResponseEntity.noContent().build();
	}

	// Estadisticas de recompensas
	@GetMapping("/estadisticas")
	public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
		return ResponseEntity.ok(recompensaService.obtenerEstadisticas());
	}
}
