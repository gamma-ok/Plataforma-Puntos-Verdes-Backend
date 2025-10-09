package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.dto.RecompensaDTO;
import pe.com.puntosverdes.exception.RecompensaNotFoundException;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.RecompensaRepository;
import pe.com.puntosverdes.service.RecompensaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecompensaServiceImpl implements RecompensaService {

	@Autowired
	private RecompensaRepository recompensaRepository;

	@Override
	public Recompensa crearRecompensa(Recompensa recompensa) {
		return recompensaRepository.save(recompensa);
	}

	@Override
	public Recompensa actualizarRecompensa(Long id, Recompensa datos) {
		Recompensa existente = recompensaRepository.findById(id)
				.orElseThrow(() -> new RecompensaNotFoundException("Recompensa no encontrada con ID: " + id));

		if (datos.getNombre() != null)
			existente.setNombre(datos.getNombre());
		if (datos.getDescripcion() != null)
			existente.setDescripcion(datos.getDescripcion());
		if (datos.getPuntosNecesarios() > 0)
			existente.setPuntosNecesarios(datos.getPuntosNecesarios());
		existente.setStock(datos.getStock());
		existente.setActivo(datos.isActivo());

		return recompensaRepository.save(existente);
	}

	@Override
	public List<Recompensa> listarRecompensas() {
		return recompensaRepository.findAll();
	}

	@Override
	public List<Recompensa> listarPorEstado(boolean activo) {
		return recompensaRepository.findByActivo(activo);
	}

	@Override
	public Recompensa obtenerPorId(Long id) {
		return recompensaRepository.findById(id)
				.orElseThrow(() -> new RecompensaNotFoundException("Recompensa no encontrada con ID: " + id));
	}

	@Override
	public void eliminarRecompensa(Long id) {
		Recompensa r = obtenerPorId(id);
		recompensaRepository.deleteById(r.getId());
	}

	@Override
	public List<Recompensa> buscarPorNombre(String nombre) {
		return recompensaRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	public List<Recompensa> listarPorCreadorNombre(String nombreCompleto) {
		return recompensaRepository.findByCreadoPorNombreContainingIgnoreCaseOrCreadoPorApellidoContainingIgnoreCase(
				nombreCompleto, nombreCompleto);
	}

	@Override
	public List<Recompensa> listarPorRolCreador(String rolNombre) {
		return recompensaRepository.findByCreadoPorUsuarioRolesRolRolNombreIgnoreCase(rolNombre);
	}

	@Override
	public RecompensaDTO convertirADTO(Recompensa recompensa) {
		String creadoPorNombre = null;
		Usuario creador = recompensa.getCreadoPor();
		if (creador != null) {
			creadoPorNombre = creador.getNombre() + " " + creador.getApellido();
		}
		return new RecompensaDTO(recompensa.getId(), recompensa.getNombre(), recompensa.getDescripcion(),
				recompensa.getPuntosNecesarios(), recompensa.getStock(), recompensa.isActivo(), creadoPorNombre,
				recompensa.getFechaCreacion(), recompensa.getFechaActualizacion());
	}

	@Override
	public Map<String, Object> obtenerEstadisticas() {
		Map<String, Object> stats = new HashMap<>();
		List<Recompensa> todas = recompensaRepository.findAll();

		long total = todas.size();
		long activas = todas.stream().filter(Recompensa::isActivo).count();
		long inactivas = total - activas;

		String masReclamada = todas.stream().findFirst().map(Recompensa::getNombre).orElse("N/A");
		String menosReclamada = todas.stream().skip(todas.size() > 1 ? todas.size() - 1 : 0).findFirst()
				.map(Recompensa::getNombre).orElse("N/A");

		stats.put("totalRecompensas", total);
		stats.put("activas", activas);
		stats.put("inactivas", inactivas);
		stats.put("masReclamada", masReclamada);
		stats.put("menosReclamada", menosReclamada);
		return stats;
	}
}
