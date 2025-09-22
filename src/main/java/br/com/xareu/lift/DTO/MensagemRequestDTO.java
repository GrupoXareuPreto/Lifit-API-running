package br.com.xareu.lift.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensagemRequestDTO {

    @NotBlank(message = "O conteudo da mensagem deve ser valorado")
    private String conteudo;

}
