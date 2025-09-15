package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Service.ConversaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversa")
public class ConversaController {

    @Autowired
    private ConversaService service;

    @GetMapping
    public List<Conversa> getAll(){
        return service.getAll();
    }

    @PostMapping
    public Conversa  criarConversa(@RequestBody Conversa conversaNova){
        return  service.criarconversa(conversaNova);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarConversa(@RequestBody Conversa conversa, @PathVariable Long id){
        return service.atualizarConversa(conversa, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarConversa(@PathVariable Long id){
        if (service.excluirConversa(id)){
            return ResponseEntity.ok().body("Conversa apagada com sucesso!!");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
