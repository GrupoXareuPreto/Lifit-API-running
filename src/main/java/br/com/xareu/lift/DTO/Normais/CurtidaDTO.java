package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.PostagemResumoDTO;
import br.com.xareu.lift.DTO.Resumos.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurtidaDTO {

    private Long id;
    private PostagemResumoDTO postagem;
    private UsuarioResumoDTO autor;
}
