package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);


    Usuario findByEmailContainingIgnoreCase(String email);

}
