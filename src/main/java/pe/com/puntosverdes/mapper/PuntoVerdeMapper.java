package pe.com.puntosverdes.mapper;

import pe.com.puntosverdes.dto.PuntoVerdeDTO;
import pe.com.puntosverdes.dto.PuntoVerdeDetalleDTO;
import pe.com.puntosverdes.model.PuntoVerde;

import java.util.List;
import java.util.stream.Collectors;

public class PuntoVerdeMapper {

	public static PuntoVerdeDTO toDTO(PuntoVerde pv) {
		String creador = (pv.getCreadoPor() != null)
				? pv.getCreadoPor().getNombre() + " " + pv.getCreadoPor().getApellido()
				: "Desconocido";

		return new PuntoVerdeDTO(pv.getId(), pv.getNombre(), pv.getDireccion(), pv.getLatitud(), pv.getLongitud(),
				pv.isActivo(), creador);
	}

	public static PuntoVerdeDetalleDTO toDetalleDTO(PuntoVerde pv) {
		PuntoVerdeDetalleDTO dto = new PuntoVerdeDetalleDTO();
		dto.setId(pv.getId());
		dto.setNombre(pv.getNombre());
		dto.setDireccion(pv.getDireccion());
		dto.setLatitud(pv.getLatitud());
		dto.setLongitud(pv.getLongitud());
		dto.setActivo(pv.isActivo());
		dto.setFechaRegistro(pv.getFechaRegistro());
		dto.setCreadoPorNombre(
				pv.getCreadoPor() != null ? pv.getCreadoPor().getNombre() + " " + pv.getCreadoPor().getApellido()
						: "Desconocido");
		dto.setTotalEntregas(pv.getEntregas().size());
		return dto;
	}

	public static List<PuntoVerdeDTO> toDTOList(List<PuntoVerde> lista) {
		return lista.stream().map(PuntoVerdeMapper::toDTO).collect(Collectors.toList());
	}
}
