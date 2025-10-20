package br.com.xareu.lift.DTO.Conversa;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record ConversaRequestDTO(
        @NotEmpty(message = "A lista de integrantes não pode ser vazia.")
        List<Long> integranteIds,

        String titulo,
        String foto,
        String descricao
) {}
