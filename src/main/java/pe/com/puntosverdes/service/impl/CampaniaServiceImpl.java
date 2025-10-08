package pe.com.puntosverdes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.repository.CampaniaRepository;
import pe.com.puntosverdes.service.CampaniaService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampaniaServiceImpl implements CampaniaService {

    @Autowired
    private CampaniaRepository campaniaRepository;

    @Override
    public Campania crearCampania(Campania campania) {
        if (campania.getFechaFin() != null && campania.getFechaFin().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha actual.");
        }
        return campaniaRepository.save(campania);
    }

    @Override
    public Campania obtenerPorId(Long id) {
        actualizarCampaniasVencidas();
        return campaniaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Campania> listarCampanias() {
        actualizarCampaniasVencidas();
        return campaniaRepository.findAll();
    }

    @Override
    public List<Campania> listarPorEstado(boolean activa) {
        actualizarCampaniasVencidas();
        return campaniaRepository.findAll().stream()
                .filter(c -> c.isActiva() == activa)
                .toList();
    }

    @Override
    public List<Campania> buscarCampaniaPorTitulo(String titulo) {
        actualizarCampaniasVencidas();
        return campaniaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public List<Campania> buscarCampaniaPorUbicacion(String ubicacion) {
        actualizarCampaniasVencidas();
        return campaniaRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }

    @Override
    public Campania actualizarCampania(Long id, Campania datos) {
        Campania existente = campaniaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaña no encontrada con ID: " + id));

        if (datos.getTitulo() != null) existente.setTitulo(datos.getTitulo());
        if (datos.getDescripcion() != null) existente.setDescripcion(datos.getDescripcion());
        if (datos.getUbicacion() != null) existente.setUbicacion(datos.getUbicacion());
        if (datos.getFechaInicio() != null) existente.setFechaInicio(datos.getFechaInicio());
        if (datos.getFechaFin() != null) existente.setFechaFin(datos.getFechaFin());
        existente.setActiva(datos.isActiva());
        existente.setPuntosExtra(datos.getPuntosExtra());

        return campaniaRepository.save(existente);
    }

    @Override
    public Map<String, Object> obtenerEstadisticas() {
        actualizarCampaniasVencidas();

        long total = campaniaRepository.count();
        long activas = campaniaRepository.countByActivaTrue();
        long inactivas = total - activas;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCampanias", total);
        stats.put("activas", activas);
        stats.put("inactivas", inactivas);
        return stats;
    }

    // Desactivar campañas vencidas
    @Scheduled(cron = "0 0 0 * * ?") // Se ejecuta todos los dias a medianoche
    public void actualizarCampaniasVencidas() {
        List<Campania> todas = campaniaRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        boolean cambios = false;
        for (Campania c : todas) {
            if (c.isActiva() && c.getFechaFin() != null && c.getFechaFin().isBefore(ahora)) {
                c.setActiva(false);
                cambios = true;
            }
        }
        if (cambios) {
            campaniaRepository.saveAll(todas);
            System.out.println("Campañas vencidas desactivadas automaticamente (" + LocalDateTime.now() + ")");
        }
    }
}
