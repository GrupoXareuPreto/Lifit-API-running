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
        // Ignora eventos já exibidos usando o cursor e eventos criados pelo próprio usuário
        int eventosNecessarios = tamanhoPagina / 6; // 1 evento a cada 5 posts
        List<Evento> eventos = eventoRepository.findAll().stream()
                .filter(e -> e.getDataInicio().isAfter(LocalDateTime.now()))
                .filter(e -> !e.getAutor().getId().equals(usuario.getId())) // Não mostra eventos próprios
                .filter(e -> ultimoCursor == null || (e.getDataCriacao() != null && e.getDataCriacao().isBefore(ultimoCursor)))
                .sorted((e1, e2) -> {
                    LocalDateTime dc1 = e1.getDataCriacao() != null ? e1.getDataCriacao() : e1.getDataInicio();
                    LocalDateTime dc2 = e2.getDataCriacao() != null ? e2.getDataCriacao() : e2.getDataInicio();
                    return dc2.compareTo(dc1); // Mais recente primeiro
                })
                .limit(eventosNecessarios)
                .toList();

        // Intercala posts e eventos (1 evento a cada 5 posts)
        int postIndex = 0;
        int eventoIndex = 0;

        while (feedUnificado.size() < tamanhoPagina && postIndex < postagens.size()) {
            // Adiciona 5 posts
            for (int i = 0; i < 5 && postIndex < postagens.size() && feedUnificado.size() < tamanhoPagina; i++) {
                PostagemResponseFeedDTO postagemDTO = toPostagemResponseFeedDTO(postagens.get(postIndex++), usuario);
                feedUnificado.add(ItemFeedDTO.fromPostagem(postagemDTO));
            }

            // Adiciona 1 evento (se ainda houver eventos disponíveis)
            if (eventoIndex < eventos.size() && feedUnificado.size() < tamanhoPagina) {
                EventoResponseFeedDTO eventoDTO = toEventoResponseFeedDTO(eventos.get(eventoIndex++), usuario);
                feedUnificado.add(ItemFeedDTO.fromEvento(eventoDTO));
            }
        }

        return feedUnificado;
    }

    private PostagemResponseFeedDTO toPostagemResponseFeedDTO(Postagem postagem, Usuario usuarioLogado) {
        // Verifica se o usuário logado curtiu esta postagem
        boolean usuarioCurtiu = postagem.getCurtidas() != null
                && postagem.getCurtidas().stream()
                        .anyMatch(c -> c.getAutor().getId().equals(usuarioLogado.getId()));

        return new PostagemResponseFeedDTO(
                postagem.getId(),
                usuarioMapper.toUsuarioCardPostagemEventoDTO(postagem.getAutor()),
                postagem.getMidia(),
                postagem.getTitulo(),
                postagem.getDataPublicacao(),
                postagem.getCurtidas() != null ? postagem.getCurtidas().size() : 0,
                postagem.getComentarios() != null ? postagem.getComentarios().size() : 0,
                postagem.getCompartilhamentos(),
                postagem.getDescricao(),
                usuarioCurtiu
        );
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

        // Verifica se o usuário logado curtiu este evento
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
}
