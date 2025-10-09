package pe.com.puntosverdes.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.CanjeRepository;
import pe.com.puntosverdes.repository.RecompensaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.CanjeService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanjeServiceImpl implements CanjeService {

	@Autowired
	private CanjeRepository canjeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RecompensaRepository recompensaRepository;

	@Override
	@Transactional
	public Canje realizarCanje(Usuario usuario, Recompensa recompensa) {
		if (recompensa == null)
			throw new RuntimeException("Recompensa no encontrada.");

		if (!recompensa.isActivo())
			throw new RuntimeException("Esta recompensa no está activa.");

		if (recompensa.getStock() <= 0)
			throw new RuntimeException("La recompensa no tiene stock disponible.");

		int puntosNecesarios = recompensa.getPuntosNecesarios();
		if (usuario.getPuntosAcumulados() < puntosNecesarios)
			throw new RuntimeException("No tienes puntos suficientes para canjear esta recompensa.");

		// Descontar puntos y stock
		usuario.setPuntosAcumulados(usuario.getPuntosAcumulados() - puntosNecesarios);
		recompensa.setStock(recompensa.getStock() - 1);

		usuarioRepository.save(usuario);
		recompensaRepository.save(recompensa);

		// Registrar el canje
		Canje canje = new Canje(usuario, recompensa, puntosNecesarios);
		return canjeRepository.save(canje);
	}

	@Override
	public Canje obtenerPorId(Long id) {
		return canjeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Canje no encontrado con ID: " + id));
	}

	@Override
	public List<Canje> listarCanjes() {
		return canjeRepository.findAll();
	}

	@Override
	public List<Canje> listarPorUsuario(Long usuarioId) {
		return canjeRepository.findByUsuarioId(usuarioId);
	}

	@Override
	public List<Canje> listarPorNombreUsuario(String nombre) {
		return canjeRepository.findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(nombre,
				nombre);
	}

	@Override
	public List<Canje> listarPorRolUsuario(String rol) {
		return canjeRepository.findAll().stream().filter(c -> c.getUsuario() != null && c.getUsuario().getUsuarioRoles()
				.stream().anyMatch(ur -> ur.getRol().getRolNombre().equalsIgnoreCase(rol)))
				.collect(Collectors.toList());
	}

	@Override
	public void eliminarCanje(Long id) {
		canjeRepository.deleteById(id);
	}
}
