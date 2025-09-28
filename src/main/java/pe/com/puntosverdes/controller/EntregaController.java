package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.service.EntregaService;

import java.util.List;

@RestController
@RequestMapping("/entregas")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping("/")
    public ResponseEntity<Entrega> registrarEntrega(@RequestBody Entrega entrega) {
        return ResponseEntity.ok(entregaService.registrarEntrega(entrega));
    }

    @GetMapping("/")
    public ResponseEntity<List<Entrega>> listarEntregas() {
        return ResponseEntity.ok(entregaService.listarEntregas());
    }

    @GetMapping("/ciudadano/{ciudadanoId}")
    public ResponseEntity<List<Entrega>> listarPorCiudadano(@PathVariable Long ciudadanoId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorCiudadano(ciudadanoId));
    }

    @GetMapping("/recolector/{recolectorId}")
    public ResponseEntity<List<Entrega>> listarPorRecolector(@PathVariable Long recolectorId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorRecolector(recolectorId));
    }
}
