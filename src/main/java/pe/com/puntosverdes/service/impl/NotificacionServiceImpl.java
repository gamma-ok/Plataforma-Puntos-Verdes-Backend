package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Notificacion;
import pe.com.puntosverdes.repository.NotificacionRepository;
import pe.com.puntosverdes.service.NotificacionService;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

	@Autowired
	private NotificacionRepository notificacionRepository;

	@Override
	public Notificacion crearNotificacion(Notificacion notificacion) {
		return notificacionRepository.save(notificacion);
	}

	@Override
	public List<Notificacion> listarNotificacionesPorDestinatario(Long destinatarioId) {
		return notificacionRepository.findByDestinatarioId(destinatarioId);
	}

	@Override
	public List<Notificacion> listarNoLeidasPorUsuario(Long destinatarioId) {
		return notificacionRepository.findByDestinatarioIdAndLeidaFalse(destinatarioId);
	}

	@Override
	public void marcarComoLeida(Long notificacionId) {
		notificacionRepository.findById(notificacionId).ifPresent(n -> {
			n.setLeida(true);
			notificacionRepository.save(n);
		});
	}
}
