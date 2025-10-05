package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.puntosverdes.dto.IncidenciaRespuestaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.repository.IncidenciaRepository;
import pe.com.puntosverdes.service.IncidenciaService;
import pe.com.puntosverdes.service.UsuarioService;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/incidencias")
@CrossOrigin("*")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Value("${upload.incidencias.dir}")
    private String uploadDir;

    // =======================
    // REGISTRAR INCIDENCIA
    // =======================
    @PostMapping("/")
    public ResponseEntity<Incidencia> registrar(@RequestBody Incidencia incidencia, Authentication auth) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(auth.getName());

        // Solo recolectores pueden registrar incidencias
        boolean esRecolector = usuario.getUsuarioRoles().stream()
                .anyMatch(r -> r.getRol().getRolNombre().equalsIgnoreCase("RECOLECTOR"));

        if (!esRecolector) {
            return ResponseEntity.status(403).build(); // FORBIDDEN
        }

        incidencia.setReportadoPor(usuario);
        return ResponseEntity.ok(incidenciaService.registrarIncidencia(incidencia));
    }

    // =======================
    // LISTAR INCIDENCIAS
    // =======================
    @GetMapping("/")
    public ResponseEntity<List<Incidencia>> listar() {
        return ResponseEntity.ok(incidenciaService.listarIncidencias());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Incidencia>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(incidenciaService.listarPorUsuario(usuarioId));
    }

    // =======================
    // VALIDAR INCIDENCIA
    // =======================
    @PutMapping("/{id}/validar")
    public ResponseEntity<IncidenciaRespuestaDTO> validar(
            @PathVariable Long id,
            @RequestBody IncidenciaValidacionDTO dto,
            Authentication auth) {

        Usuario admin = usuarioService.obtenerUsuarioPorUsername(auth.getName());
        return ResponseEntity.ok(incidenciaService.validarIncidencia(id, dto, admin.getId()));
    }

    // =======================
    // SUBIR EVIDENCIAS
    // =======================
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

            // Guardar archivos
            List<String> rutasEvidencias = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + fileName;

                File dest = new File(filePath);
                file.transferTo(dest);

                rutasEvidencias.add(fileName);
            }

            // Actualizar en BD
            Incidencia actualizada = incidenciaService.subirEvidencias(id, rutasEvidencias);

            return ResponseEntity.ok(Map.of(
                    "incidenciaId", actualizada.getId(),
                    "evidencias", rutasEvidencias
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // =======================
    // VISUALIZAR EVIDENCIAS
    // =======================
    @GetMapping("/evidencias/{fileName:.+}")
    public ResponseEntity<Resource> verEvidencia(@PathVariable String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
