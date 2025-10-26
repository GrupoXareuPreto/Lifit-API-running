package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Mensagem.MensagemRequestDTO;
import br.com.xareu.lift.DTO.Mensagem.MensagemResponseDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/conversas/{conversaId}/mensagens")
public class MensagemController {

    private final MensagemService service;

    public MensagemController(MensagemService service) {
        this.service = service;
    }

//    @PostMapping
//    public ResponseEntity<MensagemResponseDTO> criarMensagem(
//            @Valid @RequestBody MensagemRequestDTO dto,
//            @AuthenticationPrincipal Usuario usuarioLogado,
//            @PathVariable Long idDestinatario) {
//        try {
//            MensagemResponseDTO novaMensagem = service.criarMensagem(dto, usuarioLogado);
//            return new ResponseEntity<>(novaMensagem, HttpStatus.CREATED);
//        } catch (IllegalAccessException e) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
//        }
//        try{
//
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
//        }
//    }

    @GetMapping
    public ResponseEntity<List<MensagemResponseDTO>> listarMensagens(
            @PathVariable Long conversaId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            List<MensagemResponseDTO> mensagens = service.listarMensagensConversa(conversaId, usuarioLogado);
            return ResponseEntity.ok(mensagens);
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}