package br.com.xareu.lift.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResumoDTO {

    private String fotoPerfil;
    private String nome;
    private String nomeUsuario;

}
