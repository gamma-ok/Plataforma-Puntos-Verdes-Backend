package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.service.RecompensaService;
import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
@CrossOrigin("*")
public class RecompensaController {

	@Autowired
	private RecompensaService recompensaService;

	@PostMapping("/")
	public ResponseEntity<Recompensa> crear(@RequestBody Recompensa recompensa) {
		return ResponseEntity.ok(recompensaService.crearRecompensa(recompensa));
	}

	@GetMapping("/")
	public ResponseEntity<List<Recompensa>> listar() {
		return ResponseEntity.ok(recompensaService.listarRecompensas());
	}

	@PutMapping("/{id}/estado")
	public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
		recompensaService.actualizarEstado(id, activo);
		return ResponseEntity.ok("Estado de la recompensa actualizado");
	}
}
