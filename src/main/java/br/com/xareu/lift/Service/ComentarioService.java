package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Comentario.ComentarioRequestDTO;
import br.com.xareu.lift.DTO.Comentario.ComentarioResponseDTO;
import br.com.xareu.lift.DTO.Curtida.CurtidaResponseDTO;
import br.com.xareu.lift.Entity.Comentario;
import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.ComentarioRepository;
import br.com.xareu.lift.Repository.EventoRepository;
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
    private final EventoRepository eventoRepository;

    @Autowired
    public ComentarioService(ComentarioRepository comentarioRepository, PostagemRepository postagemRepository, EventoRepository eventoRepository) {
        this.comentarioRepository = comentarioRepository;
        this.postagemRepository = postagemRepository;
        this.eventoRepository = eventoRepository;
    }


/*--------------------------------------------------------------------------------------------------------------------*/
/*parte de DTOs*/
    private ComentarioResponseDTO toResponseDTO(Comentario comentario) {
        Long postagemId = comentario.getPostagem() != null ? comentario.getPostagem().getId() : null;
        return new ComentarioResponseDTO(
                comentario.getId(),
                comentario.getConteudo(),
                comentario.getDataCriacao(),
                comentario.getAutor().getId(),
                comentario.getAutor().getNomeUsuario(),
                postagemId
        );
}



/*--------------------------------------------------------------------------------------------------------------------*/




    @Transactional
    public ComentarioResponseDTO criarComentario(ComentarioRequestDTO dto, Usuario autorLogado) {
        Comentario novoComentario = new Comentario();
        novoComentario.setConteudo(dto.conteudo());
        novoComentario.setAutor(autorLogado);
        novoComentario.setDataCriacao(LocalDateTime.now());

        // Verifica se é comentário em postagem ou evento
        if (dto.postagemId() != null) {
            Postagem postagem = postagemRepository.findById(dto.postagemId())
                    .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
            novoComentario.setPostagem(postagem);
        } else if (dto.eventoId() != null) {
            Evento evento = eventoRepository.findById(dto.eventoId())
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
            novoComentario.setEvento(evento);
        } else {
            throw new RuntimeException("Comentário deve pertencer a uma postagem ou evento");
        }

        Comentario comentarioSalvo = comentarioRepository.save(novoComentario);
        return toResponseDTO(comentarioSalvo);
    }

    @Transactional
    public void deletarComentario(Long comentarioId, Usuario usuarioLogado) throws IllegalAccessException {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

        // *** A VERIFICAÇÃO DE AUTORIZAÇÃO CRUCIAL ***
        // Apenas o autor do comentário OU o autor da postagem/evento podem deletar.
        boolean isAutorDoComentario = comentario.getAutor().getId().equals(usuarioLogado.getId());
        boolean isAutorDoConteudo = false;
        
        if (comentario.getPostagem() != null) {
            isAutorDoConteudo = comentario.getPostagem().getAutor().getId().equals(usuarioLogado.getId());
        } else if (comentario.getEvento() != null) {
            isAutorDoConteudo = comentario.getEvento().getAutor().getId().equals(usuarioLogado.getId());
        }

        if (!isAutorDoComentario && !isAutorDoConteudo) {
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

    public List<ComentarioResponseDTO> getComentariosPorEvento(Long eventoId) {
        if (!eventoRepository.existsById(eventoId)) {
            throw new RuntimeException("Evento não encontrado");
        }
        return comentarioRepository.findByEventoIdOrderByDataCriacaoDesc(eventoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}
