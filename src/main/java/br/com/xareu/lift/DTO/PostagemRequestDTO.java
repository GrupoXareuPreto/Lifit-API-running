package br.com.xareu.lift.DTO;

import br.com.xareu.lift.Entity.Evento;
import ch.qos.logback.core.util.StringUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemRequestDTO {

    @NotBlank(message = "A publicacao precisa de uma midia")
    private String midia;

    @NotBlank(message = "A publicacao deve ter obrigatoriamente um titulo")
    private String titulo;

    private String descricao;

}
