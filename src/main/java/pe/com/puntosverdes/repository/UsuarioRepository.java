package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Usuario;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    // Buscar usuarios por rol
    List<Usuario> findByUsuarioRoles_Rol_RolNombre(String rolNombre);

    // Eliminar usuario por username
    void deleteByUsername(String username);
}
