package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.IncidenciaRespuestaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;

import java.util.List;

public interface IncidenciaService {
	
	Incidencia registrarIncidencia(Incidencia incidencia);
	List<Incidencia> listarIncidencias();
	List<Incidencia> listarPorUsuario(Long usuarioId);
	IncidenciaRespuestaDTO validarIncidencia(Long incidenciaId, IncidenciaValidacionDTO dto, Long adminId);
	Incidencia subirEvidencias(Long incidenciaId, List<String> rutasEvidencias);
}
