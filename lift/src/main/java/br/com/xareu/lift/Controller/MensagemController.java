package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Mensagem;
import br.com.xareu.lift.Service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

    @Autowired
    private MensagemService service;

    @PostMapping
    public Mensagem criarMensagem(@RequestBody Mensagem mensagemNova){
        return service.criarMensagem(mensagemNova);
    }

    @GetMapping
    public List<Mensagem> getAll(){
        return  service.getAll();
    }

    /*Mensagem nao tem update*/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMensagem(@PathVariable Long id){
        if(service.deletarMensagem(id)){
            return ResponseEntity.ok().body("mensagem apagada com sucesso!!");
        }
        else {
            return  ResponseEntity.notFound().build();
        }
    }


}
