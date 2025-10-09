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

		// activo: si no nos envían valor (null) lo creamos como true por defecto
		punto.setActivo(dto.getActivo() == null ? true : dto.getActivo());
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
		datos.setActivo(dto.getActivo() == null ? true : dto.getActivo());

		PuntoVerde actualizado = puntoVerdeService.actualizarPuntoVerde(id, datos);
		return ResponseEntity.ok(PuntoVerdeMapper.toDTO(actualizado));
	}

	// Listar todos
	@GetMapping("/listar")
	public ResponseEntity<List<PuntoVerdeDTO>> listarTodos() {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPuntosVerdes()));
	}

	// Listar por estado (true/false)
	@GetMapping("/listar/estado/{activo}")
	public ResponseEntity<List<PuntoVerdeDTO>> listarPorEstado(@PathVariable boolean activo) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPorEstado(activo)));
	}

	// Listar por usuario creador
	@GetMapping("/listar/usuario/{usuarioId}")
	public ResponseEntity<List<PuntoVerdeDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPorCreador(usuarioId)));
	}

	// Listar por rol del creador (ADMIN / MUNICIPALIDAD)
	@GetMapping("/listar/rol/{rol}")
	public ResponseEntity<List<PuntoVerdeDTO>> listarPorRol(@PathVariable String rol) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPorRolCreador(rol)));
	}

	// Buscar por nombre (query param)
	@GetMapping("/buscar/nombre")
	public ResponseEntity<List<PuntoVerdeDTO>> buscarPorNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.buscarPorNombre(nombre)));
	}

	// Detalle por id
	@GetMapping("/detalle/{id}")
	public ResponseEntity<PuntoVerdeDetalleDTO> obtenerPorId(@PathVariable Long id) {
		PuntoVerde pv = puntoVerdeService.obtenerPorId(id);
		if (pv == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(PuntoVerdeMapper.toDetalleDTO(pv));
	}

	// Estadisticas
	@GetMapping("/estadisticas")
	public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
		List<PuntoVerde> puntos = puntoVerdeService.listarPuntosVerdes();

		long total = puntos.size();
		long activos = puntos.stream().filter(PuntoVerde::isActivo).count();
		long inactivos = total - activos;

		double promedioEntregas = puntos.stream().mapToInt(p -> p.getEntregas().size()).average().orElse(0.0);

		PuntoVerde masUsado = puntos.stream().max(Comparator.comparingInt(p -> p.getEntregas().size())).orElse(null);

		PuntoVerde menosUsado = puntos.stream().min(Comparator.comparingInt(p -> p.getEntregas().size())).orElse(null);

		Map<String, Object> estadisticas = new HashMap<>();
		estadisticas.put("totalPuntosVerdes", total);
		estadisticas.put("puntosActivos", activos);
		estadisticas.put("puntosInactivos", inactivos);
		estadisticas.put("promedioEntregasPorPunto", promedioEntregas);
		estadisticas.put("puntoVerdeMasUsado",
				masUsado != null ? masUsado.getNombre() + " (" + masUsado.getEntregas().size() + " entregas)" : "N/A");
		estadisticas.put("puntoVerdeMenosUsado",
				menosUsado != null ? menosUsado.getNombre() + " (" + menosUsado.getEntregas().size() + " entregas)"
						: "N/A");

		return ResponseEntity.ok(estadisticas);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		puntoVerdeService.eliminarPuntoVerde(id);
		return ResponseEntity.noContent().build();
	}
}
