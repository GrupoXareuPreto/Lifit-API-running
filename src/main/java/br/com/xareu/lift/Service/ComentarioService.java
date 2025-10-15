package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Comentario.ComentarioRequestDTO;
import br.com.xareu.lift.DTO.Comentario.ComentarioResponseDTO;
import br.com.xareu.lift.DTO.Curtida.CurtidaResponseDTO;
import br.com.xareu.lift.Entity.Comentario;
import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.ComentarioRepository;
import br.com.xareu.lift.Repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PostagemRepository postagemRepository;

    @Autowired
    public ComentarioService(ComentarioRepository comentarioRepository, PostagemRepository postagemRepository) {
        this.comentarioRepository = comentarioRepository;
        this.postagemRepository = postagemRepository;
    }


/*--------------------------------------------------------------------------------------------------------------------*/
/*parte de DTOs*/
    private ComentarioResponseDTO toResponseDTO(Comentario comentario) {
        return new ComentarioResponseDTO(
                comentario.getId(),
                comentario.getConteudo(),
                comentario.getDataCriacao(),
                comentario.getAutor().getId(),
                comentario.getAutor().getNomeUsuario(),
                comentario.getPostagem().getId()
        );
}



/*--------------------------------------------------------------------------------------------------------------------*/




    @Transactional
    public ComentarioResponseDTO criarComentario(ComentarioRequestDTO dto, Usuario autorLogado) {
        Postagem postagem = postagemRepository.findById(dto.getPostagemId())
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Comentario novoComentario = new Comentario();
        novoComentario.setConteudo(dto.getConteudo());
        novoComentario.setPostagem(postagem);
        novoComentario.setAutor(autorLogado); // Autor vem do token!
        novoComentario.setDataCriacao(LocalDateTime.now());

        Comentario comentarioSalvo = comentarioRepository.save(novoComentario);
        return toResponseDTO(comentarioSalvo);
    }

    @Transactional
    public void deletarComentario(Long comentarioId, Usuario usuarioLogado) throws IllegalAccessException {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

        // *** A VERIFICAÇÃO DE AUTORIZAÇÃO CRUCIAL ***
        // Apenas o autor do comentário OU o autor da postagem podem deletar.
        boolean isAutorDoComentario = comentario.getAutor().getId().equals(usuarioLogado.getId());
        boolean isAutorDaPostagem = comentario.getPostagem().getAutor().getId().equals(usuarioLogado.getId());

        if (!isAutorDoComentario && !isAutorDaPostagem) {
            throw new IllegalAccessException("Você não tem permissão para deletar este comentário.");
        }

        comentarioRepository.delete(comentario);
    }

    public List<ComentarioResponseDTO> getComentariosPorPostagem(Long postagemId) {
        if (!postagemRepository.existsById(postagemId)) {
            throw new RuntimeException("Postagem não encontrada");
        }
        return comentarioRepository.findByPostagemIdOrderByDataCriacaoDesc(postagemId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }



}
