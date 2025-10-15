// VERSÃO SEGURA E CORRETA
package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Postagem.PostagemRequestCriarDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.PostagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/postagens")
public class PostagemController {

    @Autowired
    private PostagemService postagemService;


    @PostMapping
    public ResponseEntity<PostagemResponseFeedDTO> createPostagem(
            @Valid @RequestBody PostagemRequestCriarDTO postagemDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        PostagemResponseFeedDTO novaPostagem = postagemService.criarPostagem(postagemDTO, usuarioLogado);
        return new ResponseEntity<>(novaPostagem, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostagem(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        try {
            postagemService.deletePostagem(id, usuarioLogado);
            return ResponseEntity.noContent().build();
        } catch (IllegalAccessException e) {

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }


    @GetMapping("/me")
    public ResponseEntity<List<PostagemResponseFeedDTO>> getMinhasPostagens(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<PostagemResponseFeedDTO> minhasPostagens = postagemService.getPostagensByAutor(usuarioLogado);
        return ResponseEntity.ok(minhasPostagens);
    }

    // Outros endpoints (GET por ID, GET todos, etc.) podem continuar existindo
    // e não precisam necessariamente do usuário logado, a menos que você tenha
    // postagens privadas.
}