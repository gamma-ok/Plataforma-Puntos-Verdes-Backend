package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.*;
import pe.com.puntosverdes.model.Entrega;
import java.util.List;

public interface EntregaService {
    Entrega registrarEntrega(Entrega entrega);
    List<EntregaListadoDTO> listarEntregasDTO();
    List<EntregaListadoDTO> listarEntregasPorUsuario(Long usuarioId);
    List<EntregaListadoDTO> listarEntregasPorEstado(String estado); // 🔹 Añadido
    EntregaValidadaDTO validarEntrega(Long entregaId, boolean validada, int puntosGanados,
                                       String respuestaAdmin, String observaciones, Long recolectorId);
    EntregaValidadaDTO rechazarEntrega(Long entregaId, String motivoRechazo, String respuestaAdmin); // 🔹 Añadido también
    UltimaEntregaDTO obtenerUltimaEntregaPorCiudadano(Long ciudadanoId);
    List<EntregaHistorialDTO> listarHistorialPorCiudadano(Long ciudadanoId);
    Entrega subirEvidencias(Long entregaId, List<String> rutasEvidencias);
}
