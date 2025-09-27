package br.com.xareu.lift.DTO.Postagem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemRequestDTO {

    @NotBlank(message = "A publicacao precisa de uma midia")
    private String midia;

    @NotBlank(message = "A publicacao deve ter obrigatoriamente um titulo")
    private String titulo;

    private String descricao;

}
