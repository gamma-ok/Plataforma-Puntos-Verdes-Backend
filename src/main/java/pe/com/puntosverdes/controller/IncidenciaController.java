package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.puntosverdes.dto.IncidenciaRespuestaDTO;
import pe.com.puntosverdes.dto.IncidenciaValidacionDTO;
import pe.com.puntosverdes.model.Incidencia;
import pe.com.puntosverdes.model.Usuario;
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

	@Value("${upload.incidencias.dir}")
	private String uploadDir;

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

	@GetMapping("/")
	public ResponseEntity<List<Incidencia>> listar() {
		return ResponseEntity.ok(incidenciaService.listarIncidencias());
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Incidencia>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(incidenciaService.listarPorUsuario(usuarioId));
	}

	@PutMapping("/{id}/validar")
	public ResponseEntity<IncidenciaRespuestaDTO> validar(@PathVariable Long id,
			@RequestBody IncidenciaValidacionDTO dto, Authentication auth) {
		Usuario admin = usuarioService.obtenerUsuarioPorUsername(auth.getName());
		return ResponseEntity.ok(incidenciaService.validarIncidencia(id, dto, admin.getId()));
	}

	// 📌 Subir evidencias
	@PostMapping("/{id}/evidencias")
	public ResponseEntity<Incidencia> subirEvidencias(@PathVariable Long id,
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

				rutasGuardadas.add(fileName); // guardamos solo el nombre del archivo
			}

			Incidencia incidenciaActualizada = incidenciaService.subirEvidencias(id, rutasGuardadas);

			return ResponseEntity.ok(incidenciaActualizada);

		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	// 📌 Servir evidencias
	@GetMapping("/evidencias/{fileName}")
	public ResponseEntity<Resource> verEvidencia(@PathVariable String fileName) {
		try {
			Path path = Paths.get(uploadDir).resolve(fileName);
			Resource resource = new UrlResource(path.toUri());

			if (resource.exists() || resource.isReadable()) {
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // soporta jpg
						.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
