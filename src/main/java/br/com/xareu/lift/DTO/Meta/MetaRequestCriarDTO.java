package br.com.xareu.lift.DTO.Meta;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MetaRequestCriarDTO(
        @NotBlank(message = "A meta deve conter um nome")
        String nome,

        boolean publica,

        StatusMetaEnum status,

        LocalDate dataInicio,

        @NotNull(message = "A data final deve ser definida")
        @Future(message = "A data deve estar sempre no futuro")
        LocalDate dataFim
) {}
