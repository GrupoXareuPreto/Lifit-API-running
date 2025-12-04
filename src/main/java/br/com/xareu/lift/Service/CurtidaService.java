package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Curtida.CurtidaRequestDTO;
import br.com.xareu.lift.DTO.Curtida.CurtidaResponseDTO;
import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.CurtidaRepository;
import br.com.xareu.lift.Repository.EventoRepository;
import br.com.xareu.lift.Repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurtidaService {

    private final CurtidaRepository curtidaRepository;
    private final PostagemRepository postagemRepository;
    private final EventoRepository eventoRepository;

    @Autowired
    public CurtidaService(CurtidaRepository curtidaRepository, PostagemRepository postagemRepository, EventoRepository eventoRepository) {
        this.curtidaRepository = curtidaRepository;
        this.postagemRepository = postagemRepository;
        this.eventoRepository = eventoRepository;
    }
/*--------------------------------------------------------------------------------------------------------------------*/
/*parte de DTOs*/
    private CurtidaResponseDTO toResponseDTO(Curtida curtida) {
        return new CurtidaResponseDTO(
                curtida.getId(),
                curtida.getAutor().getId(),
                curtida.getAutor().getNomeUsuario(),
                curtida.getPostagem().getId()
        );
    }



    @Transactional
    public CurtidaResponseDTO criarCurtida(CurtidaRequestDTO dto, Usuario autorLogado) {
        Curtida novaCurtida = new Curtida();
        novaCurtida.setAutor(autorLogado);

        // Curtir postagem
        if (dto.postagemId() != null) {
            Postagem postagem = postagemRepository.findById(dto.postagemId())
                    .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

            if (curtidaRepository.findByPostagemAndAutor(postagem, autorLogado).isPresent()) {
                throw new IllegalStateException("Usuário já curtiu esta postagem.");
            }

            novaCurtida.setPostagem(postagem);
        }
        // Curtir evento
        else if (dto.eventoId() != null) {
            Evento evento = eventoRepository.findById(dto.eventoId())
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

            if (curtidaRepository.findByEventoAndAutor(evento, autorLogado).isPresent()) {
                throw new IllegalStateException("Usuário já curtiu este evento.");
            }

            novaCurtida.setEvento(evento);
        } else {
            throw new IllegalArgumentException("É necessário informar postagemId ou eventoId");
        }

        Curtida curtidaSalva = curtidaRepository.save(novaCurtida);
        return toResponseDTO(curtidaSalva);
    }

    /*Curtida não pode ser editada*/

    @Transactional
    public void excluirCurtida(Long postagemId, Usuario autorLogado) {
        Postagem postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Curtida curtida = curtidaRepository.findByPostagemAndAutor(postagem, autorLogado)
                .orElseThrow(() -> new RuntimeException("Curtida não encontrada para este usuário e postagem."));

        curtidaRepository.delete(curtida);
    }

    @Transactional
    public void excluirCurtidaEvento(Long eventoId, Usuario autorLogado) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Curtida curtida = curtidaRepository.findByEventoAndAutor(evento, autorLogado)
                .orElseThrow(() -> new RuntimeException("Curtida não encontrada para este usuário e evento."));

        curtidaRepository.delete(curtida);
    }

    public List<CurtidaResponseDTO> getCurtidasPorPostagem(Long postagemId) {
        if (!postagemRepository.existsById(postagemId)) {
            throw new RuntimeException("Postagem não encontrada");
        }
        return curtidaRepository.findByPostagemId(postagemId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
