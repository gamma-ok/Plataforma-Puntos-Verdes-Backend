package pe.com.puntosverdes.mapper;

import pe.com.puntosverdes.dto.CampaniaDTO;
import pe.com.puntosverdes.dto.CampaniaDetalleDTO;
import pe.com.puntosverdes.dto.EntregaHistorialDTO;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.model.Entrega;

import java.util.List;
import java.util.stream.Collectors;

public class CampaniaMapper {

    // 🔹 Mapeo general (para listar campañas)
    public static CampaniaDTO toDTO(Campania c) {
        String creador = (c.getCreadoPor() != null)
                ? c.getCreadoPor().getNombre() + " " + c.getCreadoPor().getApellido()
                : "Desconocido";

        return new CampaniaDTO(
                c.getId(),
                c.getTitulo(),
                c.getDescripcion(),
                c.getUbicacion(),
                c.isActiva(),
                c.getPuntosExtra(),
                c.getFechaInicio(),
                c.getFechaFin(),
                creador
        );
    }

    // 🔹 Mapeo detallado (para /campanias/{id})
    public static CampaniaDetalleDTO toDetalleDTO(Campania c) {
        CampaniaDetalleDTO dto = new CampaniaDetalleDTO();
        dto.setId(c.getId());
        dto.setTitulo(c.getTitulo());
        dto.setDescripcion(c.getDescripcion());
        dto.setUbicacion(c.getUbicacion());
        dto.setActiva(c.isActiva());
        dto.setPuntosExtra(c.getPuntosExtra());
        dto.setFechaInicio(c.getFechaInicio());
        dto.setFechaFin(c.getFechaFin());

        if (c.getCreadoPor() != null) {
            dto.setCreadoPorNombre(c.getCreadoPor().getNombre() + " " + c.getCreadoPor().getApellido());
            dto.setCreadoPorRol(
                    c.getCreadoPor().getUsuarioRoles().stream()
                            .findFirst()
                            .map(r -> r.getRol().getRolNombre())
                            .orElse("Desconocido")
            );
        }

        // 🔹 Convertir Entregas a EntregaHistorialDTO (sin loops ni proxys)
        List<EntregaHistorialDTO> entregasDTO = c.getEntregas().stream()
                .map(CampaniaMapper::mapEntregaToHistorialDTO)
                .collect(Collectors.toList());
        dto.setEntregas(
                entregasDTO.stream()
                        .map(e -> String.format(
                                "Material: %s | Cantidad: %.2f %s | Fecha: %s | Puntos: %d",
                                e.getMaterial(),
                                e.getCantidad(),
                                "kg",
                                e.getFechaEntrega(),
                                e.getPuntosGanados()
                        ))
                        .collect(Collectors.toList())
        );

        return dto;
    }

    // 🔹 Conversión auxiliar (Entrega → EntregaHistorialDTO)
    private static EntregaHistorialDTO mapEntregaToHistorialDTO(Entrega e) {
        String ubicacion = (e.getPuntoVerde() != null)
                ? e.getPuntoVerde().getDireccion()
                : "Sin ubicación";

        return new EntregaHistorialDTO(
                e.getFechaEntrega(),
                e.getMaterial(),
                e.getCantidad() != null ? e.getCantidad() : 0.0,
                e.getPuntosGanados(),
                ubicacion
        );
    }
}
