package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.Notificacion;
import pe.com.puntosverdes.service.NotificacionService;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin("*")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Crear notificación (solo Admin/Municipalidad)
    @PreAuthorize("hasAnyRole('ADMIN','MUNICIPALIDAD')")
    @PostMapping("/")
    public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(notificacionService.crearNotificacion(notificacion));
    }

    // Listar todas las notificaciones de un usuario
    @PreAuthorize("hasAnyRole('ADMIN','MUNICIPALIDAD','RECOLECTOR','CIUDADANO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarNotificacionesPorDestinatario(usuarioId));
    }

    // Listar solo las no leídas
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<Notificacion>> listarNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.listarNoLeidasPorUsuario(usuarioId));
    }

    // Marcar como leída
    @PutMapping("/{notificacionId}/leida")
    public ResponseEntity<?> marcarLeida(@PathVariable Long notificacionId) {
        notificacionService.marcarComoLeida(notificacionId);
        return ResponseEntity.ok("Notificación marcada como leída");
    }
}
