package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService service;

    @PostMapping
    public Evento criarEvento(@RequestBody Evento eventoNovo){
        return  service.criarEvento(eventoNovo);
    }

    @GetMapping
    public List<Evento> getAll(){
        return service.getAll();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarEvento(@PathVariable Long id, @RequestBody Evento eventoNovo){
        return service.atualizarEvento(eventoNovo, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarEvento(@PathVariable Long id ){
        if(service.deletarEvento(id)){
            return ResponseEntity.ok().body("O usario foi deletado com sucesso!!");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
