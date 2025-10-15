package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Encontra todos os eventos criados por um usuário específico.
    List<Evento> findByAutor(Usuario autor);

    // Encontra todos os eventos de um autor pelo ID (opcional, mas útil).
    List<Evento> findByAutorId(Long autorId);

}
