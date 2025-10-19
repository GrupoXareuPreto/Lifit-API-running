package br.com.xareu.lift.DTO.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome é obrigatorio")
        String nome,

        String fotoPerfil,

        String biografia,

        @NotBlank(message = "O email é obrigatorio")
        @Email(message = "O email deve ser valido")
        String email,

        @NotBlank(message = "A senha é obrigatoria")
        @Size(min = 6, message = "a senha deve ter no minimo 6 caracteres")
        String senha,

        @NotBlank(message = "O nome de usuario é obrigatório")
        String nomeUsuario
) {}
