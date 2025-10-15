package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Postagem.PostagemRequestCriarDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseImagemDTO;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.PostagemMapper;
import br.com.xareu.lift.Repository.PostagemRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostagemService {

    private PostagemRepository repository;
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    public PostagemService(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository, UsuarioService usuarioService)
    {
        this.repository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }
/*--------------------------------------------------------------------------------------------------------------------*/
/*Parte de DTOs*/


   /* private PostagemResponseFeedDTO toResponseDTO(Postagem postagem){
        if(postagem == null){
            return null;
        }
        else {
            return new PostagemResponseFeedDTO(
                    usuarioService.toUsuarioCardPostagemEventoDTO(postagem.getAutor()),
                    postagem.getMidia(),
                    postagem.getTitulo(),
                    postagem.getDataPublicacao(),
                    postagem.getCurtidas() != null ? postagem.getCurtidas().size() : 0,
                    postagem.getComentarios() != null ? postagem.getComentarios().size() : 0,
                    postagem.getCompartilhamentos()
            );
        }
    }

    public PostagemResponseImagemDTO toPostagemResponseImagemDTO(Postagem postagem){
        if(postagem == null){
            return null;
        }
        else {
            return new PostagemResponseImagemDTO(
                    postagem.getMidia()
            );
        }
    }
*/

/*--------------------------------------------------------------------------------------------------------------------*/

    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private PostagemMapper postagemMapper;

    @Transactional
    public PostagemResponseFeedDTO criarPostagem(PostagemRequestCriarDTO dto, Usuario autor) {
        Postagem postagem = new Postagem();
        postagem.setAutor(autor);
        postagem.setMidia(dto.getMidia());
        postagem.setDescricao(dto.getDescricao());
        postagem.setTitulo(dto.getTitulo());


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


    public List<PostagemResponseFeedDTO> getPostagensByAutor(Usuario autor) {
        List<Postagem> postagens = postagemRepository.findByAutor(autor);
        return postagemMapper.toResponseFeedDTOList(postagens);
    }
}
