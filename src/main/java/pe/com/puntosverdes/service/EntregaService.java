package pe.com.puntosverdes.service;

import pe.com.puntosverdes.dto.EntregaHistorialDTO;
import pe.com.puntosverdes.dto.EntregaValidadaDTO;
import pe.com.puntosverdes.dto.UltimaEntregaDTO;
import pe.com.puntosverdes.model.Entrega;

import java.util.List;

public interface EntregaService {

    Entrega registrarEntrega(Entrega entrega);
    List<Entrega> listarEntregas();
    List<Entrega> listarEntregasPorCiudadano(Long ciudadanoId);
    List<Entrega> listarEntregasPorRecolector(Long recolectorId);
    
    EntregaValidadaDTO validarEntrega(Long entregaId, boolean validada, int puntosGanados, String respuestaAdmin, String observaciones, Long recolectorId);
    
    UltimaEntregaDTO obtenerUltimaEntregaPorCiudadano(Long ciudadanoId);
    List<EntregaHistorialDTO> listarHistorialPorCiudadano(Long ciudadanoId);
}
