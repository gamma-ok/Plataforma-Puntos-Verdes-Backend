package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.CanjeDTO;
import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.CanjeService;
import pe.com.puntosverdes.service.RecompensaService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/canjes")
@CrossOrigin("*")
public class CanjeController {

	@Autowired
	private CanjeService canjeService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RecompensaService recompensaService;

	// Realizar un canje
	@PostMapping("/realizar/{recompensaId}")
	public ResponseEntity<?> realizarCanje(@PathVariable Long recompensaId, Authentication auth) {
		Usuario usuario = usuarioService.obtenerUsuarioPorUsername(auth.getName());
		Recompensa recompensa = recompensaService.obtenerPorId(recompensaId);

		try {
			Canje nuevoCanje = canjeService.realizarCanje(usuario, recompensa);
			return ResponseEntity.ok(convertirADTO(nuevoCanje));
		} catch (RuntimeException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	// Listar todos los canjes
	@GetMapping("/listar")
	public ResponseEntity<List<CanjeDTO>> listar() {
		List<CanjeDTO> lista = canjeService.listarCanjes().stream().map(this::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	// Listar por usuario
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<CanjeDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
		List<CanjeDTO> lista = canjeService.listarPorUsuario(usuarioId).stream().map(this::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	// Eliminar canje
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		canjeService.eliminarCanje(id);
		return ResponseEntity.noContent().build();
	}

	private CanjeDTO convertirADTO(Canje canje) {
		return new CanjeDTO(canje.getId(), canje.getRecompensa().getNombre(),
				canje.getUsuario().getNombre() + " " + canje.getUsuario().getApellido(), canje.getPuntosUsados(),
				canje.getFechaCanje());
	}
}
