package br.com.xareu.lift.DTO;

import br.com.xareu.lift.Enum.StatusMensagemEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensagemResponseDTO {

    private String conteudo;
    private LocalDateTime dataEnvio;
    private StatusMensagemEnum status;
}
