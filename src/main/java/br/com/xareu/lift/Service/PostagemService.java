package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Postagem.PostagemRequestDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseDTO;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.PostagemRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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


    private PostagemResponseDTO toResponseDTO(Postagem postagem){
        if(postagem == null){
            return null;
        }
        else {
            return new PostagemResponseDTO(
                    usuarioService.toUsuarioCardPostagemDTO(postagem.getAutor()),
                    postagem.getMidia(),
                    postagem.getTitulo(),
                    postagem.getDescricao(),
                    postagem.getDataPublicacao(),
                    postagem.getCurtidas() != null ? postagem.getCurtidas().size() : 0,
                    postagem.getComentarios() != null ? postagem.getComentarios().size() : 0,
                    postagem.getCompartilhamentos()
            );
        }
    }


/*--------------------------------------------------------------------------------------------------------------------*/

    public PostagemResponseDTO criarPostagem (PostagemRequestDTO postagemDTO, Long autorId){
        Usuario autor = usuarioRepository.findById(autorId).orElseThrow(() -> new IllegalArgumentException("Autor nao encontrado" + autorId));

        Postagem postagem = new Postagem();
        postagem.setMidia(postagemDTO.getMidia());
        postagem.setTitulo(postagemDTO.getTitulo());
        postagem.setDescricao(postagemDTO.getDescricao());
        postagem.setAutor(autor);

        Postagem postagemNova = repository.save(postagem);
        return toResponseDTO(postagemNova);
    }



    public List<PostagemResponseDTO> getFeed(){
      return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }


    public boolean deletarPostagem(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
