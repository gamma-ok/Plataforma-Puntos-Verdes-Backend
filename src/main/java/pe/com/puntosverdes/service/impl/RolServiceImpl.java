package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Rol;
import pe.com.puntosverdes.repository.RolRepository;
import pe.com.puntosverdes.service.RolService;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	private RolRepository rolRepository;

	@Override
	public Rol crearRol(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	public Rol obtenerRolPorNombre(String nombre) {
		return rolRepository.findByRolNombre(nombre);
	}

	@Override
	public List<Rol> listarRoles() {
		return rolRepository.findAll();
	}
}
