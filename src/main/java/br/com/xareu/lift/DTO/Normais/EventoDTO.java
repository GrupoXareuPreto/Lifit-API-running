package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class EventoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String localizacao;
    private String atividade;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private UsuarioResumoDTO autor;
    private List<UsuarioResumoDTO> participantes;
}
