package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.PuntoVerdeDTO;
import pe.com.puntosverdes.dto.PuntoVerdeDetalleDTO;
import pe.com.puntosverdes.mapper.PuntoVerdeMapper;
import pe.com.puntosverdes.model.PuntoVerde;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.PuntoVerdeService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;

@RestController
@RequestMapping("/api/puntos-verdes")
@CrossOrigin("*")
public class PuntoVerdeController {

	@Autowired
	private PuntoVerdeService puntoVerdeService;

	@Autowired
	private UsuarioService usuarioService;

	// Crear punto verde (solo admin/municipalidad)
	@PostMapping("/")
	public ResponseEntity<PuntoVerdeDTO> crear(@RequestBody PuntoVerde puntoVerde, Authentication auth) {
		Usuario creador = usuarioService.obtenerUsuarioPorUsername(auth.getName());
		puntoVerde.setCreadoPor(creador);
		PuntoVerde guardado = puntoVerdeService.crearPuntoVerde(puntoVerde);
		return ResponseEntity.ok(PuntoVerdeMapper.toDTO(guardado));
	}

	// Listar todos
	@GetMapping("/")
	public ResponseEntity<List<PuntoVerdeDTO>> listar() {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPuntosVerdes()));
	}

	// Listar activos
	@GetMapping("/activos")
	public ResponseEntity<List<PuntoVerdeDTO>> listarActivos() {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPuntosActivos()));
	}

	// Detalle por ID
	@GetMapping("/{id}")
	public ResponseEntity<PuntoVerdeDetalleDTO> obtenerPorId(@PathVariable Long id) {
		PuntoVerde pv = puntoVerdeService.obtenerPorId(id);
		if (pv == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(PuntoVerdeMapper.toDetalleDTO(pv));
	}

	// Desactivar
	@PutMapping("/{id}/desactivar")
	public ResponseEntity<Void> desactivar(@PathVariable Long id) {
		puntoVerdeService.desactivarPuntoVerde(id);
		return ResponseEntity.noContent().build();
	}
}
