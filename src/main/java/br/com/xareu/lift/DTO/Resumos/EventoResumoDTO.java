package br.com.xareu.lift.DTO.Resumos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventoResumoDTO {

    private Long id;
    private String descricao;
    private String titulo;
    private String localizacao;
    private String atividade;
    private LocalDateTime dataInico;
    private LocalDateTime dataFim;
}
