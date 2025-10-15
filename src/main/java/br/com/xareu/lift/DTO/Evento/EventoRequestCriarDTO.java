package br.com.xareu.lift.DTO.Evento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequestCriarDTO {

    @NotBlank(message = "O titulo é obrigatório")
    private String titulo;

    @NotBlank(message = "O evento deve conter uma midia")
    private String midia;

    private String descricao;

    @NotBlank(message = "A Localização do evento é obrigatoria")
    private String localizacao;

    @NotBlank(message = "A data de inicio é obrigatoria")
    @Future(message = "A data de inicio deve estar sempre no fututo")
    private LocalDateTime dataInicio;

    @Future(message = "A daat final deve estar sempre no futuro")
    private LocalDateTime dataFim;

}
