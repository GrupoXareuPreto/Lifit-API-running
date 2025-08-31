package br.com.xareu.lift.DTO.Normais;

import br.com.xareu.lift.DTO.Resumos.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String biografia;
    private String nomeUsuario;
    private String email;
    private String senha;
    private List<PostagemResumoDTO> postagens;
    private List<MetaResumoDTO> metas;
    private List<CurtidaResumoDTO> curtidas;
    private List<ComentarioResumoDTO> comentarios;
    private List<EventoResumoDTO> eventosCriados;
    private List<MensagemResumoDTO> mensagens;
    private List<CompartilhamentoResumoDTO> compartilhamentos;
    private List<EventoResumoDTO> eventosPartipar;
    private List<UsuarioResumoDTO> seguindores;
    private List<UsuarioResumoDTO> seguindo;
    private List<ConversaResumoDTO> conversas;

}
