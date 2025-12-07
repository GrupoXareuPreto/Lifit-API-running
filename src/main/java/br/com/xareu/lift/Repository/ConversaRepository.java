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
    
    @Query("SELECT c FROM Conversa c WHERE c.integrantes.size = 2 AND :usuario1 MEMBER OF c.integrantes AND :usuario2 MEMBER OF c.integrantes")
    Optional<Conversa> findConversaEntreDoasUsuarios(@Param("usuario1") Usuario usuario1, @Param("usuario2") Usuario usuario2);
}