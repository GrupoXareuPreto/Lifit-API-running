package br.com.xareu.lift.DTO.Comentario;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        Long id,
        String conteudo,
        LocalDateTime dataCriacao,
        Long autorId,
        String autorNomeUsuario,
        Long postagemId
) {}
