package br.com.xareu.lift.DTO.Resumos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ComentarioResumoDTO {

    private Long id;
    private String conteudo;
    private LocalDateTime dataCriacao;
}
