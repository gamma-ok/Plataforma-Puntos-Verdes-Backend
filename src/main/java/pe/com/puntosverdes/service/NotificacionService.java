package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Notificacion;
import java.util.List;

public interface NotificacionService {
    Notificacion crearNotificacion(Notificacion notificacion);
    List<Notificacion> listarNotificacionesPorDestinatario(Long destinatarioId);
    List<Notificacion> listarNoLeidasPorUsuario(Long destinatarioId);
    void marcarComoLeida(Long notificacionId);
}
