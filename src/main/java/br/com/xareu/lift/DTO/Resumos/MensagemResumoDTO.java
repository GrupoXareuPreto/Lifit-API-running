package br.com.xareu.lift.DTO.Resumos;

import br.com.xareu.lift.Enum.StatusMensagemEnum;
import br.com.xareu.lift.Enum.StatusMetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MensagemResumoDTO {

    private Long id;
    private LocalDateTime dataEnvio;
    private StatusMensagemEnum status;
}
