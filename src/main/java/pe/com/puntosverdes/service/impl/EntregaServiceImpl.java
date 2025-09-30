package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.EntregaHistorialDTO;
import pe.com.puntosverdes.dto.UltimaEntregaDTO;
import pe.com.puntosverdes.exception.EntregaNotFoundException;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.EntregaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.EntregaService;

import java.util.List;
import java.util.stream.Collectors;

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

            if (recolectorId != null) {
                Usuario recolector = usuarioRepository.findById(recolectorId)
                        .orElseThrow(() -> new EntregaNotFoundException("Recolector no encontrado"));
                entrega.setRecolector(recolector);
            }

            if (validada && puntosGanados > 0) {
                Usuario ciudadano = entrega.getCiudadano();
                ciudadano.setPuntosAcumulados(ciudadano.getPuntosAcumulados() + puntosGanados);
                usuarioRepository.save(ciudadano);
            }

            return entregaRepository.save(entrega);
        }).orElseThrow(() -> new EntregaNotFoundException("Entrega no encontrada con id: " + entregaId));
    }

    @Override
    public UltimaEntregaDTO obtenerUltimaEntregaPorCiudadano(Long ciudadanoId) {
        Entrega ultima = entregaRepository.findTopByCiudadanoIdOrderByFechaEntregaDesc(ciudadanoId);
        if (ultima == null) {
            throw new EntregaNotFoundException("No se encontró última entrega para el ciudadano con id: " + ciudadanoId);
        }
        return new UltimaEntregaDTO(ultima.getMaterial(), ultima.getCantidad(), ultima.getFechaEntrega());
    }

    @Override
    public List<EntregaHistorialDTO> listarHistorialPorCiudadano(Long ciudadanoId) {
        return entregaRepository.findByCiudadanoId(ciudadanoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EntregaHistorialDTO convertirADTO(Entrega entrega) {
        String ubicacion = null;
        if (entrega.getPuntoVerde() != null) {
            ubicacion = entrega.getPuntoVerde().getDireccion();
        } else if (entrega.getCampania() != null) {
            ubicacion = entrega.getCampania().getUbicacion();
        }
        return new EntregaHistorialDTO(
                entrega.getFechaEntrega(),
                entrega.getMaterial(),
                entrega.getCantidad(),
                entrega.getPuntosGanados(),
                ubicacion
        );
    }
}
