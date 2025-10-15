package br.com.xareu.lift.DTO.Mensagem;

import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MensagemRequestDTO {
    @NotBlank(message = "O conteúdo não pode ser vazio")
    private String conteudo;
}
