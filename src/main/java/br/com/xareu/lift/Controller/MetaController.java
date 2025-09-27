package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Meta.MetaRequestDTO;
import br.com.xareu.lift.DTO.Meta.MetaResponseDTO;
import br.com.xareu.lift.Service.MetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meta")
public class MetaController {

    @Autowired
    private MetaService service;

    @PostMapping("/usuario/{autorId}")
    public ResponseEntity<MetaResponseDTO> criarMeta(@Valid @RequestBody MetaRequestDTO metaNova, @PathVariable Long autorId){
        try {
            MetaResponseDTO novaMeta = service.criarMeta(metaNova, autorId);
            return new ResponseEntity<>(novaMeta, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<MetaResponseDTO>> getAll(){
        List<MetaResponseDTO> metas = service.getAll();
        return ResponseEntity.ok(metas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseDTO> atualizarMeta(@Valid @RequestBody MetaRequestDTO metaDTO, @PathVariable Long id ){
        return service.atualizarMeta(metaDTO, id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirMeta(@PathVariable Long id){
        boolean deletado = service.deletarMeta(id);

        if(deletado){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
