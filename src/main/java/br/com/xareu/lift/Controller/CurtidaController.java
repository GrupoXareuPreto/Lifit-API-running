package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Service.CurtidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

    @Autowired
    private CurtidaService curtidaService;

    @PostMapping
    public Curtida criarCurtida(@RequestBody Curtida curtidanova){
        return curtidaService.criarcurtida(curtidanova);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCurtida(@PathVariable Long id){
        Object deletado = curtidaService.excluirCurtida(id);

        if(deletado == ResponseEntity.notFound()){
            return ResponseEntity.ok().body("A curtida foi deletada");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
