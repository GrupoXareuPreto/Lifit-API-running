package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.PostagemResumoDTO;
import br.com.xareu.lift.DTO.Resumos.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ComentarioDTO {

    private Long id;
    private String conteudo;
    private LocalDateTime dataCriacao;
    private UsuarioResumoDTO autor;
    private PostagemResumoDTO postagem;
}
