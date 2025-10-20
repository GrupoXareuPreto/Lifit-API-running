package br.com.xareu.lift.DTO.Curtida;

import jakarta.validation.constraints.NotNull;

public record CurtidaRequestDTO(
    @NotNull(message = "O ID da postagem é obrigatório")
    Long postagemId
) {}
