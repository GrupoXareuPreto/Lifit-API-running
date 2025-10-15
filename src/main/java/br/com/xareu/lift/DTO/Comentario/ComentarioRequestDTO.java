// src/main/java/br/com/xareu/lift/DTO/Comentario/ComentarioRequestDTO.java
package br.com.xareu.lift.DTO.Comentario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComentarioRequestDTO {

    @NotBlank(message = "O conteúdo do comentário não pode ser vazio")
    private String conteudo;

    // Apenas para a criação. Na atualização, não precisamos do ID do post.
    @NotNull(message = "O ID da postagem é obrigatório para criar um comentário")
    private Long postagemId;
}