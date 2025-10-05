package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Entrega;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
	
    List<Entrega> findByCiudadanoId(Long ciudadanoId);
    List<Entrega> findByRecolectorId(Long recolectorId);
    List<Entrega> findByValidadaTrue();
    Entrega findTopByCiudadanoIdOrderByFechaEntregaDesc(Long ciudadanoId);
    long countByValidadaTrue();
}
