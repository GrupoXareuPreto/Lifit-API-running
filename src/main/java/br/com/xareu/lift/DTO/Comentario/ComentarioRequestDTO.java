package br.com.xareu.lift.DTO.Comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioRequestDTO(
        @NotBlank(message = "O conteúdo do comentário não pode ser vazio")
        String conteudo,

        Long postagemId,

        Long eventoId
) {}
