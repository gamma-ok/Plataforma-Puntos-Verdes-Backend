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
import java.util.List;

@RestController
@RequestMapping("/api/puntos-verdes")
@CrossOrigin("*")
public class PuntoVerdeController {

	@Autowired
	private PuntoVerdeService puntoVerdeService;

	@Autowired
	private UsuarioService usuarioService;

	// Registrar un nuevo Punto Verde
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

	// Actualizar un punto verde existente
	@PutMapping("/{id}/actualizar")
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

	// Listar por estado
	@GetMapping("/estado/{activo}")
	public ResponseEntity<List<PuntoVerdeDTO>> listarPorEstado(@PathVariable boolean activo) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.listarPorEstado(activo)));
	}

	// Buscar por nombre
	@GetMapping("/buscar")
	public ResponseEntity<List<PuntoVerdeDTO>> buscarPorNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(PuntoVerdeMapper.toDTOList(puntoVerdeService.buscarPorNombre(nombre)));
	}

	// Detalle por ID
	@GetMapping("/{id}/detalle")
	public ResponseEntity<PuntoVerdeDetalleDTO> obtenerPorId(@PathVariable Long id) {
		PuntoVerde pv = puntoVerdeService.obtenerPorId(id);
		if (pv == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(PuntoVerdeMapper.toDetalleDTO(pv));
	}

	// Cambiar estado (activar/desactivar)
	@PutMapping("/{id}/estado")
	public ResponseEntity<PuntoVerdeDTO> cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
		PuntoVerde actualizado = puntoVerdeService.cambiarEstado(id, activo);
		return ResponseEntity.ok(PuntoVerdeMapper.toDTO(actualizado));
	}
}
