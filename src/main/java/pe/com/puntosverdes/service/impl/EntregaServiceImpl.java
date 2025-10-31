package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.*;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.EntregaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.EntregaService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntregaServiceImpl implements EntregaService {

	@Autowired
	private EntregaRepository entregaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public EntregaDTO registrarEntrega(Entrega entrega) {
		entrega.setEstado("PENDIENTE");
		Entrega guardada = entregaRepository.save(entrega);
		return convertirADTO(guardada);
	}

	@Override
	public List<EntregaDTO> listarEntregas() {
		return entregaRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarPorUsuario(Long usuarioId) {
		return entregaRepository.findByCiudadanoId(usuarioId).stream().map(this::convertirADTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarPorEstado(String estado) {
		return entregaRepository.findByEstado(estado).stream().map(this::convertirADTO).collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarRelacionadasConCampanias() {
		return entregaRepository.findByCampaniaIsNotNull().stream().map(this::convertirADTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarRelacionadasConPuntosVerdes() {
		return entregaRepository.findByPuntoVerdeIsNotNull().stream().map(this::convertirADTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarRelacionadasConCampaniasPorEstado(String estado) {
		return entregaRepository.findByCampaniaIsNotNullAndEstado(estado).stream().map(this::convertirADTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarRelacionadasConPuntosVerdesPorEstado(String estado) {
		return entregaRepository.findByPuntoVerdeIsNotNullAndEstado(estado).stream().map(this::convertirADTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<EntregaDTO> listarPorRol(String rol) {
		if (rol == null)
			return List.of();

		String r = rol.trim().toUpperCase();
		return entregaRepository.findAll().stream().filter(e -> {
			if ("CIUDADANO".equalsIgnoreCase(r)) {
				Usuario c = e.getCiudadano();
				return c != null && c.getUsuarioRoles() != null && c.getUsuarioRoles().stream().anyMatch(
						ur -> ur.getRol() != null && ur.getRol().getRolNombre().equalsIgnoreCase("CIUDADANO"));
			} else if ("RECOLECTOR".equalsIgnoreCase(r)) {
				Usuario rec = e.getRecolector();
				return rec != null && rec.getUsuarioRoles() != null && rec.getUsuarioRoles().stream().anyMatch(
						ur -> ur.getRol() != null && ur.getRol().getRolNombre().equalsIgnoreCase("RECOLECTOR"));
			}
			return false;
		}).map(this::convertirADTO).collect(Collectors.toList());
	}

	@Override
	public EntregaDTO validarEntrega(Long entregaId, EntregaValidacionDTO dto, Long adminId) {
		Entrega entrega = entregaRepository.findById(entregaId)
				.orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

		entrega.setEstado(dto.getEstado());
		entrega.setObservaciones(dto.getObservaciones());
		entrega.setRespuestaAdmin(dto.getRespuestaAdmin());
		entrega.setFechaValidacion(LocalDateTime.now());
		entrega.setPuntosGanados(dto.getPuntosGanados());

		Usuario admin = usuarioRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
		entrega.setValidadoPor(admin);

		// Sumar puntos si fue aprobado
		if ("APROBADO".equalsIgnoreCase(dto.getEstado()) && dto.getPuntosGanados() > 0) {
			Usuario ciudadano = entrega.getCiudadano();
			ciudadano.setPuntosAcumulados(ciudadano.getPuntosAcumulados() + dto.getPuntosGanados());
			usuarioRepository.save(ciudadano);
		}

		Entrega actualizada = entregaRepository.save(entrega);
		return convertirADTO(actualizada);
	}

	@Override
	public EntregaDTO subirArchivos(Long entregaId, List<String> nombresArchivos) {
		Entrega entrega = entregaRepository.findById(entregaId)
				.orElseThrow(() -> new RuntimeException("Entrega no encontrada con ID: " + entregaId));
		entrega.getEntregaArchivos().addAll(nombresArchivos);
		return convertirADTO(entregaRepository.save(entrega));
	}

	@Override
	public EntregaDTO obtenerPorId(Long entregaId) {
		return entregaRepository.findById(entregaId).map(this::convertirADTO)
				.orElseThrow(() -> new RuntimeException("Entrega no encontrada con ID: " + entregaId));
	}

	@Override
	public EntregaDTO obtenerUltimaEntregaPorUsuario(Long usuarioId) {
		Entrega ultima = entregaRepository.findTopByCiudadanoIdOrderByFechaEntregaDesc(usuarioId);
		if (ultima == null)
			throw new RuntimeException("No se encontró última entrega");
		return convertirADTO(ultima);
	}

	@Override
	public void eliminarEntrega(Long entregaId) {
		Entrega entrega = entregaRepository.findById(entregaId)
				.orElseThrow(() -> new RuntimeException("Entrega no encontrada con ID: " + entregaId));
		entregaRepository.delete(entrega);
	}

	// Metodo auxiliar para transformar a DTO
	private EntregaDTO convertirADTO(Entrega e) {
		String nombreCiudadano = e.getCiudadano() != null
				? e.getCiudadano().getNombre() + " " + e.getCiudadano().getApellido()
				: null;

		String rolCiudadano = e.getCiudadano() != null && !e.getCiudadano().getUsuarioRoles().isEmpty()
				? e.getCiudadano().getUsuarioRoles().iterator().next().getRol().getRolNombre()
				: null;

		String validadoPor = e.getValidadoPor() != null
				? e.getValidadoPor().getNombre() + " " + e.getValidadoPor().getApellido()
				: null;

		String rolValidador = e.getValidadoPor() != null && !e.getValidadoPor().getUsuarioRoles().isEmpty()
				? e.getValidadoPor().getUsuarioRoles().iterator().next().getRol().getRolNombre()
				: null;

		String puntoVerdeNombre = e.getPuntoVerde() != null ? e.getPuntoVerde().getNombre() : null;
		String puntoVerdeDireccion = e.getPuntoVerde() != null ? e.getPuntoVerde().getDireccion() : null;

		String campaniaTitulo = e.getCampania() != null ? e.getCampania().getTitulo() : null;
		String campaniaUbicacion = e.getCampania() != null ? e.getCampania().getUbicacion() : null;

		return new EntregaDTO(e.getId(), e.getMaterial(), e.getCantidad(), e.getUnidad(), e.getEstado(),
				e.getPuntosGanados(), e.getObservaciones(), e.getRespuestaAdmin(), e.getFechaEntrega(),
				e.getFechaValidacion(), nombreCiudadano, rolCiudadano, validadoPor, rolValidador, puntoVerdeNombre,
				puntoVerdeDireccion, campaniaTitulo, campaniaUbicacion, e.getEntregaArchivos());
	}
	
	@Override
	public List<EntregaDTO> obtenerUltimasPorUsuario(Long usuarioId, int limite) {
	    List<Entrega> entregas = entregaRepository.findTop3ByCiudadanoIdOrderByFechaEntregaDesc(usuarioId);
	    return entregas.stream().map(this::convertirADTO).collect(Collectors.toList());
	}
}
