package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Conversa.ConversaRequestDTO;
import br.com.xareu.lift.DTO.Conversa.ConversaResponseDTO;
import br.com.xareu.lift.DTO.Curtida.CurtidaResponseDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.ConversaRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversaService {
    private final ConversaRepository conversaRepository;
    private final UsuarioRepository usuarioRepository;

    public ConversaService(ConversaRepository conversaRepository, UsuarioRepository usuarioRepository) {
        this.conversaRepository = conversaRepository;
        this.usuarioRepository = usuarioRepository;
    }
/*--------------------------------------------------------------------------------------------------------------------*/
/*parte de DTOs*/



    private ConversaResponseDTO toResponseDTO(Conversa conversa) {
        List<UsuarioResponseCardConversaDTO> integrantesDTO = conversa.getIntegrantes().stream()
                .map(u -> new UsuarioResponseCardConversaDTO(u.getId(), u.getNomeUsuario(), u.getFotoPerfil()))
                .collect(Collectors.toList());

        return new ConversaResponseDTO(
                conversa.getId(),
                conversa.getTitulo(),
                conversa.getFoto(),
                conversa.getDescricao(),
                integrantesDTO
        );
    }


/*--------------------------------------------------------------------------------------------------------------------*/





    @Transactional
    public ConversaResponseDTO criarConversa(ConversaRequestDTO dto, Usuario criador) {
        // Recarrega o criador dentro da transação para evitar LazyInitializationException
        Usuario criadorManaged = usuarioRepository.findById(criador.getId())
                .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado"));
        
        // Busca os integrantes no banco de dados a partir dos IDs
        List<Usuario> integrantes = usuarioRepository.findAllById(dto.integranteIds());
        // Adiciona o próprio criador à lista de integrantes
        integrantes.add(criadorManaged);

        if (integrantes.size() < 2) {
            throw new IllegalArgumentException("Uma conversa precisa de pelo menos 2 integrantes.");
        }

        // Se for uma conversa de 2 pessoas, verifica se já existe
        if (integrantes.size() == 2) {
            Usuario outroUsuario = integrantes.stream()
                    .filter(u -> !u.getId().equals(criadorManaged.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Outro usuário não encontrado"));
            
           
        }

        Conversa novaConversa = new Conversa();
        novaConversa.setIntegrantes(integrantes);

        // Lógica para título: se for uma conversa de 2 pessoas, o título pode ser o nome do outro.
        // Se for em grupo, usa o título do DTO.
        if (integrantes.size() == 2) {
            Usuario outroUsuario = integrantes.stream()
                    .filter(u -> !u.getId().equals(criadorManaged.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Outro usuário não encontrado"));
            novaConversa.setTitulo(outroUsuario.getNomeUsuario());
        } else {
            novaConversa.setTitulo(dto.titulo());
            novaConversa.setFoto(dto.foto());
            novaConversa.setDescricao(dto.descricao());
        }

        Conversa conversaSalva = conversaRepository.save(novaConversa);

        // Atualiza a lista de conversas de cada integrante
        for (Usuario integrante : integrantes) {
            // Recarrega cada integrante para ter acesso às coleções lazy
            Usuario integranteManaged = usuarioRepository.findById(integrante.getId())
                    .orElseThrow(() -> new RuntimeException("Integrante não encontrado"));
            
            if (integranteManaged.getConversas() == null) {
                integranteManaged.setConversas(new ArrayList<>());
            }
            integranteManaged.getConversas().add(conversaSalva);
            usuarioRepository.save(integranteManaged);
        }

        return toResponseDTO(conversaSalva);
    }

    public List<ConversaResponseDTO> getMinhasConversas(Usuario usuarioLogado) {
        return conversaRepository.findAllByIntegrantes_Id(usuarioLogado.getId()).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }



    public Optional<Conversa> atualizarConversa(Conversa conversaAtualizada, Long id){
        return conversaRepository.findById(id).map(conversa -> {
            conversa.setDescricao(conversaAtualizada.getDescricao());
            conversa.setFoto(conversaAtualizada.getFoto());
            conversa.setIntegrantes(conversaAtualizada.getIntegrantes());

            return conversaRepository.save(conversa);
        });
    }


    public boolean excluirConversa(Long id){
        if(conversaRepository.existsById(id)){
            conversaRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
