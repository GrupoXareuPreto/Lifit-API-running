package br.com.xareu.lift.Repository;


import br.com.xareu.lift.Entity.Comentario;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByPostagemIdOrderByDataCriacaoDesc(Long postagemId);
    
    List<Comentario> findByEventoIdOrderByDataCriacaoDesc(Long eventoId);
}
