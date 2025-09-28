package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.PuntoVerde;
import pe.com.puntosverdes.service.PuntoVerdeService;

import java.util.List;

@RestController
@RequestMapping("/puntos-verdes")
@CrossOrigin("*")
public class PuntoVerdeController {

    @Autowired
    private PuntoVerdeService puntoVerdeService;

    @PostMapping("/")
    public ResponseEntity<PuntoVerde> registrar(@RequestBody PuntoVerde puntoVerde) {
    	return ResponseEntity.ok(puntoVerdeService.crearPuntoVerde(puntoVerde));
    }

    @GetMapping("/")
    public ResponseEntity<List<PuntoVerde>> listar() {
        return ResponseEntity.ok(puntoVerdeService.listarPuntosVerdes());
    }
}
