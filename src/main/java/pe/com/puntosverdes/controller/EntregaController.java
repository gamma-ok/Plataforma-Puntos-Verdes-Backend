package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
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
@RequestMapping("/api/entregas")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private UsuarioService usuarioService;

    @Value("${upload.dir:uploads/entregas/}")
    private String uploadDir;

    // Registrar una entrega (Ciudadano o Recolector)
    @PostMapping("/registrar")
    public ResponseEntity<Entrega> registrarEntrega(@RequestBody Entrega entrega, Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
        entrega.setCiudadano(usuario);
        return ResponseEntity.ok(entregaService.registrarEntrega(entrega));
    }

    // Listar TODAS las entregas (Admin/Municipalidad)
    @GetMapping("/listar")
    public ResponseEntity<List<EntregaListadoDTO>> listarTodas() {
        return ResponseEntity.ok(entregaService.listarEntregasDTO());
    }

    // Listar entregas por estado (APROBADA, PENDIENTE, RECHAZADA)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EntregaListadoDTO>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(entregaService.listarEntregasPorEstado(estado));
    }

    // Listar entregas por usuario (Ciudadano o Recolector)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EntregaListadoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(entregaService.listarEntregasPorUsuario(usuarioId));
    }

    // Obtener detalle de una entrega
    @GetMapping("/{entregaId}/detalle")
    public ResponseEntity<EntregaListadoDTO> obtenerEntregaPorId(@PathVariable Long entregaId) {
        return entregaService.listarEntregasDTO().stream()
                .filter(e -> e.getId().equals(entregaId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Resolver entrega (validar o rechazar con una sola consulta)
    @PutMapping("/{id}/resolver")
    public ResponseEntity<EntregaValidadaDTO> resolverEntrega(
            @PathVariable Long id,
            @RequestBody EntregaValidacionDTO dto,
            Authentication authentication
    ) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());

        if (dto.isValidada()) {
            return ResponseEntity.ok(entregaService.validarEntrega(
                    id,
                    true,
                    dto.getPuntosGanados(),
                    dto.getRespuestaAdmin(),
                    dto.getObservaciones(),
                    usuario.getId()
            ));
        } else {
            return ResponseEntity.ok(entregaService.rechazarEntrega(
                    id,
                    dto.getObservaciones(),
                    dto.getRespuestaAdmin()
            ));
        }
    }

    // Ultima entrega del usuario
    @GetMapping("/usuario/{usuarioId}/ultima")
    public ResponseEntity<UltimaEntregaDTO> ultimaEntrega(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(entregaService.obtenerUltimaEntregaPorCiudadano(usuarioId));
    }

    // Historial de entregas del usuario
    @GetMapping("/usuario/{usuarioId}/historial")
    public ResponseEntity<List<EntregaHistorialDTO>> historial(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(entregaService.listarHistorialPorCiudadano(usuarioId));
    }

    // Subir evidencias (Ciudadano o Recolector)
    @PostMapping("/{id}/evidencias")
    public ResponseEntity<Map<String, Object>> subirEvidencias(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files
    ) {
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) directory.mkdirs();

            List<String> rutasEvidencias = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + File.separator + fileName;
                file.transferTo(new File(filePath));
                rutasEvidencias.add(filePath);
            }

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

    // Ver evidencia (publico)
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
