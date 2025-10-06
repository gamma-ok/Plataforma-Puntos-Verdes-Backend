package pe.com.puntosverdes.service;

import pe.com.puntosverdes.model.Campania;
import java.util.List;

public interface CampaniaService {
    Campania crearCampania(Campania campania);
    Campania obtenerPorId(Long id);
    List<Campania> listarCampanias();
    List<Campania> listarCampaniasActivas();
    void desactivarCampania(Long id);
    List<Campania> buscarCampaniaPorTitulo(String titulo);
    List<Campania> buscarCampaniaPorUbicacion(String ubicacion);
}
