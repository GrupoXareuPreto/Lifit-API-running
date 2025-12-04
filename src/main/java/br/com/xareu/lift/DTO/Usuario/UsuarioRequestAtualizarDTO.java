package br.com.xareu.lift.DTO.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestAtualizarDTO(
        @NotBlank(message = "O nome é obrigatorio")
        String nome,

        String fotoPerfil,

        String biografia,

        @NotBlank(message = "O email é obrigatorio")
        @Email(message = "O email deve ser valido")
        String email,

        String senha, // Opcional para atualização

        @NotBlank(message = "O nome de usuario é obrigatório")
        String nomeUsuario
) {}
