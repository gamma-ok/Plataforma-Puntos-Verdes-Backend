package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pe.com.puntosverdes.dto.EntregaEvidenciaDTO;
import pe.com.puntosverdes.dto.EntregaHistorialDTO;
import pe.com.puntosverdes.dto.EntregaValidacionDTO;
import pe.com.puntosverdes.dto.EntregaValidadaDTO;
import pe.com.puntosverdes.dto.UltimaEntregaDTO;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.EntregaService;
import pe.com.puntosverdes.service.UsuarioService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entregas")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private UsuarioService usuarioService;

    // Ruta configurada en application.properties
    @Value("${upload.dir}")
    private String uploadDir;

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

    @PutMapping("/{id}/validar")
    public ResponseEntity<EntregaValidadaDTO> validarEntrega(
            @PathVariable Long id,
            @RequestBody EntregaValidacionDTO dto,
            Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
        EntregaValidadaDTO entrega = entregaService.validarEntrega(
                id,
                dto.isValidada(),
                dto.getPuntosGanados(),
                dto.getRespuestaAdmin(),
                dto.getObservaciones(),
                usuario.getId()
        );
        return ResponseEntity.ok(entrega);
    }

    @GetMapping("/ciudadano/{ciudadanoId}/ultima")
    public ResponseEntity<UltimaEntregaDTO> ultimaEntrega(@PathVariable Long ciudadanoId) {
        return ResponseEntity.ok(entregaService.obtenerUltimaEntregaPorCiudadano(ciudadanoId));
    }

    @GetMapping("/ciudadano/{ciudadanoId}/historial")
    public ResponseEntity<List<EntregaHistorialDTO>> historial(@PathVariable Long ciudadanoId) {
        return ResponseEntity.ok(entregaService.listarHistorialPorCiudadano(ciudadanoId));
    }
    
    // 📌 Nuevo endpoint con DTO
    @PostMapping("/{id}/evidencias")
    public ResponseEntity<EntregaEvidenciaDTO> subirEvidencias(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {

        List<String> rutasGuardadas = new ArrayList<>();

        try {
            File directorio = new File(uploadDir);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + fileName;
                file.transferTo(new File(filePath));
                rutasGuardadas.add(filePath);
            }

            Entrega entregaActualizada = entregaService.subirEvidencias(id, rutasGuardadas);

            return ResponseEntity.ok(
                    new EntregaEvidenciaDTO(entregaActualizada.getId(), entregaActualizada.getEvidencias())
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
