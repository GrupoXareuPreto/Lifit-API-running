package br.com.xareu.lift.DTO.Mensagem;

import jakarta.validation.constraints.NotBlank;

public record MensagemRequestDTO(
        @NotBlank(message = "O conteúdo não pode ser vazio")
        String conteudo
) {}
