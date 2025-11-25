package br.com.xareu.lift.DTO.Feed;

import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;

public record ItemFeedDTO(
        String tipo, // "POSTAGEM" ou "EVENTO"
        PostagemResponseFeedDTO postagem,
        EventoResponseFeedDTO evento
) {
    public static ItemFeedDTO fromPostagem(PostagemResponseFeedDTO postagem) {
        return new ItemFeedDTO("POSTAGEM", postagem, null);
    }
    
    public static ItemFeedDTO fromEvento(EventoResponseFeedDTO evento) {
        return new ItemFeedDTO("EVENTO", null, evento);
    }
}
