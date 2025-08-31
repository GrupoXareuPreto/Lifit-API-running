package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.MensagemResumoDTO;
import br.com.xareu.lift.DTO.Resumos.PostagemResumoDTO;
import br.com.xareu.lift.DTO.Resumos.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompartilhamentoDTO {

    private Long id;
    private UsuarioResumoDTO autor;
    private PostagemResumoDTO postagem;
    private MensagemResumoDTO mensagem;
}
