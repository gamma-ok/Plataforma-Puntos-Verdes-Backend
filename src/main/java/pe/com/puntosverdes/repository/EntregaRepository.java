package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Entrega;
import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByCiudadanoId(Long ciudadanoId);
    List<Entrega> findByRecolectorId(Long recolectorId);
    List<Entrega> findByEstado(String estado);
    List<Entrega> findByCampaniaId(Long campaniaId);
    List<Entrega> findByPuntoVerdeId(Long puntoVerdeId);
    List<Entrega> findByCampaniaIdAndEstado(Long campaniaId, String estado);
    List<Entrega> findByPuntoVerdeIdAndEstado(Long puntoVerdeId, String estado);
    List<Entrega> findByCampaniaIsNotNull();
    List<Entrega> findByPuntoVerdeIsNotNull();
    List<Entrega> findByCampaniaIsNotNullAndEstado(String estado);
    List<Entrega> findByPuntoVerdeIsNotNullAndEstado(String estado);
    Entrega findTopByCiudadanoIdOrderByFechaEntregaDesc(Long ciudadanoId);
    List<Entrega> findTop3ByCiudadanoIdOrderByFechaEntregaDesc(Long ciudadanoId);
}
