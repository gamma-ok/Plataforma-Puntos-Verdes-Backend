package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.repository.EntregaRepository;
import pe.com.puntosverdes.service.EntregaService;

import java.util.List;

@Service
public class EntregaServiceImpl implements EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Override
    public Entrega registrarEntrega(Entrega entrega) {
        // Aquí podrías calcular puntos según el tipo y cantidad de material reciclado
        return entregaRepository.save(entrega);
    }

    @Override
    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    @Override
    public List<Entrega> listarEntregasPorCiudadano(Long ciudadanoId) {
        return entregaRepository.findByCiudadanoId(ciudadanoId);
    }

    @Override
    public List<Entrega> listarEntregasPorRecolector(Long recolectorId) {
        return entregaRepository.findByRecolectorId(recolectorId);
    }
}
