package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Rol;
import java.util.List;

public interface RolService {
	Rol crearRol(Rol rol);
	Rol obtenerRolPorNombre(String nombre);
	List<Rol> listarRoles();
}
