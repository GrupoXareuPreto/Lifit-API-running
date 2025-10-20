package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Mensagem.MensagemRequestDTO;
import br.com.xareu.lift.DTO.Mensagem.MensagemResponseDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Entity.Mensagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Enum.StatusMensagemEnum;
import br.com.xareu.lift.Repository.ConversaRepository;
import br.com.xareu.lift.Repository.MensagemRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;

    public MensagemService(MensagemRepository mensagemRepository, ConversaRepository conversaRepository) {
        this.mensagemRepository = mensagemRepository;
        this.conversaRepository = conversaRepository;
    }


/*--------------------------------------------------------------------------------------------------------------------*/
/*Parte de DTOs*/

    private MensagemResponseDTO toResponseDTO(Mensagem mensagem) {
        UsuarioResponseCardConversaDTO autorDTO = new UsuarioResponseCardConversaDTO(
                mensagem.getAutor().getId(),
                mensagem.getAutor().getNomeUsuario(),
                mensagem.getAutor().getFotoPerfil()
        );
        return new MensagemResponseDTO(
                mensagem.getId(),
                mensagem.getConteudo(),
                mensagem.getDataEnvio(),
                mensagem.getStatus(),
                autorDTO
        );
    }

/*--------------------------------------------------------------------------------------------------------------------*/


    @Transactional
    public MensagemResponseDTO criarMensagem(Long conversaId, MensagemRequestDTO dto, Usuario autorLogado) throws IllegalAccessException {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        // *** VERIFICAÇÃO DE PERMISSÃO CRUCIAL ***
        boolean isIntegrante = conversa.getIntegrantes().stream().anyMatch(u -> u.getId().equals(autorLogado.getId()));
        if (!isIntegrante) {
            throw new IllegalAccessException("Você não é um integrante desta conversa.");
        }

        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setConteudo(dto.conteudo());
        novaMensagem.setAutor(autorLogado);
        novaMensagem.setConversa(conversa);
        novaMensagem.setDataEnvio(LocalDateTime.now());
        novaMensagem.setStatus(StatusMensagemEnum.ENVIADA_NAO_RECEBIDA); // Status mais apropriado

        Mensagem savedMensagem = mensagemRepository.save(novaMensagem);
        return toResponseDTO(savedMensagem);
    }


    public List<MensagemResponseDTO> listarMensagensConversa(Long conversaId, Usuario usuarioLogado) throws IllegalAccessException {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        // *** VERIFICAÇÃO DE PERMISSÃO CRUCIAL ***
        boolean isIntegrante = conversa.getIntegrantes().stream().anyMatch(u -> u.getId().equals(usuarioLogado.getId()));
        if (!isIntegrante) {
            throw new IllegalAccessException("Você não tem permissão para ver as mensagens desta conversa.");
        }

        return mensagemRepository.findByConversaIdOrderByDataEnvioAsc(conversaId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public void deletarMensagem(Long mensagemId, Usuario usuarioLogado) throws IllegalAccessException {
        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        // *** VERIFICAÇÃO DE PERMISSÃO: Apenas o autor da mensagem pode deletá-la ***
        if (!mensagem.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para deletar esta mensagem.");
        }

        mensagemRepository.delete(mensagem);
    }
}
