package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Mensagem.MensagemRequestDTO;
import br.com.xareu.lift.DTO.Mensagem.MensagemResponseDTO;
import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Entity.Mensagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Enum.StatusMensagemEnum;
import br.com.xareu.lift.Mapper.MensagemMapper;
import br.com.xareu.lift.Repository.ConversaRepository;
import br.com.xareu.lift.Repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private  MensagemRepository mensagemRepository;
    @Autowired
    private  ConversaRepository conversaRepository;
    @Autowired
    private MensagemMapper mapper;

    @Transactional
    public MensagemResponseDTO criarMensagem(Long conversaId, MensagemRequestDTO dto, Usuario autorLogado) throws IllegalAccessException {
//        Conversa conversa = conversaRepository.findById(conversaId)
//                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));
//
//
//        boolean isIntegrante = conversa.getIntegrantes().stream().anyMatch(u -> u.getId().equals(autorLogado.getId()));
//        if (!isIntegrante) {
//            throw new IllegalAccessException("Você não é um integrante desta conversa.");
//        }
//
//        Mensagem novaMensagem = new Mensagem();
//        novaMensagem.setConteudo(dto.conteudo());
//        novaMensagem.setAutor(autorLogado);
//        novaMensagem.setConversa(conversa);
//        novaMensagem.setDataEnvio(LocalDateTime.now());
//        novaMensagem.setStatus(StatusMensagemEnum.ENVIADA_NAO_RECEBIDA);
//
//        Mensagem savedMensagem = mensagemRepository.save(novaMensagem);
//        return mapper.toResponseDTO(savedMensagem);
    }


    public List<MensagemResponseDTO> listarMensagensConversa(Long conversaId, Usuario usuarioLogado) throws IllegalAccessException {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada"));

        boolean isIntegrante = conversa.getIntegrantes().stream().anyMatch(u -> u.getId().equals(usuarioLogado.getId()));
        if (!isIntegrante) {
            throw new IllegalAccessException("Você não tem permissão para ver as mensagens desta conversa.");
        }

        return mapper.toResponseDTOList(mensagemRepository.findByConversaIdOrderByDataEnvioAsc(conversaId));
    }



    @Transactional
    public void deletarMensagem(Long mensagemId, Usuario usuarioLogado) throws IllegalAccessException {
        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));

        if (!mensagem.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para deletar esta mensagem.");
        }

        mensagemRepository.delete(mensagem);
    }
}
