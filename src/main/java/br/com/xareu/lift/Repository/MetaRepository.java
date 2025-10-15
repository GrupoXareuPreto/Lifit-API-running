package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Meta;
import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    Optional<Meta> findByNome(String nome);
    List<Meta> findByAutor(Usuario autor);
    List<Meta> findByPublicaTrue();
}
