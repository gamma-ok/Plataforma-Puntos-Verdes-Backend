package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.EntregaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.EntregaService;

import java.util.List;

@Service
public class EntregaServiceImpl implements EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Entrega registrarEntrega(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    @Override
    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    @Override
    public List<Entrega> listarEntregasPorCiudadano(Long ciudadanoId) {
        return entregaRepository.findByCiudadanoId(ciudadanoId);
    }

    @Override
    public List<Entrega> listarEntregasPorRecolector(Long recolectorId) {
        return entregaRepository.findByRecolectorId(recolectorId);
    }

    @Override
    public Entrega validarEntrega(Long entregaId, boolean validada, int puntosGanados, String respuestaAdmin, String observaciones, Long recolectorId) {
        return entregaRepository.findById(entregaId).map(entrega -> {
            entrega.setValidada(validada);
            entrega.setFechaValidacion(java.time.LocalDateTime.now());
            entrega.setPuntosGanados(puntosGanados);
            entrega.setRespuestaAdmin(respuestaAdmin);
            entrega.setObservaciones(observaciones);

            // si hay un recolector/admin asignado que validó
            if (recolectorId != null) {
                Usuario recolector = usuarioRepository.findById(recolectorId)
                        .orElseThrow(() -> new RuntimeException("Recolector no encontrado"));
                entrega.setRecolector(recolector);
            }

            // si se valida, se suman los puntos al ciudadano
            if (validada && puntosGanados > 0) {
                Usuario ciudadano = entrega.getCiudadano();
                ciudadano.setPuntosAcumulados(ciudadano.getPuntosAcumulados() + puntosGanados);
                usuarioRepository.save(ciudadano); // persistir cambios
            }

            return entregaRepository.save(entrega);
        }).orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + entregaId));
    }
}
