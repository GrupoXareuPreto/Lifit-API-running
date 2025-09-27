package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Comentario;
import br.com.xareu.lift.Service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService service;

    @PostMapping
    public Comentario criarComentario(@RequestBody Comentario comentarioNovo){
        return service.criarComentario(comentarioNovo);
    }

    @GetMapping
    public List<Comentario> getAll(){
        return service.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarComentario(@PathVariable Long id){
        if(service.deletarComentario(id)){
            return ResponseEntity.ok().body("O comentario foi deletado com sucesso!!");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
