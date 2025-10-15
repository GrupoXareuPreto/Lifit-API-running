package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Conversa.ConversaRequestDTO;
import br.com.xareu.lift.DTO.Conversa.ConversaResponseDTO;
import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.ConversaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversas")
public class ConversaController {

    private final ConversaService service;

    public ConversaController(ConversaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConversaResponseDTO> criarConversa(
            @Valid @RequestBody ConversaRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        ConversaResponseDTO novaConversa = service.criarConversa(dto, usuarioLogado);
        return new ResponseEntity<>(novaConversa, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ConversaResponseDTO>> getMinhasConversas(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<ConversaResponseDTO> conversas = service.getMinhasConversas(usuarioLogado);
        return ResponseEntity.ok(conversas);
    }
}
