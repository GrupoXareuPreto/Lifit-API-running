package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Curtida.CurtidaRequestDTO;
import br.com.xareu.lift.DTO.Curtida.CurtidaResponseDTO;
import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.CurtidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

    @Autowired
    private CurtidaService service;

    @PostMapping
    public ResponseEntity<CurtidaResponseDTO> criarCurtida(
            @Valid @RequestBody CurtidaRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            CurtidaResponseDTO novaCurtida = service.criarCurtida(dto, usuarioLogado);
            return new ResponseEntity<>(novaCurtida, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Retorna 409 Conflict se o usuário já curtiu
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    @DeleteMapping("/postagem/{postagemId}")
    public ResponseEntity<Void> excluirCurtida(
            @PathVariable Long postagemId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            service.excluirCurtida(postagemId, usuarioLogado);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content para sucesso
        } catch (RuntimeException e) {
            // Captura "Postagem não encontrada" ou "Curtida não encontrada"
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<CurtidaResponseDTO>> getCurtidasPorPostagem(@PathVariable Long postagemId) {
        try {
            List<CurtidaResponseDTO> curtidas = service.getCurtidasPorPostagem(postagemId);
            return ResponseEntity.ok(curtidas);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
