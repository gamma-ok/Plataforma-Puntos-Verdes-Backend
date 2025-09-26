package pe.com.puntosverdes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.puntosverdes.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByUsername(String username);
}
