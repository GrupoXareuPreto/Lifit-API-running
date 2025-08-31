package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Mensagem;
import br.com.xareu.lift.LiftApplication;
import br.com.xareu.lift.Service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @PostMapping
    public Mensagem criarMensagem(@RequestBody Mensagem mensagemNova){
        return mensagemService.criarMensagem(mensagemNova);
    }

    @GetMapping
    public List<Mensagem> getAll(){
        return  mensagemService.getAll();
    }

    /*Mensagem nao tem update*/
    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMensagem(@PathVariable Long id){
        return mensagemService.deletarMensagem(id);
    }

     */
}
