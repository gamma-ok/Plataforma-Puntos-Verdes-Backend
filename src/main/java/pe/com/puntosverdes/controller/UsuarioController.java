package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.dto.CambioPasswordDTO;
import pe.com.puntosverdes.dto.UsuarioDTO;
import pe.com.puntosverdes.dto.UsuarioPerfilDTO;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.UsuarioService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Registrar usuario
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody Usuario usuario) {
        usuario.setPerfil("default.png");
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(usuarioService.convertirADTO(creado));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(
                usuarioService.listarUsuarios().stream()
                        .map(usuarioService::convertirADTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<Usuario>> obtenerRanking() {
        return ResponseEntity.ok(usuarioService.obtenerRankingUsuarios());
    }
    
    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario != null ? ResponseEntity.ok(usuarioService.convertirADTO(usuario))
                : ResponseEntity.notFound().build();
    }

    // Obtener por username
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        return usuario != null ? ResponseEntity.ok(usuarioService.convertirADTO(usuario))
                : ResponseEntity.notFound().build();
    }
    
    // Buscar por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    // Buscar por celular
    @GetMapping("/celular/{celular}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorCelular(@PathVariable String celular) {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosPorCelular(celular));
    }

    // Actualizar usuario (ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return actualizado != null ? ResponseEntity.ok(usuarioService.convertirADTO(actualizado))
                : ResponseEntity.notFound().build();
    }

    // Cambiar contraseña
    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<UsuarioDTO> cambiarContrasena(@PathVariable Long id, @RequestBody CambioPasswordDTO request) {
        Usuario actualizado = usuarioService.cambiarContrasena(id, request.getNuevaContrasena());
        return actualizado != null ? ResponseEntity.ok(usuarioService.convertirADTO(actualizado))
                : ResponseEntity.notFound().build();
    }

    // Habilitar usuario
    @PutMapping("/{id}/habilitar")
    public ResponseEntity<UsuarioDTO> habilitarUsuario(@PathVariable Long id) {
        Usuario actualizado = usuarioService.habilitarUsuario(id);
        return actualizado != null ? ResponseEntity.ok(usuarioService.convertirADTO(actualizado))
                : ResponseEntity.notFound().build();
    }

    // Deshabilitar usuario
    @PutMapping("/{id}/deshabilitar")
    public ResponseEntity<UsuarioDTO> deshabilitarUsuario(@PathVariable Long id) {
        Usuario actualizado = usuarioService.deshabilitarUsuario(id);
        return actualizado != null ? ResponseEntity.ok(usuarioService.convertirADTO(actualizado))
                : ResponseEntity.notFound().build();
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Listar usuarios por rol
    @GetMapping("/rol/{rolNombre}")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosPorRol(@PathVariable String rolNombre) {
        return ResponseEntity.ok(
                usuarioService.listarUsuariosPorRol(rolNombre).stream()
                        .map(usuarioService::convertirADTO)
                        .collect(Collectors.toList())
        );
    }

    // Actualizar perfil (foto)
    @PutMapping("/{id}/perfil")
    public ResponseEntity<UsuarioDTO> actualizarPerfil(
            @PathVariable Long id,
            @RequestParam("perfilUrl") String perfilUrl) {
        Usuario usuario = usuarioService.actualizarPerfil(id, perfilUrl);
        return ResponseEntity.ok(usuarioService.convertirADTO(usuario));
    }

    // Obtener mi perfil
    @GetMapping("/mi-perfil")
    public ResponseEntity<UsuarioPerfilDTO> obtenerMiPerfil(Authentication authentication) {
        String username = authentication.getName(); // viene del JWT
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

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

    // Actualizar mi perfil
    @PutMapping("/mi-perfil")
    public ResponseEntity<UsuarioPerfilDTO> actualizarMiPerfil(
            Authentication authentication,
            @RequestBody UsuarioPerfilDTO perfilActualizado) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setNombre(perfilActualizado.getNombre());
        usuario.setApellido(perfilActualizado.getApellido());
        usuario.setEmail(perfilActualizado.getEmail());
        usuario.setCelular(perfilActualizado.getCelular());
        usuario.setPerfil(perfilActualizado.getPerfil());

        Usuario actualizado = usuarioService.actualizarUsuario(usuario.getId(), usuario);

        Set<String> roles = actualizado.getUsuarioRoles().stream()
                .map(ur -> ur.getRol().getRolNombre())
                .collect(Collectors.toSet());

        UsuarioPerfilDTO dto = new UsuarioPerfilDTO(
                actualizado.getUsername(),
                actualizado.getNombre(),
                actualizado.getApellido(),
                actualizado.getEmail(),
                actualizado.getCelular(),
                actualizado.getPerfil(),
                roles,
                actualizado.getPuntosAcumulados(),
                actualizado.getFechaRegistro()
        );

        return ResponseEntity.ok(dto);
    }
    
    // Asignar rol (solo ADMIN debería acceder a este endpoint)
    @PutMapping("/{id}/asignar-rol")
    public ResponseEntity<Usuario> asignarRol(@PathVariable Long id, @RequestParam("rol") String rol) {
        Usuario usuario = usuarioService.asignarRol(id, rol);
        return ResponseEntity.ok(usuario);
    }
}
