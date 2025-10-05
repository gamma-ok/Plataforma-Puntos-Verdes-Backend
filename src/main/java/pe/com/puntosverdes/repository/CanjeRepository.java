package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.puntosverdes.model.Canje;
import java.time.LocalDateTime;
import java.util.List;

public interface CanjeRepository extends JpaRepository<Canje, Long> {
    List<Canje> findByUsuarioId(Long usuarioId);
    List<Canje> findByEstado(String estado);
    List<Canje> findByFechaSolicitudBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Canje> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
