package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.PuntoVerde;
import java.util.List;

@Repository
public interface PuntoVerdeRepository extends JpaRepository<PuntoVerde, Long> {
    List<PuntoVerde> findByActivoTrue();
    List<PuntoVerde> findByNombreContainingIgnoreCase(String nombre);
}
