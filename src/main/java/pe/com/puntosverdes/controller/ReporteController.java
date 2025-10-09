package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.service.EntregaService;
import pe.com.puntosverdes.service.RecompensaService;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin("*")
public class ReporteController {

	@Autowired
	private EntregaService entregaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RecompensaService recompensaService;

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
		Map<String, Object> estadisticas = new HashMap<>();
		estadisticas.put("totalUsuarios", usuarioService.listarUsuarios().size());
		estadisticas.put("totalRecompensas", recompensaService.listarRecompensas().size());
		return ResponseEntity.ok(estadisticas);
	}
}
