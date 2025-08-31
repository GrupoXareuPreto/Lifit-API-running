package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Resumos.CurtidaResumoDTO;
import br.com.xareu.lift.DTO.Normais.PostagemDTO;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Repository.PostagemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostagemService {

    private PostagemRepository postagemRepository;

    public PostagemService(PostagemRepository postagemRepository) {
        this.postagemRepository = postagemRepository;
    }

    /*crud*/
    public Postagem criarPostagem (Postagem postagemNova){
        return  postagemRepository.save(postagemNova);
    }

    public List<Postagem> getAll(){
        return postagemRepository.findAll();
    }

    /*Postagem nao pode ser editada!!!!*/

    public boolean deletarPostagem(Long id){
        if(postagemRepository.existsById(id)){
            postagemRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
