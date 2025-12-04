package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Seguidor;
import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {

    Optional<Seguidor> findBySeguidorAndSeguido(Usuario seguidor, Usuario seguido);

    List<Seguidor> findBySeguido(Usuario seguido); // Seguidores de um usuário

    List<Seguidor> findBySeguidor(Usuario seguidor); // Quem o usuário está seguindo

    long countBySeguido(Usuario seguido); // Contar seguidores

    long countBySeguidor(Usuario seguidor); // Contar seguindo

    boolean existsBySeguidorAndSeguido(Usuario seguidor, Usuario seguido);
}
