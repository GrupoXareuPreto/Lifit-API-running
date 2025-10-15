package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Evento.EventoRequestCriarDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponsePerfilDTO;
import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.EventoMapper;
import br.com.xareu.lift.Repository.EventoRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

/*--------------------------------------------------------------------------------------------------------------------*/
/*Parte de DTOs

    private EventoResponseFeedDTO toResponseFeedDTO(Evento evento){
        if(evento == null){
            return null;
        }
        else {
            return new EventoResponseFeedDTO(
                    usuarioService.toUsuarioCardPostagemEventoDTO(evento.getAutor()),
                    evento.getMidia(),
                    evento.getDataInicio(),
                    evento.getCurtidas() != null ? evento.getCurtidas().size() : 0,
                    evento.getComentarios() != null ? evento.getComentarios().size() : 0,
                    evento.getCompartilhamentos()
            );
        }

    }

    public EventoResponsePerfilDTO toEventoResponsePerfilDTO(Evento evento){
        if(evento == null){
            return null;
        }
        else {
            return new EventoResponsePerfilDTO(
                    evento.getMidia(),
                    evento.getTitulo()
            );
        }
    }

--------------------------------------------------------------------------------------------------------------------*/

    @Autowired
    private EventoRepository repository;

    @Autowired
    private EventoMapper mapper;

    @Transactional
    public EventoResponseFeedDTO criarEvento(EventoRequestCriarDTO dto, Usuario autor) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setMidia(dto.getMidia());
        evento.setDescricao(dto.getDescricao());
        evento.setLocalizacao(dto.getLocalizacao());
        evento.setDataInicio(dto.getDataInicio());
        evento.setDataFim(dto.getDataFim());
        evento.setAutor(autor); // Define o autor como o usuário que está logado!

        Evento eventoSaved = repository.save(evento);
        return mapper.toResponseFeedDTO(eventoSaved);
    }


    // MELHORIA 2: O método getAll agora retorna uma lista de DTOs.
    public List<EventoResponseFeedDTO> getAll() {
        return mapper.toResponseFeedDTOList(repository.findAll());

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
        eventoExistente.setTitulo(dto.getTitulo());
        eventoExistente.setMidia(dto.getMidia());
        eventoExistente.setDescricao(dto.getDescricao());
        eventoExistente.setLocalizacao(dto.getLocalizacao());
        eventoExistente.setDataInicio(dto.getDataInicio());
        eventoExistente.setDataFim(dto.getDataFim());

        Evento eventoAtualizado = repository.save(eventoExistente);
        return Optional.of(mapper.toResponseFeedDTO(eventoAtualizado));
    }
}
