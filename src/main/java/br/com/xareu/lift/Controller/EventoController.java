package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Evento.EventoRequestCriarDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService service;

    @PostMapping
    public ResponseEntity<EventoResponseFeedDTO> criarEvento(
            @Valid @RequestBody EventoRequestCriarDTO eventoDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) { // Obtém o autor do token!

        EventoResponseFeedDTO eventoCriado = service.criarEvento(eventoDTO, usuarioLogado);
        return new ResponseEntity<>(eventoCriado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseFeedDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseFeedDTO> atualizarEvento(
            @PathVariable Long id,
            @Valid @RequestBody EventoRequestCriarDTO eventoDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        try {
            return service.atualizarEvento(id, eventoDTO, usuarioLogado)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalAccessException e) {
            // Se o serviço lançar a exceção de permissão, retorna 403 Forbidden.
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        try {
            service.deletarEvento(id, usuarioLogado);
            // Retorna 204 No Content para uma deleção bem-sucedida.
            return ResponseEntity.noContent().build();
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) { // Captura o "Evento não encontrado"
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
