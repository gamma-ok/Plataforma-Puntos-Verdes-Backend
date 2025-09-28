package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.Notificacion;
import pe.com.puntosverdes.service.NotificacionService;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin("*")
public class NotificacionController {

	@Autowired
	private NotificacionService notificacionService;

	@PostMapping("/")
	public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
		return ResponseEntity.ok(notificacionService.crearNotificacion(notificacion));
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Notificacion>> listarPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
		return ResponseEntity.ok(notificacionService.listarNotificacionesPorDestinatario(usuarioId));
	}
}
