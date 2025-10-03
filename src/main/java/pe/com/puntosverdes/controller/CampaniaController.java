package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.service.CampaniaService;

import java.util.List;

@RestController
@RequestMapping("/campanias")
@CrossOrigin("*")
public class CampaniaController {

    @Autowired
    private CampaniaService campaniaService;

    @PostMapping("/")
    public ResponseEntity<Campania> registrar(@RequestBody Campania campania) {
        return ResponseEntity.ok(campaniaService.crearCampania(campania));
    }

    @GetMapping("/")
    public ResponseEntity<List<Campania>> listar() {
        return ResponseEntity.ok(campaniaService.listarCampanias());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Campania>> listarActivas() {
        return ResponseEntity.ok(campaniaService.listarCampaniasActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campania> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(campaniaService.obtenerPorId(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        campaniaService.desactivarCampania(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/titulo/{titulo}")
    public ResponseEntity<List<Campania>> buscarPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(campaniaService.buscarCampaniaPorTitulo(titulo));
    }

    @GetMapping("/buscar/ubicacion/{ubicacion}")
    public ResponseEntity<List<Campania>> buscarPorUbicacion(@PathVariable String ubicacion) {
        return ResponseEntity.ok(campaniaService.buscarCampaniaPorUbicacion(ubicacion));
    }
}
