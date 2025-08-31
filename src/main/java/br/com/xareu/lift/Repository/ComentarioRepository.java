package br.com.xareu.lift.Repository;


import br.com.xareu.lift.Entity.Comentario;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
