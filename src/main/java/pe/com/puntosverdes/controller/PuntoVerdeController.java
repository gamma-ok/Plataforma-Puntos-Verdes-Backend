package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.*;
import pe.com.puntosverdes.mapper.PuntoVerdeMapper;
import pe.com.puntosverdes.model.PuntoVerde;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.PuntoVerdeService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.*;

@RestController
@RequestMapping("/api/puntos-verdes")
@CrossOrigin("*")
public class PuntoVerdeController {

	@Autowired
	private PuntoVerdeService puntoVerdeService;

	@Autowired
	private UsuarioService usuarioService;

	// Registrar
	@PostMapping("/registrar")
	public ResponseEntity<PuntoVerdeDTO> registrar(@RequestBody PuntoVerdeRegistroDTO dto, Authentication auth) {
		Usuario creador = usuarioService.obtenerUsuarioPorUsername(auth.getName());

		PuntoVerde punto = new PuntoVerde();
		punto.setNombre(dto.getNombre());
		punto.setDireccion(dto.getDireccion());
		punto.setDescripcion(dto.getDescripcion());
		punto.setLatitud(dto.getLatitud());
		punto.setLongitud(dto.getLongitud());
		punto.setActivo(dto.isActivo());
		punto.setCreadoPor(creador);

		PuntoVerde guardado = puntoVerdeService.crearPuntoVerde(punto);
		return ResponseEntity.ok(PuntoVerdeMapper.toDTO(guardado));
	}

	// Actualizar
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<PuntoVerdeDTO> actualizar(@PathVariable Long id, @RequestBody PuntoVerdeRegistroDTO dto) {
		PuntoVerde datos = new PuntoVerde();
		datos.setNombre(dto.getNombre());
		datos.setDireccion(dto.getDireccion());
		datos.setDescripcion(dto.getDescripcion());
		datos.setLatitud(dto.getLatitud());
		datos.setLongitud(dto.getLongitud());
		datos.setActivo(dto.isActivo());

		PuntoVerde actualizado = puntoVerdeService.actualizarPuntoVerde(id, datos);
		return ResponseEntity.ok(PuntoVerdeMapper.toDTO(actualizado));
	}

	// Listar todos
	@GetMapping("/listar")
	public ResponseEntity<List<PuntoVerdeDTO>> listarTodos() {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPuntosVerdes()));
	}

	// Listar por estado (true/false)
	@GetMapping("listar//estado/{activo}")
	public ResponseEntity<List<PuntoVerdeDTO>> listarPorEstado(@PathVariable boolean activo) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPorEstado(activo)));
	}

	// Buscar por nombre
	@GetMapping("/buscar")
	public ResponseEntity<List<PuntoVerdeDTO>> buscarPorNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.buscarPorNombre(nombre)));
	}

	// Detalle
	@GetMapping("/{id}/detalle")
	public ResponseEntity<PuntoVerdeDetalleDTO> obtenerPorId(@PathVariable Long id) {
		PuntoVerde pv = puntoVerdeService.obtenerPorId(id);
		if (pv == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(PuntoVerdeMapper.toDetalleDTO(pv));
	}

	// Estadisticas generales
	@GetMapping("/estadisticas")
	public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
		List<PuntoVerde> puntos = puntoVerdeService.listarPuntosVerdes();

		long total = puntos.size();
		long activos = puntos.stream().filter(PuntoVerde::isActivo).count();
		long inactivos = total - activos;

		double promedioEntregas = puntos.stream().mapToInt(p -> p.getEntregas().size()).average().orElse(0.0);

		PuntoVerde masUsado = puntos.stream().max(Comparator.comparingInt(p -> p.getEntregas().size())).orElse(null);

		Map<String, Object> estadisticas = new HashMap<>();
		estadisticas.put("totalPuntosVerdes", total);
		estadisticas.put("puntosActivos", activos);
		estadisticas.put("puntosInactivos", inactivos);
		estadisticas.put("promedioEntregasPorPunto", promedioEntregas);
		estadisticas.put("puntoVerdeMasUsado",
				masUsado != null ? masUsado.getNombre() + " (" + masUsado.getEntregas().size() + " entregas)" : "N/A");

		return ResponseEntity.ok(estadisticas);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		puntoVerdeService.eliminarPuntoVerde(id);
		return ResponseEntity.noContent().build();
	}
}
