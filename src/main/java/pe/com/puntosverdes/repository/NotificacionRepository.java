package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
	
    List<Notificacion> findByDestinatarioId(Long destinatarioId);
    List<Notificacion> findByEsCampanaTrue();
}
