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
	public PuntoVerde actualizarPuntoVerde(Long id, PuntoVerde datos) {
		PuntoVerde existente = puntoVerdeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Punto Verde no encontrado con id: " + id));

		existente.setNombre(datos.getNombre());
		existente.setDireccion(datos.getDireccion());
		existente.setDescripcion(datos.getDescripcion());
		existente.setLatitud(datos.getLatitud());
		existente.setLongitud(datos.getLongitud());
		return puntoVerdeRepository.save(existente);
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
	public List<PuntoVerde> listarPorEstado(boolean activo) {
		return activo ? puntoVerdeRepository.findByActivoTrue() : puntoVerdeRepository.findByActivoFalse();
	}

	@Override
	public List<PuntoVerde> buscarPorNombre(String nombre) {
		return puntoVerdeRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	public PuntoVerde cambiarEstado(Long id, boolean activo) {
		PuntoVerde punto = puntoVerdeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Punto Verde no encontrado con id: " + id));
		punto.setActivo(activo);
		return puntoVerdeRepository.save(punto);
	}
}
