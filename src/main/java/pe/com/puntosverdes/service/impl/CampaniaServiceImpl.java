package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.repository.CampaniaRepository;
import pe.com.puntosverdes.service.CampaniaService;

import java.util.List;

@Service
public class CampaniaServiceImpl implements CampaniaService {

    @Autowired
    private CampaniaRepository campaniaRepository;

    @Override
    public Campania crearCampania(Campania campania) {
        return campaniaRepository.save(campania);
    }

    @Override
    public Campania obtenerPorId(Long id) {
        return campaniaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Campania> listarCampanias() {
        return campaniaRepository.findAll();
    }

    @Override
    public List<Campania> listarCampaniasActivas() {
        return campaniaRepository.findByActivaTrue();
    }

    @Override
    public void desactivarCampania(Long id) {
        campaniaRepository.findById(id).ifPresent(c -> {
            c.setActiva(false);
            campaniaRepository.save(c);
        });
    }

    @Override
    public List<Campania> buscarCampaniaPorTitulo(String titulo) {
        return campaniaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public List<Campania> buscarCampaniaPorUbicacion(String ubicacion) {
        return campaniaRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }
}
