package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.puntosverdes.model.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByCelular(String celular);
    List<Usuario> findByUsuarioRoles_Rol_RolNombre(String rolNombre);
    List<Usuario> findAllByOrderByPuntosAcumuladosDesc();
}
