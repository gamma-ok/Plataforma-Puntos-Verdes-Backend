package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.repository.RecompensaRepository;
import pe.com.puntosverdes.service.RecompensaService;
import java.util.List;

@Service
public class RecompensaServiceImpl implements RecompensaService {

	@Autowired
	private RecompensaRepository recompensaRepository;

	@Override
	public Recompensa crearRecompensa(Recompensa recompensa) {
		return recompensaRepository.save(recompensa);
	}

	@Override
	public List<Recompensa> listarRecompensas() {
		return recompensaRepository.findAll();
	}

	@Override
	public Recompensa obtenerPorId(Long id) {
		return recompensaRepository.findById(id).orElse(null);
	}

	@Override
	public void actualizarEstado(Long id, boolean activo) {
		Recompensa r = obtenerPorId(id);
		if (r != null) {
			r.setActivo(activo);
			recompensaRepository.save(r);
		}
	}

	@Override
	public void eliminarRecompensa(Long id) {
		recompensaRepository.deleteById(id);
	}
}
