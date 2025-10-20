package br.com.xareu.lift.DTO.Mensagem;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import br.com.xareu.lift.Enum.StatusMensagemEnum;
import java.time.LocalDateTime;

public record MensagemResponseDTO(
        Long id,
        String conteudo,
        LocalDateTime dataEnvio,
        StatusMensagemEnum status,
        UsuarioResponseCardConversaDTO autor
) {}
