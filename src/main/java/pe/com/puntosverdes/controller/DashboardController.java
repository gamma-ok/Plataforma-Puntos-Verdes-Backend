package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.DashboardResumenDTO;
import pe.com.puntosverdes.repository.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

	@Autowired
	private EntregaRepository entregaRepository;

	@Autowired
	private CanjeRepository canjeRepository;

	@Autowired
	private CampaniaRepository campaniaRepository;

	@Autowired
	private RecompensaRepository recompensaRepository;

	@GetMapping("/resumen")
	public ResponseEntity<DashboardResumenDTO> obtenerResumen() {
		long totalEntregasValidadas = entregaRepository.countByValidadaTrue();
		long totalCanjesAprobados = canjeRepository.countByEstado("APROBADO");
		long totalCampaniasActivas = campaniaRepository.countByActivaTrue();
		long totalRecompensasActivas = recompensaRepository.countByActivoTrue();

		DashboardResumenDTO resumen = new DashboardResumenDTO(totalEntregasValidadas, totalCanjesAprobados,
				totalCampaniasActivas, totalRecompensasActivas);

		return ResponseEntity.ok(resumen);
	}
}
