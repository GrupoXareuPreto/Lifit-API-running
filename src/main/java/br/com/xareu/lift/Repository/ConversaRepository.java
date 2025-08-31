package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {
}
