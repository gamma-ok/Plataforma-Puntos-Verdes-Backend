package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.IncidenciaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;
import java.util.List;

public interface IncidenciaService {
    IncidenciaDTO registrarIncidencia(Incidencia incidencia);
    List<IncidenciaDTO> listarIncidencias();
    List<IncidenciaDTO> listarPorUsuario(Long usuarioId);
    List<IncidenciaDTO> listarPorEstado(String estado);
    IncidenciaDTO obtenerPorId(Long id);
    IncidenciaDTO obtenerUltimaPorUsuario(Long usuarioId);
    IncidenciaDTO validarIncidencia(Long incidenciaId, IncidenciaValidacionDTO dto, Long adminId);
    IncidenciaDTO subirArchivos(Long incidenciaId, List<String> nombresArchivos);
}
