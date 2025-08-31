package br.com.xareu.lift.DTO.Resumos;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetaResumoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private StatusMetaEnum status;
}
