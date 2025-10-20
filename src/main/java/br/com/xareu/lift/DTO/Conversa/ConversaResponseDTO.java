package br.com.xareu.lift.DTO.Conversa;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import java.util.List;

public record ConversaResponseDTO(
        Long id,
        String titulo,
        String foto,
        String descricao,
        List<UsuarioResponseCardConversaDTO> integrantes
) {}
