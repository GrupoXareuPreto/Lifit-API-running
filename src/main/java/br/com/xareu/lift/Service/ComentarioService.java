package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Comentario;
import br.com.xareu.lift.Repository.ComentarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {
    private ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository){
        this.comentarioRepository = comentarioRepository;
    }

    public Comentario criarComentario(Comentario comentario){return comentarioRepository.save(comentario);}

    public Object deletarComentario(Long id){
        if (comentarioRepository.existsById(id)){
            comentarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound();
    }

}
