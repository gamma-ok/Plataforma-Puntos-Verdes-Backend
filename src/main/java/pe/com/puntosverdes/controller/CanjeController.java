package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import pe.com.puntosverdes.dto.CanjeResolucionDTO;
import pe.com.puntosverdes.dto.CanjeSolicitudDTO;
import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.CanjeService;
import pe.com.puntosverdes.service.NotificacionService;
import pe.com.puntosverdes.service.RecompensaService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;

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

	@Autowired
	private NotificacionService notificacionService;

	// Solicitar canje (CIUDADANO)
	@PostMapping("/solicitar")
	public ResponseEntity<?> solicitarCanje(@RequestBody CanjeSolicitudDTO dto, Authentication authentication) {
		// Obtener usuario autenticado desde el token JWT
		Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
		Recompensa recompensa = recompensaService.obtenerPorId(dto.getRecompensaId());

		if (recompensa == null) {
			return ResponseEntity.badRequest().body("Recompensa no encontrada");
		}

		if (!recompensa.isActivo()) {
			return ResponseEntity.badRequest().body("Recompensa no disponible");
		}

		if (usuario.getPuntosAcumulados() < recompensa.getPuntosNecesarios()) {
			return ResponseEntity.badRequest().body("No tienes puntos suficientes");
		}

		Canje canje = canjeService.crearCanje(usuario, recompensa);

		// Notificación
		notificacionService.crearNotificacion(new pe.com.puntosverdes.model.Notificacion("Canje solicitado",
				"Tu canje de '" + recompensa.getNombre() + "' fue enviado y está pendiente de revisión.", usuario,
				false));

		return ResponseEntity.ok(canje);
	}

	// Aprobar canje (ADMIN o MUNICIPALIDAD)
	@PutMapping("/{canjeId}/resolver")
	public ResponseEntity<?> resolverCanje(@PathVariable Long canjeId, @RequestBody CanjeResolucionDTO dto) {
		Canje canje;

		if (dto.isAceptado()) {
			canje = canjeService.aprobarCanje(canjeId, dto.getRespuestaAdmin());
			notificacionService
					.crearNotificacion(new pe.com.puntosverdes.model.Notificacion(
							"Canje aprobado", "Tu canje de '" + canje.getRecompensa().getNombre()
									+ "' fue aprobado. Mensaje: " + dto.getRespuestaAdmin(),
							canje.getUsuario(), false));
		} else {
			canje = canjeService.rechazarCanje(canjeId, dto.getMotivo(), dto.getRespuestaAdmin());
			notificacionService
					.crearNotificacion(new pe.com.puntosverdes.model.Notificacion("Canje rechazado",
							"Tu canje de '" + canje.getRecompensa().getNombre() + "' fue rechazado. Motivo: "
									+ dto.getMotivo() + " | Mensaje: " + dto.getRespuestaAdmin(),
							canje.getUsuario(), false));
		}

		return ResponseEntity.ok(canje);
	}

	// Listar todos (ADMIN)
	@GetMapping("/")
	public ResponseEntity<List<Canje>> listarTodos() {
		return ResponseEntity.ok(canjeService.listarCanjes());
	}

	// Listar canjes de un usuario (Ciudadano)
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Canje>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(canjeService.listarPorUsuario(usuarioId));
	}

	// Obtener canje por id
	@GetMapping("/{id}")
	public ResponseEntity<Canje> obtenerPorId(@PathVariable Long id) {
		Canje canje = canjeService.obtenerPorId(id);
		return canje != null ? ResponseEntity.ok(canje) : ResponseEntity.notFound().build();
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<Canje>> buscarCanjes(@RequestParam(required = false) String estado,
			@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,
			@RequestParam(required = false) Long usuarioId) {

		List<Canje> resultados = canjeService.buscarCanjes(estado, fechaInicio, fechaFin, usuarioId);
		return ResponseEntity.ok(resultados);
	}
}
