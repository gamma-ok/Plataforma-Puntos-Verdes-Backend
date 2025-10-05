package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Recompensa;
import java.util.List;

@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {
    List<Recompensa> findByActivoTrue();
    long countByActivoTrue();
}
