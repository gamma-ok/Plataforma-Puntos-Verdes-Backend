package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.PuntoVerde;
import java.util.List;

@Repository
public interface PuntoVerdeRepository extends JpaRepository<PuntoVerde, Long> {
    List<PuntoVerde> findByActivoTrue();
    List<PuntoVerde> findByActivoFalse();
    List<PuntoVerde> findByNombreContainingIgnoreCase(String nombre);    List<PuntoVerde> findByCreadoPorId(Long usuarioId);
    List<PuntoVerde> findByCreadoPorUsuarioRolesRolRolNombre(String rol);
}
