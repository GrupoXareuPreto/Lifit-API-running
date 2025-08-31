package br.com.xareu.lift.DTO.Resumos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostagemResumoDTO {

        private Long id;
        private String titulo;
        private String midia;
        private String descricao;
        private LocalDateTime dataPublicacao;

}
