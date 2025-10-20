package br.com.xareu.lift.DTO.Evento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventoRequestCriarDTO (
        @NotBlank(message = "O titulo é obrigatório")
        String titulo,

        @NotBlank(message = "O evento deve conter uma midia")
        String midia,
        
        String descricao,
        
        @NotBlank(message = "A Localização do evento é obrigatoria")
        String localizacao,
        
        @NotNull(message = "A data de inicio é obrigatoria")
        @Future(message = "A data de inicio deve estar sempre no fututo")
        LocalDateTime dataInicio,
        
        @Future(message = "A daat final deve estar sempre no futuro")
        LocalDateTime dataFim
) { }
