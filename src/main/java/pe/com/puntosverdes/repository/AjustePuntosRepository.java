package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.AjustePuntos;
import java.util.List;

@Repository
public interface AjustePuntosRepository extends JpaRepository<AjustePuntos, Long> {
    List<AjustePuntos> findByUsuarioId(Long usuarioId);
}
