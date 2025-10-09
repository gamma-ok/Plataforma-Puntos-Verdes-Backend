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
import pe.com.puntosverdes.dto.IncidenciaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.IncidenciaService;
import pe.com.puntosverdes.service.UsuarioService;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin("*")
public class IncidenciaController {

	@Autowired
	private IncidenciaService incidenciaService;

	@Autowired
	private UsuarioService usuarioService;

	// Carpeta donde se almacenan los archivos de incidencias
	@Value("${upload.incidencias.dir}")
	private String uploadDir;

	// REGISTRAR NUEVA INCIDENCIA
	@PostMapping("/registrar")
	public ResponseEntity<IncidenciaDTO> registrar(@RequestBody Incidencia incidencia, Authentication auth) {
		Usuario usuario = usuarioService.obtenerUsuarioPorUsername(auth.getName());

		boolean esRecolector = usuario.getUsuarioRoles().stream()
				.anyMatch(r -> r.getRol().getRolNombre().equalsIgnoreCase("RECOLECTOR"));

		if (!esRecolector) {
			return ResponseEntity.status(403).build(); // FORBIDDEN
		}

		incidencia.setReportadoPor(usuario);
		return ResponseEntity.ok(incidenciaService.registrarIncidencia(incidencia));
	}

	// LISTAR TODAS LAS INCIDENCIAS
	@GetMapping("/listar")
	public ResponseEntity<List<IncidenciaDTO>> listar() {
		return ResponseEntity.ok(incidenciaService.listarIncidencias());
	}

	// LISTAR INCIDENCIAS POR ESTADO (APROBADO / PENDIENTE / RECHAZADO)
	@GetMapping("/listar/estado/{estado}")
	public ResponseEntity<List<IncidenciaDTO>> listarPorEstado(@PathVariable String estado) {
		return ResponseEntity.ok(incidenciaService.listarPorEstado(estado.toUpperCase()));
	}

	// LISTAR INCIDENCIAS POR USUARIO
	@GetMapping("/listar/usuario/{usuarioId}")
	public ResponseEntity<List<IncidenciaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(incidenciaService.listarPorUsuario(usuarioId));
	}

	// OBTENER DETALLE DE INCIDENCIA POR ID
	@GetMapping("/detalle/{id}")
	public ResponseEntity<IncidenciaDTO> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(incidenciaService.obtenerPorId(id));
	}

	// OBTENER ÚLTIMA INCIDENCIA REPORTADA POR UN USUARIO
	@GetMapping("/ultima/{usuarioId}")
	public ResponseEntity<IncidenciaDTO> obtenerUltimaPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(incidenciaService.obtenerUltimaPorUsuario(usuarioId));
	}

	// VALIDAR O RESPONDER INCIDENCIA (ADMIN / MUNICIPALIDAD)
	@PutMapping("/{id}/validar")
	public ResponseEntity<IncidenciaDTO> validar(@PathVariable Long id, @RequestBody IncidenciaValidacionDTO dto,
			Authentication auth) {
		Usuario admin = usuarioService.obtenerUsuarioPorUsername(auth.getName());
		return ResponseEntity.ok(incidenciaService.validarIncidencia(id, dto, admin.getId()));
	}

	// SUBIR ARCHIVOS RELACIONADOS A UNA INCIDENCIA
	@PostMapping("/{id}/archivos")
	public ResponseEntity<Map<String, Object>> subirArchivos(@PathVariable Long id,
			@RequestParam("files") List<MultipartFile> files) {
		try {
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			List<String> nombresArchivos = new ArrayList<>();
			for (MultipartFile file : files) {
				String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				String filePath = uploadDir + fileName;

				file.transferTo(new File(filePath));
				nombresArchivos.add(fileName);
			}

			IncidenciaDTO actualizada = incidenciaService.subirArchivos(id, nombresArchivos);
			return ResponseEntity.ok(Map.of("incidenciaId", actualizada.getId(), "archivos", nombresArchivos));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	// VISUALIZAR ARCHIVO DE INCIDENCIA
	@GetMapping("/archivos/{fileName:.+}")
	public ResponseEntity<Resource> verArchivo(@PathVariable String fileName) {
		try {
			Path path = Paths.get(uploadDir).resolve(fileName);
			Resource resource = new UrlResource(path.toUri());

			if (resource.exists() && resource.isReadable()) {
				String contentType = Files.probeContentType(path);
				return ResponseEntity.ok()
						.contentType(MediaType
								.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
