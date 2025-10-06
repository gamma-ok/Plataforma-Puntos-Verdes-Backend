package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.PuntoVerde;
import pe.com.puntosverdes.repository.PuntoVerdeRepository;
import pe.com.puntosverdes.service.PuntoVerdeService;
import java.util.List;

@Service
public class PuntoVerdeServiceImpl implements PuntoVerdeService {

	@Autowired
	private PuntoVerdeRepository puntoVerdeRepository;

	@Override
	public PuntoVerde crearPuntoVerde(PuntoVerde puntoVerde) {
		return puntoVerdeRepository.save(puntoVerde);
	}

	@Override
	public PuntoVerde obtenerPorId(Long id) {
		return puntoVerdeRepository.findById(id).orElse(null);
	}

	@Override
	public List<PuntoVerde> listarPuntosVerdes() {
		return puntoVerdeRepository.findAll();
	}

	@Override
	public void desactivarPuntoVerde(Long id) {
		PuntoVerde puntoVerde = puntoVerdeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Punto Verde no encontrado con id: " + id));
		puntoVerde.setActivo(false);
		puntoVerdeRepository.save(puntoVerde);
	}

	@Override
	public List<PuntoVerde> listarPuntosActivos() {
		return puntoVerdeRepository.findByActivoTrue();
	}
}
