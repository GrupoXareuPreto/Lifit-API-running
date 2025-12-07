package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Evento.EventoRequestCriarDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponsePerfilDTO;
import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.EventoMapper;
import br.com.xareu.lift.Mapper.UsuarioMapper;
import br.com.xareu.lift.Repository.EventoRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoMapper eventoMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    private EventoResponseFeedDTO toResponseFeedDTO(Evento evento, Usuario usuarioLogado) {
        if (evento == null) {
            return null;
        }
        
        // Pega os primeiros 3 participantes
        List<Usuario> primeirosParticipantes = evento.getParticipantes() != null
                ? evento.getParticipantes().stream().limit(3).toList()
                : List.of();
        
        // Verifica se o usuário logado está confirmado
        boolean confirmado = evento.getParticipantes() != null
                && evento.getParticipantes().stream()
                        .anyMatch(p -> p.getId().equals(usuarioLogado.getId()));
        
        // Verifica se o usuário curtiu este evento
        boolean usuarioCurtiu = evento.getCurtidas() != null
                && evento.getCurtidas().stream()
                        .anyMatch(c -> c.getAutor().getId().equals(usuarioLogado.getId()));
        
        return new EventoResponseFeedDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getLocalizacao(),
                evento.getDataInicio(),
                evento.getDataFim(),
                usuarioMapper.toUsuarioCardPostagemEventoDTO(evento.getAutor()),
                evento.getMidia(),
                evento.getCurtidas() != null ? evento.getCurtidas().size() : 0,
                evento.getComentarios() != null ? evento.getComentarios().size() : 0,
                evento.getCompartilhamentos(),
                evento.getParticipantes() != null ? evento.getParticipantes().size() : 0,
                usuarioMapper.toUsuarioResponseSimplesList(primeirosParticipantes),
                confirmado,
                usuarioCurtiu
        );
    }

    @Transactional
    public EventoResponseFeedDTO criarEvento(EventoRequestCriarDTO dto, Usuario autor) {
        Evento evento = new Evento();
        evento.setTitulo(dto.titulo());
        evento.setMidia(dto.midia());
        evento.setDescricao(dto.descricao());
        evento.setLocalizacao(dto.localizacao());
        evento.setDataInicio(dto.dataInicio());
        evento.setDataFim(dto.dataFim());
        evento.setAutor(autor);

        Evento eventoSaved = repository.save(evento);
        return toResponseFeedDTO(eventoSaved, autor);
    }


    public List<EventoResponseFeedDTO> getAll(Usuario usuarioLogado) {
        return repository.findAll().stream()
                .map(evento -> toResponseFeedDTO(evento, usuarioLogado))
                .toList();
    }



    @Transactional
    public void deletarEvento(Long eventoId, Usuario usuarioLogado) throws IllegalAccessException {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        // *** A VERIFICAÇÃO DE AUTORIZAÇÃO CRUCIAL ***
        if (!evento.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para deletar este evento.");
        }

        repository.delete(evento);
    }

    @Transactional
    public Optional<EventoResponseFeedDTO> atualizarEvento(Long eventoId, EventoRequestCriarDTO dto, Usuario usuarioLogado) throws IllegalAccessException {
        // Busca o evento que será atualizado
        Optional<Evento> eventoOpt = repository.findById(eventoId);

        if (eventoOpt.isEmpty()) {
            return Optional.empty(); // Retorna vazio se o evento não existe
        }

        Evento eventoExistente = eventoOpt.get();

        // *** A VERIFICAÇÃO DE AUTORIZAÇÃO CRUCIAL ***
        if (!eventoExistente.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para editar este evento.");
        }

        // Atualiza os campos do evento existente com os dados da DTO
        eventoExistente.setTitulo(dto.titulo());
        eventoExistente.setMidia(dto.midia());
        eventoExistente.setDescricao(dto.descricao());
        eventoExistente.setLocalizacao(dto.localizacao());
        eventoExistente.setDataInicio(dto.dataInicio());
        eventoExistente.setDataFim(dto.dataFim());

        Evento eventoAtualizado = repository.save(eventoExistente);
        return Optional.of(toResponseFeedDTO(eventoAtualizado, usuarioLogado));
    }

    public List<EventoResponsePerfilDTO> getEventosPorAutor(Usuario autor) {
          return eventoMapper.toEventoResponsePerfilDTOList(repository.findByAutor(autor));
    }

    public Optional<EventoResponseFeedDTO> getEventoPorId(Long id, Usuario usuarioLogado) {
        return repository.findById(id)
                .map(evento -> toResponseFeedDTO(evento, usuarioLogado));
    }

    @Transactional
    public EventoResponseFeedDTO confirmarPresenca(Long eventoId, Usuario usuarioLogado) {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        // Inicializa a lista de eventos do usuário se necessário
        if (usuarioLogado.getEventosParticipar() == null) {
            usuarioLogado.setEventosParticipar(new java.util.ArrayList<>());
        }
        
        // Verifica se o usuário já confirmou presença
        boolean jaConfirmado = usuarioLogado.getEventosParticipar().stream()
                .anyMatch(e -> e.getId().equals(eventoId));
        
        if (!jaConfirmado) {
            // Adiciona o evento na lista do usuário (lado proprietário do relacionamento)
            usuarioLogado.getEventosParticipar().add(evento);
            usuarioRepository.save(usuarioLogado);
        }
        
        // Recarrega o evento para ter a lista atualizada de participantes
        evento = repository.findById(eventoId).orElseThrow();
        
        return toResponseFeedDTO(evento, usuarioLogado);
    }

    @Transactional
    public EventoResponseFeedDTO removerPresenca(Long eventoId, Usuario usuarioLogado) {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        
        // Remove o evento da lista do usuário (lado proprietário do relacionamento)
        if (usuarioLogado.getEventosParticipar() != null) {
            usuarioLogado.getEventosParticipar().removeIf(e -> e.getId().equals(eventoId));
            usuarioRepository.save(usuarioLogado);
        }
        
        // Recarrega o evento para ter a lista atualizada de participantes
        evento = repository.findById(eventoId).orElseThrow();
        
        return toResponseFeedDTO(evento, usuarioLogado);
    }
}
