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
import pe.com.puntosverdes.model.Campania;
import pe.com.puntosverdes.model.Entrega;
import pe.com.puntosverdes.model.PuntoVerde;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.*;
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

	@Autowired
	private CampaniaService campaniaService;

	@Autowired
	private PuntoVerdeService puntoVerdeService;

	@Value("${upload.dir:uploads/entregas/}")
	private String uploadDir;

	// Registrar entrega (puede estar ligada a campaña o punto verde)
	@PostMapping("/registrar")
	public ResponseEntity<EntregaDTO> registrarEntrega(@RequestBody Entrega entrega, Authentication authentication) {
		Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
		entrega.setCiudadano(usuario);

		// Asignar campaña o punto verde si se envian los IDs
		if (entrega.getCampania() != null && entrega.getCampania().getId() != null) {
			Campania campania = campaniaService.obtenerPorId(entrega.getCampania().getId());
			entrega.setCampania(campania);
		}
		if (entrega.getPuntoVerde() != null && entrega.getPuntoVerde().getId() != null) {
			PuntoVerde puntoVerde = puntoVerdeService.obtenerPorId(entrega.getPuntoVerde().getId());
			entrega.setPuntoVerde(puntoVerde);
		}

		EntregaDTO guardada = entregaService.registrarEntrega(entrega);
		return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
	}

	// Listar
	@GetMapping("/listar")
	public ResponseEntity<List<EntregaDTO>> listarTodas() {
		return ResponseEntity.ok(entregaService.listarEntregas());
	}

	// Listar entregas por usuario
	@GetMapping("/listar/usuario/{usuarioId}")
	public ResponseEntity<List<EntregaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(entregaService.listarPorUsuario(usuarioId));
	}

	// Listar por estado
	@GetMapping("/listar/estado/{estado}")
	public ResponseEntity<List<EntregaDTO>> listarPorEstado(@PathVariable String estado) {
		return ResponseEntity.ok(entregaService.listarPorEstado(estado.toUpperCase()));
	}
	
	// LISTAR todas las ENTREGAS relacionadas con PUNTOS VERDES
    @GetMapping("/listar/puntos-verdes")
    public ResponseEntity<List<EntregaDTO>> listarRelacionadasPuntosVerdes() {
        return ResponseEntity.ok(entregaService.listarRelacionadasConPuntosVerdes());
    }

    // LISTAR por PUNTO VERDE y ESTADO
    @GetMapping("/listar/puntos-verdes/estado/{estado}")
    public ResponseEntity<List<EntregaDTO>> listarPuntosVerdesPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(entregaService.listarRelacionadasConPuntosVerdesPorEstado(estado.toUpperCase()));
    }
    
    // LISTAR todas las ENTREGAS relacionadas con CAMPANIAS
    @GetMapping("/listar/campanias")
    public ResponseEntity<List<EntregaDTO>> listarRelacionadasCampanias() {
        return ResponseEntity.ok(entregaService.listarRelacionadasConCampanias());
    }
    
    // LISTAR por CAMPAÑAS y ESTADO
    @GetMapping("/listar/campanias/estado/{estado}")
    public ResponseEntity<List<EntregaDTO>> listarCampaniasPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(entregaService.listarRelacionadasConCampaniasPorEstado(estado.toUpperCase()));
    }

    // LISTAR por ROL (CIUDADANO / RECOLECTOR)
    @GetMapping("/listar/rol/{rol}")
    public ResponseEntity<List<EntregaDTO>> listarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(entregaService.listarPorRol(rol));
    }

	// Detalle por ID
	@GetMapping("/detalle/{id}")
	public ResponseEntity<EntregaDTO> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(entregaService.obtenerPorId(id));
	}

	// Validar o rechazar entrega (ADMIN/MUNI)
	@PutMapping("/validar/{id}")
	public ResponseEntity<EntregaDTO> validarEntrega(@PathVariable Long id, @RequestBody EntregaValidacionDTO dto,
			Authentication authentication) {
		Usuario admin = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
		EntregaDTO validada = entregaService.validarEntrega(id, dto, admin.getId());
		return ResponseEntity.ok(validada);
	}

	// Subir archivos de evidencia
	@PostMapping("/{id}/archivos")
	public ResponseEntity<Map<String, Object>> subirArchivos(@PathVariable Long id,
			@RequestParam("files") List<MultipartFile> files) {
		try {
			File dir = new File(uploadDir);
			if (!dir.exists())
				dir.mkdirs();

			List<String> nombresArchivos = new ArrayList<>();
			for (MultipartFile file : files) {
				String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				String fullPath = uploadDir + File.separator + fileName;
				file.transferTo(new File(fullPath));
				nombresArchivos.add(fileName);
			}

			EntregaDTO entregaActualizada = entregaService.subirArchivos(id, nombresArchivos);
			Map<String, Object> response = new HashMap<>();
			response.put("entregaId", entregaActualizada.getId());
			response.put("archivos", nombresArchivos);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	// Visualizar archivo
	@GetMapping("/archivos/{fileName:.+}")
	public ResponseEntity<Resource> verArchivo(@PathVariable String fileName) {
		try {
			Path path = Paths.get(uploadDir + File.separator + fileName).normalize();
			Resource resource = new UrlResource(path.toUri());

			if (resource.exists()) {
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
						.body(resource);
			}
			return ResponseEntity.notFound().build();

		} catch (MalformedURLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Ultima entrega del usuario
	@GetMapping("/usuario/{usuarioId}/ultima")
	public ResponseEntity<EntregaDTO> ultimaEntrega(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(entregaService.obtenerUltimaEntregaPorUsuario(usuarioId));
	}
	
	// Eliminar entrega por ID
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Map<String, Object>> eliminarEntrega(@PathVariable Long id) {
	    try {
	        entregaService.eliminarEntrega(id);
	        return ResponseEntity.ok(Map.of("mensaje", "Entrega eliminada correctamente", "idEliminado", id));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
	    }
	}
	
	// Obtener las últimas 3 entregas del usuario autenticado
	@GetMapping("/mis-ultimas")
	public ResponseEntity<List<EntregaDTO>> obtenerMisUltimasEntregas(Authentication authentication) {
	    Usuario usuario = usuarioService.obtenerUsuarioPorUsername(authentication.getName());
	    List<EntregaDTO> entregas = entregaService.obtenerUltimasPorUsuario(usuario.getId(), 3);
	    return ResponseEntity.ok(entregas);
	}
}
