package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.PuntoVerde;
import pe.com.puntosverdes.repository.PuntoVerdeRepository;
import pe.com.puntosverdes.service.PuntoVerdeService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PuntoVerdeServiceImpl implements PuntoVerdeService {

	@Autowired
	private PuntoVerdeRepository puntoVerdeRepository;

	@Override
	public PuntoVerde crearPuntoVerde(PuntoVerde puntoVerde) {
		if (puntoVerde.getFechaRegistro() == null)
			puntoVerde.setFechaRegistro(LocalDateTime.now());
		puntoVerde.setFechaActualizacion(LocalDateTime.now());
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
	public List<PuntoVerde> listarPorEstado(boolean activo) {
		return activo ? puntoVerdeRepository.findByActivoTrue() : puntoVerdeRepository.findByActivoFalse();
	}

	@Override
	public List<PuntoVerde> buscarPorNombre(String nombre) {
		return puntoVerdeRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	public PuntoVerde actualizarPuntoVerde(Long id, PuntoVerde datos) {
		PuntoVerde existente = puntoVerdeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Punto Verde no encontrado con ID: " + id));

		if (datos.getNombre() != null)
			existente.setNombre(datos.getNombre());
		if (datos.getDireccion() != null)
			existente.setDireccion(datos.getDireccion());
		if (datos.getDescripcion() != null)
			existente.setDescripcion(datos.getDescripcion());
		if (datos.getLatitud() != null)
			existente.setLatitud(datos.getLatitud());
		if (datos.getLongitud() != null)
			existente.setLongitud(datos.getLongitud());

		existente.setActivo(datos.isActivo());

		existente.setFechaActualizacion(LocalDateTime.now());

		return puntoVerdeRepository.save(existente);
	}

	@Override
	public void eliminarPuntoVerde(Long id) {
		PuntoVerde existente = puntoVerdeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Punto Verde no encontrado con ID: " + id));
		puntoVerdeRepository.delete(existente);
	}

	@Override
	public List<PuntoVerde> listarPorCreador(Long usuarioId) {
		return puntoVerdeRepository.findByCreadoPorId(usuarioId);
	}

	@Override
	public List<PuntoVerde> listarPorRolCreador(String rol) {
		return puntoVerdeRepository.findByCreadoPorUsuarioRolesRolRolNombre(rol);
	}
}
