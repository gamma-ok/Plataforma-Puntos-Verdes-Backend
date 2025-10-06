package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.CampaniaDTO;
import pe.com.puntosverdes.dto.CampaniaDetalleDTO;
import pe.com.puntosverdes.mapper.CampaniaMapper;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.CampaniaService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/campanias")
@CrossOrigin("*")
public class CampaniaController {

	@Autowired
	private CampaniaService campaniaService;

	@Autowired
	private UsuarioService usuarioService;

	// CREAR NUEVA CAMPANIA
	@PostMapping("/")
	public ResponseEntity<Campania> crearCampania(@RequestBody Campania campania, Authentication authentication) {

		// Obtener el usuario autenticado (ADMIN o MUNICIPALIDAD)
		Usuario creador = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
		campania.setCreadoPor(creador);

		Campania creada = campaniaService.crearCampania(campania);
		return ResponseEntity.ok(creada);
	}

	// LISTAR TODAS
	@GetMapping("/")
	public ResponseEntity<List<CampaniaDTO>> listar() {
		List<CampaniaDTO> lista = campaniaService.listarCampanias().stream().map(CampaniaMapper::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	// LISTAR ACTIVAS
	@GetMapping("/activas")
	public ResponseEntity<List<CampaniaDTO>> listarActivas() {
		List<CampaniaDTO> activas = campaniaService.listarCampaniasActivas().stream().map(CampaniaMapper::toDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(activas);
	}

	// DETALLE POR ID
	@GetMapping("/{id}")
	public ResponseEntity<CampaniaDetalleDTO> obtenerPorId(@PathVariable Long id) {
		Campania campania = campaniaService.obtenerPorId(id);
		if (campania == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(CampaniaMapper.toDetalleDTO(campania));
	}

	// DESACTIVAR CAMPAÑA
	@PutMapping("/{id}/desactivar")
	public ResponseEntity<Void> desactivar(@PathVariable Long id) {
		campaniaService.desactivarCampania(id);
		return ResponseEntity.noContent().build();
	}

	// BUSCAR POR TÍTULO
	@GetMapping("/buscar/titulo/{titulo}")
	public ResponseEntity<List<CampaniaDTO>> buscarPorTitulo(@PathVariable String titulo) {
		List<CampaniaDTO> resultados = campaniaService.buscarCampaniaPorTitulo(titulo).stream()
				.map(CampaniaMapper::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok(resultados);
	}

	// BUSCAR POR UBICACIÓN
	@GetMapping("/buscar/ubicacion/{ubicacion}")
	public ResponseEntity<List<CampaniaDTO>> buscarPorUbicacion(@PathVariable String ubicacion) {
		List<CampaniaDTO> resultados = campaniaService.buscarCampaniaPorUbicacion(ubicacion).stream()
				.map(CampaniaMapper::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok(resultados);
	}
}
