package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.EntregaHistorialDTO;
import pe.com.puntosverdes.dto.EntregaValidadaDTO;
import pe.com.puntosverdes.dto.UltimaEntregaDTO;
import pe.com.puntosverdes.exception.EntregaNotFoundException;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.EntregaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.EntregaService;

import java.util.ArrayList;
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
    public EntregaValidadaDTO validarEntrega(Long entregaId, boolean validada, int puntosGanados,
                                             String respuestaAdmin, String observaciones, Long recolectorId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntregaNotFoundException("Entrega no encontrada con id: " + entregaId));

        entrega.setValidada(validada);
        entrega.setFechaValidacion(java.time.LocalDateTime.now());
        entrega.setRespuestaAdmin(respuestaAdmin);
        entrega.setObservaciones(observaciones);

        if (recolectorId != null) {
            Usuario recolector = usuarioRepository.findById(recolectorId)
                    .orElseThrow(() -> new EntregaNotFoundException("Recolector no encontrado"));
            entrega.setRecolector(recolector);
        }

        if (validada && puntosGanados > 0) {
            Usuario ciudadano = entrega.getCiudadano();
            int totalPuntos = puntosGanados;

            // 🔥 Si la entrega pertenece a una campaña activa, sumar puntos extra
            if (entrega.getCampania() != null && entrega.getCampania().isActiva()) {
                totalPuntos += entrega.getCampania().getPuntosExtra();
            }

            ciudadano.setPuntosAcumulados(ciudadano.getPuntosAcumulados() + totalPuntos);
            entrega.setPuntosGanados(totalPuntos); // Se guarda el total de puntos ganados
            usuarioRepository.save(ciudadano);
        }

        Entrega actualizada = entregaRepository.save(entrega);

        // Obtener el rol del usuario que realizó la entrega
        String rolUsuario = null;
        if (actualizada.getCiudadano() != null && !actualizada.getCiudadano().getUsuarioRoles().isEmpty()) {
            rolUsuario = actualizada.getCiudadano().getUsuarioRoles()
                    .iterator().next().getRol().getRolNombre();
        }

        // Convertimos a DTO incluyendo el rol
        return new EntregaValidadaDTO(
                actualizada.getId(),
                actualizada.getMaterial(),
                actualizada.getCantidad(),
                actualizada.getPuntosGanados(),
                actualizada.isValidada(),
                actualizada.getRespuestaAdmin(),
                actualizada.getObservaciones(),
                actualizada.getFechaValidacion(),
                actualizada.getCiudadano() != null ? actualizada.getCiudadano().getUsername() : null,
                actualizada.getPuntoVerde() != null ? actualizada.getPuntoVerde().getDireccion() : null,
                rolUsuario
        );
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

    @Override
    public Entrega subirEvidencias(Long entregaId, List<String> rutasEvidencias) {
        return entregaRepository.findById(entregaId).map(entrega -> {
            if (entrega.getEvidencias() == null) {
                entrega.setEvidencias(new ArrayList<>());
            }
            entrega.getEvidencias().addAll(rutasEvidencias);
            return entregaRepository.save(entrega);
        }).orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + entregaId));
    }
}
