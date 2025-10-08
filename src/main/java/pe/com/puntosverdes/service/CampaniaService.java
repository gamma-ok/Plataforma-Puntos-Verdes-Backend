package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Campania;
import java.util.List;
import java.util.Map;

public interface CampaniaService {
    Campania crearCampania(Campania campania);
    Campania obtenerPorId(Long id);
    List<Campania> listarCampanias();
    List<Campania> listarPorEstado(boolean activa);
    List<Campania> buscarCampaniaPorTitulo(String titulo);
    List<Campania> buscarCampaniaPorUbicacion(String ubicacion);
    Campania actualizarCampania(Long id, Campania datos);
    Map<String, Object> obtenerEstadisticas();
}
