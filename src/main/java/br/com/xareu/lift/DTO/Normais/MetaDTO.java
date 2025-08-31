package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.UsuarioResumoDTO;
import br.com.xareu.lift.Enum.StatusMetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetaDTO {

    private Long id;
    private UsuarioResumoDTO autor;
    private String nome;
    private String descricao;
    private StatusMetaEnum status;
}
