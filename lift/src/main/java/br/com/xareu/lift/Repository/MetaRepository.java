package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    Optional<Meta> findByNome(String nome);
}
