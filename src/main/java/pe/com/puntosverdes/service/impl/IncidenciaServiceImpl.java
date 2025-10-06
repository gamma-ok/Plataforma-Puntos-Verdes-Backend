package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.IncidenciaRespuestaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.IncidenciaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.IncidenciaService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

	@Autowired
	private IncidenciaRepository incidenciaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Incidencia registrarIncidencia(Incidencia incidencia) {
		return incidenciaRepository.save(incidencia);
	}

	@Override
	public List<Incidencia> listarIncidencias() {
		return incidenciaRepository.findAll();
	}

	@Override
	public List<Incidencia> listarPorUsuario(Long usuarioId) {
		return incidenciaRepository.findByReportadoPorId(usuarioId);
	}

	@Override
	public IncidenciaRespuestaDTO validarIncidencia(Long incidenciaId, IncidenciaValidacionDTO dto, Long adminId) {
		Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
				.orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

		incidencia.setValidada(dto.isValidada());
		incidencia.setObservaciones(dto.getObservaciones());
		incidencia.setFechaValidacion(LocalDateTime.now());
		incidencia.setPuntosGanados(dto.getPuntosGanados());

		Usuario admin = usuarioRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("Admin/Municipalidad no encontrado"));
		incidencia.setValidadoPor(admin);

		if (dto.isValidada() && dto.getPuntosGanados() > 0) {
			Usuario reportante = incidencia.getReportadoPor();
			reportante.setPuntosAcumulados(reportante.getPuntosAcumulados() + dto.getPuntosGanados());
			usuarioRepository.save(reportante);
		}

		Incidencia actualizada = incidenciaRepository.save(incidencia);

		String rolUsuario = actualizada.getReportadoPor().getUsuarioRoles().iterator().next().getRol().getRolNombre();

		return new IncidenciaRespuestaDTO(actualizada.getId(), actualizada.getTitulo(), actualizada.getDescripcion(),
				actualizada.isValidada(), actualizada.getPuntosGanados(), actualizada.getObservaciones(),
				actualizada.getFechaReporte(), actualizada.getFechaValidacion(),
				actualizada.getReportadoPor().getUsername(), rolUsuario);
	}

	@Override
	public Incidencia subirEvidencias(Long incidenciaId, List<String> rutasEvidencias) {
		return incidenciaRepository.findById(incidenciaId).map(incidencia -> {
			if (incidencia.getEvidencias() == null) {
				incidencia.setEvidencias(new ArrayList<>());
			}
			incidencia.getEvidencias().addAll(rutasEvidencias);
			return incidenciaRepository.save(incidencia);
		}).orElseThrow(() -> new RuntimeException("Incidencia no encontrada con id: " + incidenciaId));
	}
}
