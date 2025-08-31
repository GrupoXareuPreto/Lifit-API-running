package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Compartilhamento;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CompartilhamentoRepository extends JpaRepository<Compartilhamento, Long> {
}
