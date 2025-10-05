package pe.com.puntosverdes.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.model.Recompensa;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.CanjeRepository;
import pe.com.puntosverdes.repository.RecompensaRepository;
import pe.com.puntosverdes.repository.UsuarioRepository;
import pe.com.puntosverdes.service.CanjeService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CanjeServiceImpl implements CanjeService {

    @Autowired
    private CanjeRepository canjeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecompensaRepository recompensaRepository;

    @Override
    @Transactional
    public Canje crearCanje(Usuario usuario, Recompensa recompensa) {
        // Asumimos que el controller ya verificó existencia y puntos, pero guardamos puntosUsados aquí
        Canje canje = new Canje();
        canje.setUsuario(usuario);
        canje.setRecompensa(recompensa);
        canje.setFechaSolicitud(LocalDateTime.now());
        canje.setEstado("PENDIENTE");
        canje.setPuntosUsados(recompensa.getPuntosNecesarios());

        return canjeRepository.save(canje);
    }

    @Override
    @Transactional
    public Canje aprobarCanje(Long canjeId, String respuestaAdmin) {
        Canje canje = canjeRepository.findById(canjeId)
                .orElseThrow(() -> new RuntimeException("Canje no encontrado con id: " + canjeId));

        Usuario usuario = canje.getUsuario();
        Recompensa recompensa = canje.getRecompensa();

        int puntosNecesarios = recompensa.getPuntosNecesarios();
        if (usuario.getPuntosAcumulados() < puntosNecesarios) {
            throw new RuntimeException("El usuario no tiene puntos suficientes para este canje.");
        }

        usuario.setPuntosAcumulados(usuario.getPuntosAcumulados() - puntosNecesarios);
        usuarioRepository.save(usuario);

        canje.setEstado("APROBADO");
        canje.setRespuestaAdmin(respuestaAdmin);
        canje.setFechaResolucion(LocalDateTime.now());
        return canjeRepository.save(canje);
    }

    @Override
    @Transactional
    public Canje rechazarCanje(Long canjeId, String motivo, String respuestaAdmin) {
        Canje canje = canjeRepository.findById(canjeId)
                .orElseThrow(() -> new RuntimeException("Canje no encontrado con id: " + canjeId));

        canje.setEstado("RECHAZADO");
        canje.setMotivoRechazo(motivo);
        canje.setRespuestaAdmin(respuestaAdmin);
        canje.setFechaResolucion(LocalDateTime.now());
        return canjeRepository.save(canje);
    }

    @Override
    public Canje obtenerPorId(Long id) {
        return canjeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Canje> listarCanjes() {
        return canjeRepository.findAll();
    }

    @Override
    public List<Canje> listarPorUsuario(Long usuarioId) {
        return canjeRepository.findByUsuarioId(usuarioId);
    }
}
