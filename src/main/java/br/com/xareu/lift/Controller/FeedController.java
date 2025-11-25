package br.com.xareu.lift.Controller;

import br.com.xareu.lift.DTO.Feed.ItemFeedDTO;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/unificado")
    public ResponseEntity<List<ItemFeedDTO>> getFeedUnificado(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(defaultValue = "15") int tamanhoPagina,
            @RequestParam(required = false) String ultimoCursor) {
        
        List<ItemFeedDTO> feed = feedService.getFeedUnificado(usuarioLogado, tamanhoPagina, ultimoCursor);
        return ResponseEntity.ok(feed);
    }
}
