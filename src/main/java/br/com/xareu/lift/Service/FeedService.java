package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.DTO.Feed.ItemFeedDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.PostagemMapper;
import br.com.xareu.lift.Mapper.UsuarioMapper;
import br.com.xareu.lift.Repository.EventoRepository;
import br.com.xareu.lift.Repository.PostagemRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostagemMapper postagemMapper;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Transactional(readOnly = true)
    public List<ItemFeedDTO> getFeedUnificado(Usuario usuarioLogado, int tamanhoPagina, String ultimoCursorStr) {
        LocalDateTime ultimoCursor = ultimoCursorStr != null ? LocalDateTime.parse(ultimoCursorStr) : null;

        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Usuario> seguindo = usuario.getSeguindo();
        List<ItemFeedDTO> feedUnificado = new ArrayList<>();

        // Busca postagens (quantidade maior para depois intercalar eventos)
        int postagensNecessarias = (int) Math.ceil(tamanhoPagina * 5.0 / 6.0); // 5 posts para cada evento
        List<Postagem> postagens = postagemRepository.findFeedSeguindo(
                seguindo, ultimoCursor, PageRequest.of(0, postagensNecessarias)
        );

        // Se faltar, preenche com posts explorar
        if (postagens.size() < postagensNecessarias) {
            int faltando = postagensNecessarias - postagens.size();
            List<Postagem> postsExplorar = postagemRepository.findFeedExplorar(
                    ultimoCursor, PageRequest.of(0, faltando)
            );
            postsExplorar.removeIf(p -> p.getAutor().getId().equals(usuario.getId()));
            postagens.addAll(postsExplorar);
        }

        // Busca eventos futuros (apenas eventos que ainda não aconteceram)
        int eventosNecessarios = tamanhoPagina / 6; // 1 evento a cada 5 posts
        List<Evento> eventos = eventoRepository.findAll().stream()
                .filter(e -> e.getDataInicio().isAfter(LocalDateTime.now()))
                .limit(eventosNecessarios)
                .toList();

        // Intercala posts e eventos (1 evento a cada 5 posts)
        int postIndex = 0;
        int eventoIndex = 0;
        int contador = 0;

        while (feedUnificado.size() < tamanhoPagina && (postIndex < postagens.size() || eventoIndex < eventos.size())) {
            // Adiciona 5 posts
            for (int i = 0; i < 5 && postIndex < postagens.size() && feedUnificado.size() < tamanhoPagina; i++) {
                PostagemResponseFeedDTO postagemDTO = postagemMapper.toResponseFeedDTO(postagens.get(postIndex++));
                feedUnificado.add(ItemFeedDTO.fromPostagem(postagemDTO));
            }

            // Adiciona 1 evento
            if (eventoIndex < eventos.size() && feedUnificado.size() < tamanhoPagina) {
                EventoResponseFeedDTO eventoDTO = toEventoResponseFeedDTO(eventos.get(eventoIndex++), usuario);
                feedUnificado.add(ItemFeedDTO.fromEvento(eventoDTO));
            }
        }

        return feedUnificado;
    }

    private EventoResponseFeedDTO toEventoResponseFeedDTO(Evento evento, Usuario usuarioLogado) {
        // Pega os primeiros 3 participantes
        List<Usuario> primeirosParticipantes = evento.getParticipantes() != null
                ? evento.getParticipantes().stream().limit(3).toList()
                : List.of();

        // Verifica se o usuário logado está confirmado
        boolean confirmado = evento.getParticipantes() != null
                && evento.getParticipantes().stream()
                        .anyMatch(p -> p.getId().equals(usuarioLogado.getId()));

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
                confirmado
        );
    }
}
