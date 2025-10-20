package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Postagem.PostagemFeedResponseDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemRequestCriarDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.PostagemMapper;
import br.com.xareu.lift.Repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;


@Service
public class PostagemService {

    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private PostagemMapper postagemMapper;

    @Transactional
    public PostagemResponseFeedDTO criarPostagem(PostagemRequestCriarDTO dto, Usuario autor) {
        Postagem postagem = new Postagem();
        postagem.setAutor(autor);
        postagem.setMidia(dto.midia());
        postagem.setDescricao(dto.descricao());
        postagem.setTitulo(dto.titulo());


        Postagem savedPostagem = postagemRepository.save(postagem);
        return postagemMapper.toResponseFeedDTO(savedPostagem);
    }


    @Transactional
    public void deletePostagem(Long postagemId, Usuario usuarioLogado) throws IllegalAccessException {

        Postagem postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        if (!postagem.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para deletar esta postagem.");
        }

        postagemRepository.delete(postagem);
    }

    @Transactional
    public List<PostagemResponseFeedDTO> getPostagensByAutor(Usuario autor) {
        List<Postagem> postagens = postagemRepository.findByAutor(autor);
        return postagemMapper.toResponseFeedDTOList(postagens);
    }

    @Transactional(readOnly = true)
    public PostagemFeedResponseDTO getFeedInfinito(Usuario usuarioLogado, LocalDateTime ultimoCursor, int tamanhoPagina) {

        List<PostagemResponseFeedDTO> feedFinal = new ArrayList<>();
        List<Usuario> seguindo = usuarioLogado.getSeguindo();

        // 1️⃣ Posts de quem o usuário segue
        List<Postagem> postsSeguindo = postagemRepository.findFeedSeguindo(
                seguindo, ultimoCursor, PageRequest.of(0, tamanhoPagina)
        );

        feedFinal.addAll(postagemMapper.toResponseFeedDTOList(postsSeguindo));

        // 2️⃣ Se faltar, preenche com posts "explorar"
        if (postsSeguindo.size() < tamanhoPagina) {
            int faltando = tamanhoPagina - postsSeguindo.size();
            List<Postagem> postsExplorar = postagemRepository.findFeedExplorar(
                    ultimoCursor, PageRequest.of(0, faltando)
            );
            feedFinal.addAll(postagemMapper.toResponseFeedDTOList(postsExplorar));
        }

        // 3️⃣ Calcula o novo cursor
        LocalDateTime nextCursor = null;
        boolean hasMore = false;

        if (!feedFinal.isEmpty()) {
            // O cursor é a data de publicação do último post retornado
            nextCursor = feedFinal.get(feedFinal.size() - 1).dataPublicacao();

            // Verifica se ainda há posts mais antigos
            hasMore = postagemRepository.count() > feedFinal.size(); // simples, pode otimizar depois
        }

        return new PostagemFeedResponseDTO(feedFinal, nextCursor, hasMore);
    }


}
