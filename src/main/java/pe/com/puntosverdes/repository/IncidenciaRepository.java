package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Incidencia;
import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
	List<Incidencia> findByReportadoPorId(Long usuarioId);
	List<Incidencia> findByValidadaTrue();
}
