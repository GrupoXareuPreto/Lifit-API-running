// src/main/java/br/com/xareu/lift/Repository/ConversaRepository.java
package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    List<Conversa> findAllByIntegrantes_Id(Long usuarioId);
}