package br.com.xareu.lift.Repository;

import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;


@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

    List<Postagem> findByAutor(Usuario autor);

    @Query("""
        SELECT p FROM Postagem p
        WHERE p.autor IN :seguindo
          AND (:ultimoCursor IS NULL OR p.dataPublicacao < :ultimoCursor)
        ORDER BY p.dataPublicacao DESC
    """)
    List<Postagem> findFeedSeguindo(@Param("seguindo") List<Usuario> seguindo,
                                    @Param("ultimoCursor") LocalDateTime ultimoCursor,
                                    Pageable pageable);

    @Query("""
        SELECT p FROM Postagem p
        WHERE (:ultimoCursor IS NULL OR p.dataPublicacao < :ultimoCursor)
        ORDER BY p.dataPublicacao DESC
    """)
    List<Postagem> findFeedExplorar(@Param("ultimoCursor") LocalDateTime ultimoCursor,
                                    Pageable pageable);
}
