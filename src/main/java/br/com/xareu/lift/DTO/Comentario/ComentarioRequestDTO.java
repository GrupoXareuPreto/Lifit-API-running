package br.com.xareu.lift.DTO.Comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComentarioRequestDTO(
        @NotBlank(message = "O conteúdo do comentário não pode ser vazio")
        String conteudo,

        // Apenas para a criação. Na atualização, não precisamos do ID do post.
        @NotNull(message = "O ID da postagem é obrigatório para criar um comentário")
        Long postagemId
) {}
