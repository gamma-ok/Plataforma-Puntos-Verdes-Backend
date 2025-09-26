package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
	
    Rol findByRolNombre(String rolNombre);
}
