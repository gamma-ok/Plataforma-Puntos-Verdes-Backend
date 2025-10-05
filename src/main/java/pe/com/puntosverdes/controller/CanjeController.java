package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.*;
import pe.com.puntosverdes.repository.*;
import pe.com.puntosverdes.service.CanjeService;
import pe.com.puntosverdes.service.NotificacionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/canjes")
@CrossOrigin("*")
public class CanjeController {

    @Autowired private CanjeService canjeService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private RecompensaRepository recompensaRepository;
    @Autowired private NotificacionService notificacionService;
    @Autowired private CanjeRepository canjeRepository;

    // Registrar un canje (Ciudadano / Recolector)
    @PreAuthorize("hasAnyRole('CIUDADANO','RECOLECTOR')")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCanje(@RequestParam Long usuarioId, @RequestParam Long recompensaId) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Recompensa> recompensaOpt = recompensaRepository.findById(recompensaId);

        if (usuarioOpt.isEmpty() || recompensaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuario o recompensa no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        Recompensa recompensa = recompensaOpt.get();

        if (!recompensa.isActivo()) {
            return ResponseEntity.badRequest().body("Esta recompensa no está disponible actualmente.");
        }

        if (usuario.getPuntos() < recompensa.getPuntosNecesarios()) {
            return ResponseEntity.badRequest().body("No tienes puntos suficientes para este canje.");
        }

        // Descontar puntos y registrar el canje como pendiente
        usuario.setPuntos(usuario.getPuntos() - recompensa.getPuntosNecesarios());
        Canje nuevoCanje = new Canje(usuario, recompensa, recompensa.getPuntosNecesarios());
        nuevoCanje.setEstado("PENDIENTE");
        canjeService.registrarCanje(nuevoCanje);

        // Notificación al usuario
        notificacionService.crearNotificacion(new Notificacion(
                "Solicitud de canje enviada",
                "Tu solicitud para canjear '" + recompensa.getNombre() + "' está en revisión.",
                usuario,
                false
        ));

        return ResponseEntity.ok("Canje registrado y en espera de aprobación.");
    }

    // Listar canjes de un usuario (Ciudadano / Recolector)
    @PreAuthorize("hasAnyRole('CIUDADANO','RECOLECTOR')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Canje>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(canjeService.listarCanjesPorUsuario(usuarioId));
    }

    // Listar todos los canjes (solo Admin o Municipalidad)
    @PreAuthorize("hasAnyRole('ADMIN','MUNICIPALIDAD')")
    @GetMapping("/")
    public ResponseEntity<List<Canje>> listarTodos() {
        return ResponseEntity.ok(canjeService.listarTodos());
    }

    // Aprobar un canje
    @PreAuthorize("hasAnyRole('ADMIN','MUNICIPALIDAD')")
    @PutMapping("/{canjeId}/aprobar")
    public ResponseEntity<?> aprobarCanje(@PathVariable Long canjeId, @RequestParam String mensaje) {
        Optional<Canje> canjeOpt = canjeRepository.findById(canjeId);
        if (canjeOpt.isEmpty()) return ResponseEntity.badRequest().body("Canje no encontrado.");

        Canje canje = canjeOpt.get();
        canje.setEstado("APROBADO");
        canje.setRespuestaAdmin(mensaje);
        canjeService.registrarCanje(canje);

        // Notificación al usuario
        notificacionService.crearNotificacion(new Notificacion(
                "Canje aprobado",
                "Tu canje de '" + canje.getRecompensa().getNombre() + "' fue aprobado. " + mensaje,
                canje.getUsuario(),
                false
        ));

        return ResponseEntity.ok("Canje aprobado y notificación enviada.");
    }

    // Rechazar un canje
    @PreAuthorize("hasAnyRole('ADMIN','MUNICIPALIDAD')")
    @PutMapping("/{canjeId}/rechazar")
    public ResponseEntity<?> rechazarCanje(@PathVariable Long canjeId, @RequestParam String motivo) {
        Optional<Canje> canjeOpt = canjeRepository.findById(canjeId);
        if (canjeOpt.isEmpty()) return ResponseEntity.badRequest().body("Canje no encontrado.");

        Canje canje = canjeOpt.get();
        canje.setEstado("RECHAZADO");
        canje.setRespuestaAdmin(motivo);
        canjeService.registrarCanje(canje);

        // Notificación al usuario
        notificacionService.crearNotificacion(new Notificacion(
                "Canje rechazado",
                "Tu canje de '" + canje.getRecompensa().getNombre() + "' fue rechazado. Motivo: " + motivo,
                canje.getUsuario(),
                false
        ));

        return ResponseEntity.ok("Canje rechazado y notificación enviada.");
    }
}
