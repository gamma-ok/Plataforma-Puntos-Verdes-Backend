package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Campania;
import java.util.List;

@Repository
public interface CampaniaRepository extends JpaRepository<Campania, Long> {
    List<Campania> findByActivaTrue();
    List<Campania> findByTituloContainingIgnoreCase(String titulo);
    List<Campania> findByUbicacionContainingIgnoreCase(String ubicacion);
    List<Campania> findByCreadoPorId(Long usuarioId);
    long countByActivaTrue();
}
