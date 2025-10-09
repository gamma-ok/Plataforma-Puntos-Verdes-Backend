package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.IncidenciaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;
import pe.com.puntosverdes.repository.IncidenciaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.IncidenciaService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

	@Autowired
	private IncidenciaRepository incidenciaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public IncidenciaDTO registrarIncidencia(Incidencia incidencia) {
		incidencia.setEstado("PENDIENTE");
		return convertirADTO(incidenciaRepository.save(incidencia));
	}

	@Override
	public List<IncidenciaDTO> listarIncidencias() {
		return incidenciaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
	}

	@Override
	public List<IncidenciaDTO> listarPorUsuario(Long usuarioId) {
		return incidenciaRepository.findByReportadoPorId(usuarioId).stream().map(this::convertirADTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<IncidenciaDTO> listarPorEstado(String estado) {
		return incidenciaRepository.findByEstado(estado).stream().map(this::convertirADTO).collect(Collectors.toList());
	}

	@Override
	public IncidenciaDTO obtenerPorId(Long id) {
		return incidenciaRepository.findById(id).map(this::convertirADTO)
				.orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));
	}

	@Override
	public IncidenciaDTO obtenerUltimaPorUsuario(Long usuarioId) {
		Incidencia ultima = incidenciaRepository.findTopByReportadoPorIdOrderByFechaReporteDesc(usuarioId);
		if (ultima == null)
			throw new RuntimeException("No hay incidencias reportadas por este usuario");
		return convertirADTO(ultima);
	}

	@Override
	public IncidenciaDTO validarIncidencia(Long incidenciaId, IncidenciaValidacionDTO dto, Long adminId) {
		Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
				.orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));
		incidencia.setEstado(dto.getEstado());
		incidencia.setObservacionAdmin(dto.getObservaciones());
		incidencia.setRespuesta(dto.getRespuesta());
		incidencia.setFechaRespuesta(LocalDateTime.now());
		incidencia.setValidadoPor(
				usuarioRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin no encontrado")));
		return convertirADTO(incidenciaRepository.save(incidencia));
	}

	@Override
	public IncidenciaDTO subirArchivos(Long incidenciaId, List<String> nombresArchivos) {
		Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
				.orElseThrow(() -> new RuntimeException("Incidencia no encontrada con id: " + incidenciaId));
		incidencia.getIncidenciasArchivos().addAll(nombresArchivos);
		return convertirADTO(incidenciaRepository.save(incidencia));
	}

	private IncidenciaDTO convertirADTO(Incidencia i) {
		String reportadoPor = (i.getReportadoPor() != null)
				? i.getReportadoPor().getNombre() + " " + i.getReportadoPor().getApellido()
				: null;

		String rolReportado = (i.getReportadoPor() != null && !i.getReportadoPor().getUsuarioRoles().isEmpty())
				? i.getReportadoPor().getUsuarioRoles().iterator().next().getRol().getRolNombre()
				: null;

		String validadoPor = (i.getValidadoPor() != null)
				? i.getValidadoPor().getNombre() + " " + i.getValidadoPor().getApellido()
				: null;

		String rolValidado = (i.getValidadoPor() != null && !i.getValidadoPor().getUsuarioRoles().isEmpty())
				? i.getValidadoPor().getUsuarioRoles().iterator().next().getRol().getRolNombre()
				: null;

		return new IncidenciaDTO(i.getId(), i.getTitulo(), i.getDescripcion(), i.getEstado(), i.getObservacionAdmin(),
				i.getRespuesta(), i.getFechaReporte(), i.getFechaRespuesta(), reportadoPor, rolReportado, validadoPor,
				rolValidado, i.getIncidenciasArchivos());
	}
}
