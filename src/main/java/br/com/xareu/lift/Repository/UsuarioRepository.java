package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(u.nomeUsuario) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Usuario> buscarPorNomeOuUsername(@Param("query") String query);

}
