package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.ComentarioResumoDTO;
import br.com.xareu.lift.DTO.Resumos.CompartilhamentoResumoDTO;
import br.com.xareu.lift.DTO.Resumos.CurtidaResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostagemDTO {

    private Long id;
    private String midia;
    private String titulo;
    private String descricao;
    private LocalDateTime dataPublicacao;
    private List<CurtidaResumoDTO> curtidas;
    private List<ComentarioResumoDTO> comentarios;
    private List<CompartilhamentoResumoDTO> compartilhamentos;
}
