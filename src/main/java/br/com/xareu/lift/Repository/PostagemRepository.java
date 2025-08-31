package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {


}
