package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Comentario;
import br.com.xareu.lift.Repository.ComentarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {
    private ComentarioRepository repository;

    public ComentarioService(ComentarioRepository comentarioRepository){
        this.repository = comentarioRepository;
    }

    public List<Comentario> getAll(){
        return repository.findAll();
    }

    public Comentario criarComentario(Comentario comentario){return repository.save(comentario);}

    public boolean deletarComentario(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;

        }
        return false;
    }

}
