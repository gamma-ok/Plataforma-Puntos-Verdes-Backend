package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.puntosverdes.dto.*;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.EntregaService;
import pe.com.puntosverdes.service.UsuarioService;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/entregas")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private UsuarioService usuarioService;

    // Ruta base para las subidas (definida en application.properties)
    @Value("${upload.dir:uploads/entregas/}")
    private String uploadDir;

    // ======================
    // CRUD BÁSICO
    // ======================

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

    // ======================
    // VALIDAR ENTREGA
    // ======================
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

    // ======================
    // HISTORIAL Y ÚLTIMA ENTREGA
    // ======================
    @GetMapping("/ciudadano/{ciudadanoId}/ultima")
    public ResponseEntity<UltimaEntregaDTO> ultimaEntrega(@PathVariable Long ciudadanoId) {
        return ResponseEntity.ok(entregaService.obtenerUltimaEntregaPorCiudadano(ciudadanoId));
    }

    @GetMapping("/ciudadano/{ciudadanoId}/historial")
    public ResponseEntity<List<EntregaHistorialDTO>> historial(@PathVariable Long ciudadanoId) {
        return ResponseEntity.ok(entregaService.listarHistorialPorCiudadano(ciudadanoId));
    }

    // ======================
    // SUBIR EVIDENCIAS
    // ======================
    @PostMapping("/{id}/evidencias")
    public ResponseEntity<Map<String, Object>> subirEvidencias(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {

        try {
            // Crear carpeta si no existe
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            List<String> rutasEvidencias = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + File.separator + fileName;

                File dest = new File(filePath);
                file.transferTo(dest);

                rutasEvidencias.add(filePath);
            }

            // Registrar rutas en la base de datos
            Entrega actualizada = entregaService.subirEvidencias(id, rutasEvidencias);

            Map<String, Object> response = new HashMap<>();
            response.put("entregaId", actualizada.getId());
            response.put("evidencias", rutasEvidencias);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ======================
    // VISUALIZAR EVIDENCIAS
    // ======================
    @GetMapping("/evidencias/{fileName:.+}")
    public ResponseEntity<Resource> verEvidencia(@PathVariable String fileName) {
        try {
        	Path filePath = Paths.get(uploadDir + File.separator + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
