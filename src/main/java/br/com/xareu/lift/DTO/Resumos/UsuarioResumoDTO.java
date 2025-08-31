package br.com.xareu.lift.DTO.Resumos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResumoDTO {

    private Long id;
    private String nome;
    private String biografia;
    private String email;
    private String senha;
    private String nomeUsuario;
}
