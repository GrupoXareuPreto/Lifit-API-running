package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Mensagem.MensagemRequestDTO;
import br.com.xareu.lift.DTO.Mensagem.MensagemResponseDTO;
import br.com.xareu.lift.Service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagem")
public class MensagemController {

    @Autowired
    private MensagemService service;

    @PostMapping("/usuario/{autorId}/conversa/{conversaId}")
    public ResponseEntity<MensagemResponseDTO> criarMensagem(@Valid @RequestBody MensagemRequestDTO mensagemDTO,@PathVariable Long autorId, @PathVariable Long conversaId){
        try{
            MensagemResponseDTO novaMensagem = service.criarMensagem(mensagemDTO, autorId, conversaId);
            return new ResponseEntity<>(novaMensagem, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponseDTO>> ListarMensagensConversa(@PathVariable Long conversaId){
        List<MensagemResponseDTO> mensagens = service.listarMensagensConversa(conversaId);
        return ResponseEntity.ok(mensagens);
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
