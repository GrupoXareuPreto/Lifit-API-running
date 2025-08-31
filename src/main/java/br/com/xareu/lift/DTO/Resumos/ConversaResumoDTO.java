package br.com.xareu.lift.DTO.Resumos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversaResumoDTO {

    private Long id;
    private String foto;
    private String descricao;
}
