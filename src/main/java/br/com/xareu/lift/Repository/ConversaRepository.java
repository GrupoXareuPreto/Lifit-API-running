// src/main/java/br/com/xareu/lift/Repository/ConversaRepository.java
package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    List<Conversa> findAllByIntegrantes_Id(Long usuarioId);
    
}