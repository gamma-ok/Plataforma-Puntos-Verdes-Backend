package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.puntosverdes.dto.AjustePuntosRequest;
import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.dto.UsuarioPerfilDTO;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.UsuarioService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Value("${upload.perfiles.dir}")
    private String uploadPerfilesDir;

    // Registrar usuario (público)
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody Usuario usuario) {
        usuario.setPerfil("default.png");
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(usuarioService.convertirADTO(creado));
    }

    // Listar todos los usuarios
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios()
                .stream()
                .map(usuarioService::convertirADTO)
                .collect(Collectors.toList()));
    }

    // Listar por estado (activo/inactivo)
    @GetMapping("/listar/{estado}")
    public ResponseEntity<List<UsuarioDTO>> listarPorEstado(@PathVariable boolean estado) {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuariosPorEstado(estado)
                .stream()
                .map(usuarioService::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    // Listar por rol
    @GetMapping("/listar/rol/{rol}")
    public ResponseEntity<List<UsuarioDTO>> listarPorRol(@PathVariable String rol) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorRol(rol)
                .stream()
                .map(usuarioService::convertirADTO)
                .collect(Collectors.toList()));
    }

    // Buscar usuario por ID
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuarioService.convertirADTO(usuario));
    }

    // Buscar usuario por username
    @GetMapping("/buscar/username/{username}")
    public ResponseEntity<UsuarioDTO> buscarPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        return ResponseEntity.ok(usuarioService.convertirADTO(usuario));
    }

    // Buscar usuario por email
    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuarioService.convertirADTO(usuario));
    }

    // Buscar usuarios por número de celular
    @GetMapping("/buscar/celular/{celular}")
    public ResponseEntity<List<UsuarioDTO>> buscarPorCelular(@PathVariable String celular) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosPorCelular(celular)
                .stream()
                .map(usuarioService::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    // Obtener perfil propio (JWT)
    @GetMapping("/perfil/mi")
    public ResponseEntity<UsuarioPerfilDTO> miPerfil(Authentication auth) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(auth.getName());
        Set<String> roles = usuario.getUsuarioRoles().stream()
                .map(ur -> ur.getRol().getRolNombre())
                .collect(Collectors.toSet());

        UsuarioPerfilDTO dto = new UsuarioPerfilDTO(
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getPerfil(),
                roles,
                usuario.getPuntosAcumulados(),
                usuario.getFechaRegistro()
        );

        return ResponseEntity.ok(dto);
    }

    // Actualizar datos personales (usuario autenticado)
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<UsuarioDTO> actualizarDatos(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado
    ) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }

    // Subir o actualizar foto de perfil (usuario o admin)
    @PostMapping("/{id}/perfil")
    public ResponseEntity<UsuarioDTO> subirFotoPerfil(
            @PathVariable Long id,
            @RequestParam("imagen") MultipartFile imagen
    ) throws IOException {

        if (imagen.isEmpty()) {
            throw new IllegalArgumentException("No se recibió ninguna imagen.");
        }

        File directorio = new File(uploadPerfilesDir);
        if (!directorio.exists()) directorio.mkdirs();

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        // Eliminar foto anterior si no es la predeterminada
        if (usuario.getPerfil() != null && !usuario.getPerfil().equals("default.png")) {
            File archivoAntiguo = new File(uploadPerfilesDir + usuario.getPerfil());
            if (archivoAntiguo.exists()) archivoAntiguo.delete();
        }

        String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
        File archivoDestino = new File(uploadPerfilesDir + nombreArchivo);
        imagen.transferTo(archivoDestino);

        Usuario actualizado = usuarioService.actualizarPerfil(id, nombreArchivo);
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }

    // Cambiar contraseña (solo ADMIN)
    @PutMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<UsuarioDTO> cambiarContrasena(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        Usuario actualizado = usuarioService.cambiarContrasena(id, request.get("nuevaContrasena"));
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }

    // Cambiar estado activo/inactivo (solo ADMIN)
    @PutMapping("/{id}/estado/{activo}")
    public ResponseEntity<UsuarioDTO> cambiarEstado(
            @PathVariable Long id,
            @PathVariable boolean activo
    ) {
        Usuario actualizado = usuarioService.actualizarEstado(id, activo);
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }

    // Asignar rol (solo ADMIN)
    @PutMapping("/{id}/asignar-rol")
    public ResponseEntity<UsuarioDTO> asignarRol(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        Usuario actualizado = usuarioService.asignarRol(id, request.get("nuevoRol"));
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }

    // Ajustar puntos (solo ADMIN/MUNICIPALIDAD)
    @PutMapping("/{id}/ajustar-puntos")
    public ResponseEntity<UsuarioDTO> ajustarPuntos(
            @PathVariable Long id,
            @RequestBody AjustePuntosRequest request,
            Authentication auth
    ) {
        Usuario actualizado = usuarioService.ajustarPuntos(
                id,
                request.getAccion(),
                request.getCantidad(),
                request.getMotivo(),
                auth.getName()
        );
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }

    // Eliminar usuario (solo ADMIN)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Estadísticas
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        return ResponseEntity.ok(usuarioService.obtenerEstadisticasUsuarios());
    }

    // Ranking
    @GetMapping("/ranking")
    public ResponseEntity<List<Usuario>> ranking() {
        return ResponseEntity.ok(usuarioService.obtenerRankingUsuarios());
    }
    
    // Actualizar datos de cualquier usuario (solo ADMIN)
    @PutMapping("/{id}/actualizar-admin")
    public ResponseEntity<UsuarioDTO> actualizarUsuarioPorAdmin(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado
    ) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return ResponseEntity.ok(usuarioService.convertirADTO(actualizado));
    }
}
