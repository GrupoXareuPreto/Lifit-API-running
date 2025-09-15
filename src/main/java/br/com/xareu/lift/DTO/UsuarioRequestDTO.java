package br.com.xareu.lift.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {


    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String biografia;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "O nome de usuario é obrigatório")
    @Size(min = 3, max = 10, message = "O nome de usuario deve ter entre 3 a 20 caracteres")
    private String nomeUsuario;

}
