package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Entrega;

import java.util.List;

public interface EntregaService {
	
    Entrega registrarEntrega(Entrega entrega);
    List<Entrega> listarEntregas();
    List<Entrega> listarEntregasPorCiudadano(Long ciudadanoId);
    List<Entrega> listarEntregasPorRecolector(Long recolectorId);
    Entrega validarEntrega(Long entregaId, boolean validada, int puntosGanados, String respuestaAdmin, String observaciones, Long recolectorId);
}
