package br.com.xareu.lift.DTO.Postagem;

import jakarta.validation.constraints.NotBlank;

public record PostagemRequestCriarDTO(
        @NotBlank(message = "A publicacao precisa de uma midia")
        String midia,

        @NotBlank(message = "A publicacao deve ter obrigatoriamente um titulo")
        String titulo,

        String descricao
) {}
