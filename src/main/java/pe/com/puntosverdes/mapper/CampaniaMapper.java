package pe.com.puntosverdes.mapper;

import pe.com.puntosverdes.dto.CampaniaDTO;
import pe.com.puntosverdes.dto.CampaniaDetalleDTO;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.model.Usuario;
import java.util.stream.Collectors;

public class CampaniaMapper {

	public static CampaniaDTO toDTO(Campania campania) {
		String creadoPorNombre = "Desconocido";
		Usuario creador = campania.getCreadoPor();
		if (creador != null) {
			creadoPorNombre = creador.getNombre() + " " + creador.getApellido();
		}

		return new CampaniaDTO(campania.getId(), campania.getTitulo(), campania.getDescripcion(),
				campania.getUbicacion(), campania.isActiva(), campania.getPuntosExtra(), campania.getFechaInicio(),
				campania.getFechaFin(), campania.getFechaRegistro(), campania.getFechaActualizacion(), creadoPorNombre);
	}

	public static CampaniaDetalleDTO toDetalleDTO(Campania campania) {
		CampaniaDetalleDTO dto = new CampaniaDetalleDTO();
		dto.setId(campania.getId());
		dto.setTitulo(campania.getTitulo());
		dto.setDescripcion(campania.getDescripcion());
		dto.setUbicacion(campania.getUbicacion());
		dto.setActiva(campania.isActiva());
		dto.setPuntosExtra(campania.getPuntosExtra());
		dto.setFechaInicio(campania.getFechaInicio());
		dto.setFechaFin(campania.getFechaFin());
		dto.setFechaRegistro(campania.getFechaRegistro());
		dto.setFechaActualizacion(campania.getFechaActualizacion());

		if (campania.getCreadoPor() != null) {
			dto.setCreadoPorNombre(campania.getCreadoPor().getNombre() + " " + campania.getCreadoPor().getApellido());
			if (!campania.getCreadoPor().getUsuarioRoles().isEmpty()) {
				dto.setCreadoPorRol(
						campania.getCreadoPor().getUsuarioRoles().iterator().next().getRol().getRolNombre());
			}
		}

		if (campania.getEntregas() != null) {
			dto.setEntregas(campania.getEntregas().stream()
					.map(e -> e.getMaterial() + " (" + e.getCantidad() + " " + e.getUnidad() + ")")
					.collect(Collectors.toList()));
		}

		return dto;
	}
}
