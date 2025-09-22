package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

}
