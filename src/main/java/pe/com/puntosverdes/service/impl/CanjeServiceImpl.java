package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Canje;
import pe.com.puntosverdes.repository.CanjeRepository;
import pe.com.puntosverdes.service.CanjeService;

import java.util.List;

@Service
public class CanjeServiceImpl implements CanjeService {

    @Autowired
    private CanjeRepository canjeRepository;

    @Override
    public Canje registrarCanje(Canje canje) {
        return canjeRepository.save(canje);
    }

    @Override
    public List<Canje> listarCanjesPorUsuario(Long usuarioId) {
        return canjeRepository.findByUsuarioId(usuarioId);
    }
}
