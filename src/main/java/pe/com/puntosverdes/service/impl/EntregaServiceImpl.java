package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.*;
import pe.com.puntosverdes.exception.EntregaNotFoundException;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.EntregaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.EntregaService;
import java.time.LocalDateTime;
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
        entrega.setFechaEntrega(LocalDateTime.now());
        return entregaRepository.save(entrega);
    }

    // Convertir Entrega a DTO (Listado)
    private EntregaListadoDTO convertirAListadoDTO(Entrega entrega) {
        String estado;
        if (entrega.isValidada()) {
            estado = "APROBADO";
        } else if (entrega.getRespuestaAdmin() != null
                && entrega.getRespuestaAdmin().toLowerCase().contains("rechaz")) {
            estado = "RECHAZADO";
        } else {
            estado = "PENDIENTE";
        }

        return new EntregaListadoDTO(
                entrega.getId(),
                entrega.getMaterial(),
                entrega.getCantidad(),
                estado,
                entrega.getCiudadano() != null ? entrega.getCiudadano().getUsername() : null,
                entrega.getRecolector() != null ? entrega.getRecolector().getUsername() : null,
                entrega.getFechaEntrega(),
                entrega.getFechaValidacion()
        );
    }

    @Override
    public List<EntregaListadoDTO> listarEntregasDTO() {
        return entregaRepository.findAll()
                .stream()
                .map(this::convertirAListadoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EntregaListadoDTO> listarEntregasPorUsuario(Long usuarioId) {
        List<Entrega> entregas = new ArrayList<>();
        entregas.addAll(entregaRepository.findByCiudadanoId(usuarioId));
        entregas.addAll(entregaRepository.findByRecolectorId(usuarioId));
        return entregas.stream().map(this::convertirAListadoDTO).collect(Collectors.toList());
    }

    @Override
    public List<EntregaListadoDTO> listarEntregasPorEstado(String estado) {
        if (estado == null) {
            return listarEntregasDTO();
        }
        String e = estado.trim().toUpperCase();
        switch (e) {
            case "APROBADO":
                return entregaRepository.findAll().stream()
                        .filter(Entrega::isValidada)
                        .map(this::convertirAListadoDTO)
                        .collect(Collectors.toList());
            case "RECHAZADO":
                return entregaRepository.findAll().stream()
                        .filter(ent -> ent.getRespuestaAdmin() != null
                                && ent.getRespuestaAdmin().toLowerCase().contains("rechaz"))
                        .map(this::convertirAListadoDTO)
                        .collect(Collectors.toList());
            case "PENDIENTE":
            default:
                return entregaRepository.findAll().stream()
                        .filter(ent -> !ent.isValidada()
                                && (ent.getRespuestaAdmin() == null || !ent.getRespuestaAdmin().toLowerCase().contains("rechaz")))
                        .map(this::convertirAListadoDTO)
                        .collect(Collectors.toList());
        }
    }

    @Override
    public EntregaValidadaDTO validarEntrega(Long entregaId, boolean validada, int puntosGanados, String respuestaAdmin,
                                             String observaciones, Long recolectorId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntregaNotFoundException("Entrega no encontrada con id: " + entregaId));

        entrega.setValidada(validada);
        entrega.setFechaValidacion(LocalDateTime.now());
        entrega.setRespuestaAdmin(respuestaAdmin);
        entrega.setObservaciones(observaciones);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String validadoPor = (auth != null) ? auth.getName() : "Sistema";
        entrega.setValidadoPor(validadoPor);

        if (recolectorId != null) {
            Usuario recolector = usuarioRepository.findById(recolectorId)
                    .orElseThrow(() -> new EntregaNotFoundException("Recolector no encontrado"));
            entrega.setRecolector(recolector);
        }

        if (validada && puntosGanados > 0) {
            Usuario ciudadano = entrega.getCiudadano();
            int totalPuntos = puntosGanados;

            if (entrega.getCampania() != null && entrega.getCampania().isActiva()) {
                totalPuntos += entrega.getCampania().getPuntosExtra();
            }

            ciudadano.setPuntosAcumulados(ciudadano.getPuntosAcumulados() + totalPuntos);
            entrega.setPuntosGanados(totalPuntos);
            usuarioRepository.save(ciudadano);
        }

        entregaRepository.save(entrega);

        // construir DTO de respuesta
        String rolUsuario = null;
        if (entrega.getCiudadano() != null && !entrega.getCiudadano().getUsuarioRoles().isEmpty()) {
            rolUsuario = entrega.getCiudadano().getUsuarioRoles().iterator().next().getRol().getRolNombre();
        }

        return new EntregaValidadaDTO(
                entrega.getId(),
                entrega.getMaterial(),
                entrega.getCantidad(),
                entrega.getPuntosGanados(),
                entrega.isValidada(),
                entrega.getRespuestaAdmin(),
                entrega.getObservaciones(),
                entrega.getFechaValidacion(),
                entrega.getCiudadano() != null ? entrega.getCiudadano().getUsername() : null,
                entrega.getPuntoVerde() != null ? entrega.getPuntoVerde().getDireccion() : null,
                rolUsuario,
                entrega.getValidadoPor()
        );
    }

    // Método explícito para rechazar (útil desde controlador si quieres separar la ruta)
    public EntregaValidadaDTO rechazarEntrega(Long entregaId, String motivoRechazo, String respuestaAdmin) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntregaNotFoundException("La entrega no encontrado con el ID: " + entregaId));

        entrega.setValidada(false);
        entrega.setFechaValidacion(LocalDateTime.now());
        entrega.setRespuestaAdmin(respuestaAdmin != null ? respuestaAdmin : "La entrega ha sido rechazado");
        entrega.setObservaciones(motivoRechazo);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rechazadoPor = (auth != null) ? auth.getName() : "Sistema";
        entrega.setValidadoPor(rechazadoPor);

        entregaRepository.save(entrega);

        String rolUsuario = null;
        if (entrega.getCiudadano() != null && !entrega.getCiudadano().getUsuarioRoles().isEmpty()) {
            rolUsuario = entrega.getCiudadano().getUsuarioRoles().iterator().next().getRol().getRolNombre();
        }

        return new EntregaValidadaDTO(
                entrega.getId(),
                entrega.getMaterial(),
                entrega.getCantidad(),
                entrega.getPuntosGanados(),
                entrega.isValidada(),
                entrega.getRespuestaAdmin(),
                entrega.getObservaciones(),
                entrega.getFechaValidacion(),
                entrega.getCiudadano() != null ? entrega.getCiudadano().getUsername() : null,
                entrega.getPuntoVerde() != null ? entrega.getPuntoVerde().getDireccion() : null,
                rolUsuario,
                entrega.getValidadoPor()
        );
    }

    @Override
    public UltimaEntregaDTO obtenerUltimaEntregaPorCiudadano(Long ciudadanoId) {
        Entrega ultima = entregaRepository.findTopByCiudadanoIdOrderByFechaEntregaDesc(ciudadanoId);
        if (ultima == null) {
            throw new EntregaNotFoundException("No se encontró última entrega para el ciudadano con ID: " + ciudadanoId);
        }
        return new UltimaEntregaDTO(ultima.getMaterial(), ultima.getCantidad(), ultima.getFechaEntrega());
    }

    @Override
    public List<EntregaHistorialDTO> listarHistorialPorCiudadano(Long ciudadanoId) {
        return entregaRepository.findByCiudadanoId(ciudadanoId)
                .stream()
                .map(e -> new EntregaHistorialDTO(
                        e.getFechaEntrega(),
                        e.getMaterial(),
                        e.getCantidad(),
                        e.getPuntosGanados(),
                        e.getPuntoVerde() != null ? e.getPuntoVerde().getDireccion() :
                                (e.getCampania() != null ? e.getCampania().getUbicacion() : "Ubicación no registrada")
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Entrega subirEvidencias(Long entregaId, List<String> rutasEvidencias) {
        return entregaRepository.findById(entregaId)
                .map(entrega -> {
                    if (entrega.getEvidencias() == null) {
                        entrega.setEvidencias(new ArrayList<>());
                    }
                    entrega.getEvidencias().addAll(rutasEvidencias);
                    return entregaRepository.save(entrega);
                })
                .orElseThrow(() -> new EntregaNotFoundException("La entrega no ha sido encontrado con ID: " + entregaId));
    }
}
