package br.com.xareu.lift.DTO.Mensagem;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import br.com.xareu.lift.Enum.StatusMensagemEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class MensagemResponseDTO {
    private Long id;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private StatusMensagemEnum status;
    private UsuarioResponseCardConversaDTO autor;
}
