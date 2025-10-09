package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.*;
import pe.com.puntosverdes.mapper.CampaniaMapper;
import pe.com.puntosverdes.model.*;
import pe.com.puntosverdes.service.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/campanias")
@CrossOrigin("*")
public class CampaniaController {

	@Autowired
	private CampaniaService campaniaService;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/registrar")
	public ResponseEntity<CampaniaDTO> registrar(@RequestBody Campania campania, Authentication authentication) {
		Usuario creador = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
		campania.setCreadoPor(creador);
		Campania creada = campaniaService.crearCampania(campania);
		return ResponseEntity.ok(CampaniaMapper.toDTO(creada));
	}

	@GetMapping("/listar")
	public ResponseEntity<List<CampaniaDTO>> listar() {
		return ResponseEntity
				.ok(campaniaService.listarCampanias().stream().map(CampaniaMapper::toDTO).collect(Collectors.toList()));
	}

	@GetMapping("/listar/usuario/{usuarioId}")
	public ResponseEntity<List<CampaniaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(campaniaService.listarPorUsuario(usuarioId).stream().map(CampaniaMapper::toDTO)
				.collect(Collectors.toList()));
	}

	@GetMapping("/listar/rol/{rol}")
	public ResponseEntity<List<CampaniaDTO>> listarPorRol(@PathVariable String rol) {
		return ResponseEntity
				.ok(campaniaService.listarPorRol(rol).stream().map(CampaniaMapper::toDTO).collect(Collectors.toList()));
	}

	@GetMapping("/buscar/titulo/{titulo}")
	public ResponseEntity<List<CampaniaDTO>> buscarPorTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(campaniaService.buscarCampaniaPorTitulo(titulo).stream().map(CampaniaMapper::toDTO)
				.collect(Collectors.toList()));
	}

	@GetMapping("/listar/estado/{activa}")
	public ResponseEntity<List<CampaniaDTO>> listarPorEstado(@PathVariable boolean activa) {
		return ResponseEntity.ok(campaniaService.listarPorEstado(activa).stream().map(CampaniaMapper::toDTO)
				.collect(Collectors.toList()));
	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<CampaniaDetalleDTO> detalle(@PathVariable Long id) {
		Campania campania = campaniaService.obtenerPorId(id);
		if (campania == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(CampaniaMapper.toDetalleDTO(campania));
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<CampaniaDTO> actualizar(@PathVariable Long id, @RequestBody Campania datos) {
		return ResponseEntity.ok(CampaniaMapper.toDTO(campaniaService.actualizarCampania(id, datos)));
	}

	@GetMapping("/estadisticas")
	public ResponseEntity<Map<String, Object>> estadisticas() {
		return ResponseEntity.ok(campaniaService.obtenerEstadisticas());
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		campaniaService.eliminarCampania(id);
		return ResponseEntity.noContent().build();
	}
}
