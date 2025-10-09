package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.CampaniaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.CampaniaService;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampaniaServiceImpl implements CampaniaService {

	@Autowired
	private CampaniaRepository campaniaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Campania crearCampania(Campania campania) {
		campania.setActiva(true);
		campania.setFechaRegistro(LocalDateTime.now());
		return campaniaRepository.save(campania);
	}

	@Override
	public Campania obtenerPorId(Long id) {
		actualizarCampaniasVencidas();
		return campaniaRepository.findById(id).orElse(null);
	}

	@Override
	public List<Campania> listarCampanias() {
		actualizarCampaniasVencidas();
		return campaniaRepository.findAll();
	}

	@Override
	public List<Campania> listarPorEstado(boolean activa) {
		actualizarCampaniasVencidas();
		return campaniaRepository.findAll().stream().filter(c -> c.isActiva() == activa).collect(Collectors.toList());
	}

	@Override
	public List<Campania> listarPorUsuario(Long usuarioId) {
		return campaniaRepository.findByCreadoPorId(usuarioId);
	}

	@Override
	public List<Campania> listarPorRol(String rol) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return campaniaRepository.findAll().stream().filter(c -> c.getCreadoPor() != null && c.getCreadoPor()
				.getUsuarioRoles().stream().anyMatch(r -> r.getRol().getRolNombre().equalsIgnoreCase(rol)))
				.collect(Collectors.toList());
	}

	@Override
	public List<Campania> buscarCampaniaPorTitulo(String titulo) {
		return campaniaRepository.findByTituloContainingIgnoreCase(titulo);
	}

	@Override
	public List<Campania> buscarCampaniaPorUbicacion(String ubicacion) {
		return campaniaRepository.findByUbicacionContainingIgnoreCase(ubicacion);
	}

	@Override
	public Campania actualizarCampania(Long id, Campania datos) {
		Campania existente = campaniaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Campaña no encontrada con ID: " + id));

		if (datos.getTitulo() != null)
			existente.setTitulo(datos.getTitulo());
		if (datos.getDescripcion() != null)
			existente.setDescripcion(datos.getDescripcion());
		if (datos.getUbicacion() != null)
			existente.setUbicacion(datos.getUbicacion());
		if (datos.getFechaInicio() != null)
			existente.setFechaInicio(datos.getFechaInicio());
		if (datos.getFechaFin() != null)
			existente.setFechaFin(datos.getFechaFin());
		existente.setActiva(datos.isActiva());
		existente.setPuntosExtra(datos.getPuntosExtra());
		existente.setFechaActualizacion(LocalDateTime.now());

		return campaniaRepository.save(existente);
	}

	@Override
	public Map<String, Object> obtenerEstadisticas() {
		long total = campaniaRepository.count();
		long activas = campaniaRepository.countByActivaTrue();
		long inactivas = total - activas;

		Map<String, Object> stats = new HashMap<>();
		stats.put("totalCampanias", total);
		stats.put("activas", activas);
		stats.put("inactivas", inactivas);
		return stats;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void actualizarCampaniasVencidas() {
		List<Campania> todas = campaniaRepository.findAll();
		LocalDateTime ahora = LocalDateTime.now();
		boolean cambios = false;

		for (Campania c : todas) {
			if (c.isActiva() && c.getFechaFin() != null && c.getFechaFin().isBefore(ahora)) {
				c.setActiva(false);
				cambios = true;
			}
		}
		if (cambios)
			campaniaRepository.saveAll(todas);
	}

	@Override
	public void eliminarCampania(Long id) {
		Campania existente = campaniaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Campaña no encontrada con ID: " + id));
		campaniaRepository.delete(existente);
	}
}
