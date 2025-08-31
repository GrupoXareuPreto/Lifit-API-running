package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.MensagemResumoDTO;
import br.com.xareu.lift.DTO.Resumos.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConversaDTO {

    private Long id;
    private String foto;
    private String descricao;
    private List<MensagemResumoDTO> mensagens;
    private List<UsuarioResumoDTO> integrantes;
}
