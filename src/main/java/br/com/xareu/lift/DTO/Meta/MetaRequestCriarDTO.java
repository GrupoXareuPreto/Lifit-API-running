package br.com.xareu.lift.DTO.Meta;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaRequestCriarDTO {

    @NotBlank(message = "A meta deve conter um nome")
    private String nome;

    private boolean publica;

    private StatusMetaEnum status;

    private LocalDateTime dataInicio;

    @NotNull(message = "A data final deve ser definida")
    @Future(message = "A data deve estar sempre no futuro")
    private LocalDate dataFim;
}
