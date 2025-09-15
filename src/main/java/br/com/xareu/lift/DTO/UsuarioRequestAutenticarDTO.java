package br.com.xareu.lift.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestAutenticarDTO {

    @NotBlank(message = "Deve-se colocar um nome de usuario ou email")
    private String nomeUsuarioOuEmail;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}
