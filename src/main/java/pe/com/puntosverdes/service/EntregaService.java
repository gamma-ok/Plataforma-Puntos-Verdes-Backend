package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.*;
import java.util.List;

public interface EntregaService {
    EntregaDTO registrarEntrega(pe.com.puntosverdes.model.Entrega entrega);
    List<EntregaDTO> listarEntregas();
    List<EntregaDTO> listarPorUsuario(Long usuarioId);
    List<EntregaDTO> listarPorEstado(String estado);
    List<EntregaDTO> listarRelacionadasConCampanias();
    List<EntregaDTO> listarRelacionadasConPuntosVerdes();
    List<EntregaDTO> listarRelacionadasConCampaniasPorEstado(String estado);
    List<EntregaDTO> listarRelacionadasConPuntosVerdesPorEstado(String estado);
    List<EntregaDTO> listarPorRol(String rol);
    EntregaDTO validarEntrega(Long entregaId, EntregaValidacionDTO dto, Long adminId);
    EntregaDTO subirArchivos(Long entregaId, List<String> nombresArchivos);
    EntregaDTO obtenerPorId(Long entregaId);
    EntregaDTO obtenerUltimaEntregaPorUsuario(Long usuarioId);
    void eliminarEntrega(Long entregaId);
}
