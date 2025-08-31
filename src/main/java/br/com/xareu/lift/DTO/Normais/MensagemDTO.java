package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.ConversaResumoDTO;
import br.com.xareu.lift.Enum.StatusMensagemEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MensagemDTO {

    private Long id;
    private LocalDateTime dataEnvio;
    private StatusMensagemEnum status;
    private ConversaResumoDTO conversa;
}
