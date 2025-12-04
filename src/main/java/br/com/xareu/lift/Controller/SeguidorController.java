package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Seguidor.SeguidorContaDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.SeguidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seguidor")
public class SeguidorController {

    private final SeguidorService seguidorService;

    @Autowired
    public SeguidorController(SeguidorService seguidorService) {
        this.seguidorService = seguidorService;
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Void> seguir(
            @PathVariable Long usuarioId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            seguidorService.seguir(usuarioId, usuarioLogado);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deixarDeSeguir(
            @PathVariable Long usuarioId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            seguidorService.deixarDeSeguir(usuarioId, usuarioLogado);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/conta/{usuarioId}")
    public ResponseEntity<SeguidorContaDTO> obterContadores(
            @PathVariable Long usuarioId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            long seguidores = seguidorService.contarSeguidores(usuarioId);
            long seguindo = seguidorService.contarSeguindo(usuarioId);
            boolean estaSeguindo = usuarioLogado != null && seguidorService.verificarSeSegue(usuarioLogado, usuarioId);

            SeguidorContaDTO conta = new SeguidorContaDTO(seguidores, seguindo, estaSeguindo);
            return ResponseEntity.ok(conta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
