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

	// EN PROCESO...
}
