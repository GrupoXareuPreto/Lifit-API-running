package br.com.xareu.lift.DTO.Meta;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaResponseDTO {

    private String nome;
    private boolean publica;
    private StatusMetaEnum status;
    private LocalDate dataFim;
}
