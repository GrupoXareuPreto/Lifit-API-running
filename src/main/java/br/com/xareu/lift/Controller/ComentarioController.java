package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Comentario.ComentarioRequestDTO;
import br.com.xareu.lift.DTO.Comentario.ComentarioResponseDTO;
import br.com.xareu.lift.Entity.Comentario;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService service;

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criarComentario(
            @Valid @RequestBody ComentarioRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        ComentarioResponseDTO novoComentario = service.criarComentario(dto, usuarioLogado);
        return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<ComentarioResponseDTO>> getComentariosPorPostagem(@PathVariable Long postagemId) {
        try {
            List<ComentarioResponseDTO> comentarios = service.getComentariosPorPostagem(postagemId);
            return ResponseEntity.ok(comentarios);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComentario(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            service.deletarComentario(id, usuarioLogado);
            return ResponseEntity.noContent().build();
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
