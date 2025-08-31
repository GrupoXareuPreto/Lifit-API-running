package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Meta;
import br.com.xareu.lift.Service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    private MetaService service;

    @PostMapping
    public Meta criarmeta(@RequestBody Meta metaNova){
        return service.criarMeta(metaNova);
    }

    @GetMapping
    public List<Meta> getAll(){
        return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMeta(@RequestBody Meta meta, @PathVariable Long id ){
        return service.atualizarMeta(meta, id).map(ResponseEntity ::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirMeta(@PathVariable Long id){
        boolean deletado = service.deletarMeta(id);

        if(deletado){
            return ResponseEntity.ok().body("A meta foi excluida com sucesso");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
