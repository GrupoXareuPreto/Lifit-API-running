package br.com.xareu.lift.DTO.Postagem;

import java.time.LocalDateTime;
import java.util.List;

public record PostagemFeedResponseDTO(
        List<PostagemResponseFeedDTO> postagens,
        LocalDateTime nextCursor,
        boolean temMais)
{ }
