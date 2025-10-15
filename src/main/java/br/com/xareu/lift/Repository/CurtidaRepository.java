// src/main/java/br/com/xareu/lift/Repository/CurtidaRepository.java
package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Long> {


    Optional<Curtida> findByPostagemAndAutor(Postagem postagem, Usuario autor);


    List<Curtida> findByPostagemId(Long postagemId);
}