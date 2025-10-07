package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.CanjeDTO;
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

	@Autowired
	private NotificacionService notificacionService;

	@PostMapping("/solicitar")
	public ResponseEntity<?> solicitarCanje(@RequestBody CanjeSolicitudDTO dto, Authentication authentication) {
		Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
		Recompensa recompensa = recompensaService.obtenerPorId(dto.getRecompensaId());

		if (recompensa == null)
			return ResponseEntity.badRequest().body("Recompensa no encontrada");

		if (!recompensa.isActivo())
			return ResponseEntity.badRequest().body("Recompensa no disponible");

		if (usuario.getPuntosAcumulados() < recompensa.getPuntosNecesarios())
			return ResponseEntity.badRequest().body("No tienes puntos suficientes");

		Canje canje = canjeService.crearCanje(usuario, recompensa);

		notificacionService.crearNotificacion(new pe.com.puntosverdes.model.Notificacion("Canje solicitado",
				"Tu canje de '" + recompensa.getNombre() + "' fue enviado y está pendiente de revisión.", usuario,
				false));

		CanjeDTO dtoRespuesta = convertirADTO(canje);
		return ResponseEntity.ok(dtoRespuesta);
	}

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

		CanjeDTO dtoRespuesta = convertirADTO(canje);
		return ResponseEntity.ok(dtoRespuesta);
	}

	@GetMapping("/")
	public ResponseEntity<List<CanjeDTO>> listarTodos() {
		List<CanjeDTO> lista = canjeService.listarCanjes().stream().map(this::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<CanjeDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
		List<CanjeDTO> lista = canjeService.listarPorUsuario(usuarioId).stream().map(this::convertirADTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CanjeDTO> obtenerPorId(@PathVariable Long id) {
		Canje canje = canjeService.obtenerPorId(id);
		if (canje == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(convertirADTO(canje));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<CanjeDTO>> buscarCanjes(@RequestParam(required = false) String estado,
			@RequestParam(required = false) String fechaInicio, @RequestParam(required = false) String fechaFin,
			@RequestParam(required = false) Long usuarioId) {

		List<CanjeDTO> resultados = canjeService.buscarCanjes(estado, fechaInicio, fechaFin, usuarioId).stream()
				.map(this::convertirADTO).collect(Collectors.toList());

		return ResponseEntity.ok(resultados);
	}

	private CanjeDTO convertirADTO(Canje canje) {
		return new CanjeDTO(canje.getId(), canje.getEstado(),
				canje.getRecompensa() != null ? canje.getRecompensa().getNombre() : "N/A",
				canje.getUsuario() != null ? (canje.getUsuario().getNombre() + " " + canje.getUsuario().getApellido())
						: "Desconocido",
				canje.getPuntosUsados(), canje.getFechaSolicitud(), canje.getFechaResolucion(),
				canje.getRespuestaAdmin(), canje.getMotivoRechazo());
	}
}
