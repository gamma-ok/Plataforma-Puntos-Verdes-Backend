package pe.com.puntosverdes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.puntosverdes.model.Usuario;
import pe.com.puntosverdes.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Registrar usuario
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        usuario.setPerfil("default.png");
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // Obtener por username
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> obtenerUsuarioPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(username);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    // Cambiar contraseña
    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<Usuario> cambiarContrasena(@PathVariable Long id, @RequestBody String nuevaContrasena) {
        Usuario actualizado = usuarioService.cambiarContrasena(id, nuevaContrasena);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    // Habilitar usuario
    @PutMapping("/{id}/habilitar")
    public ResponseEntity<Usuario> habilitarUsuario(@PathVariable Long id) {
        Usuario actualizado = usuarioService.habilitarUsuario(id);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    // Deshabilitar usuario
    @PutMapping("/{id}/deshabilitar")
    public ResponseEntity<Usuario> deshabilitarUsuario(@PathVariable Long id) {
        Usuario actualizado = usuarioService.deshabilitarUsuario(id);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar por username
    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> eliminarUsuarioPorUsername(@PathVariable String username) {
        usuarioService.eliminarUsuarioPorUsername(username);
        return ResponseEntity.noContent().build();
    }

    // Listar usuarios por rol
    @GetMapping("/rol/{rolNombre}")
    public ResponseEntity<List<Usuario>> listarUsuariosPorRol(@PathVariable String rolNombre) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorRol(rolNombre));
    }
    
    @PutMapping("/{id}/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(
            @PathVariable Long id,
            @RequestParam("perfilUrl") String perfilUrl) {
        Usuario usuario = usuarioService.actualizarPerfil(id, perfilUrl);
        return ResponseEntity.ok(usuario);
    }
}
