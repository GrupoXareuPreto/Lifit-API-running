package br.com.xareu.lift.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestAutenticarDTO(
        @NotBlank(message = "Deve-se colocar um nome de usuario ou email")
        String nomeUsuarioEmail,

        @NotBlank(message = "A senha é obrigatória")
        String senha
) {}
