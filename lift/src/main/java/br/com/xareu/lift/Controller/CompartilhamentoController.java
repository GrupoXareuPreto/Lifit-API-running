package br.com.xareu.lift.Controller;

import br.com.xareu.lift.Entity.Compartilhamento;
import br.com.xareu.lift.Service.CompartilhamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compartilhamento")
public class CompartilhamentoController {

    @Autowired
    private CompartilhamentoService service;

    @GetMapping
    public List<Compartilhamento> getAll(){
        return service.getAll();
    }

    @PostMapping
    public Compartilhamento criarCompartilhamento(@RequestBody Compartilhamento compartilhamentoNovo){
        return service.criarCompartilhamento(compartilhamentoNovo);
    }

}
