package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Canje;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CanjeRepository extends JpaRepository<Canje, Long> {
    List<Canje> findByUsuarioId(Long usuarioId);
    List<Canje> findByFechaCanjeBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Canje> findByUsuarioNombreContainingIgnoreCaseOrUsuarioApellidoContainingIgnoreCase(String nombre, String apellido);
}
