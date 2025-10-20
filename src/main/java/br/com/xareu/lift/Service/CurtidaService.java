package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Curtida.CurtidaRequestDTO;
import br.com.xareu.lift.DTO.Curtida.CurtidaResponseDTO;
import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Entity.Postagem;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.CurtidaRepository;
import br.com.xareu.lift.Repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurtidaService {

    private final CurtidaRepository curtidaRepository;
    private final PostagemRepository postagemRepository;

    @Autowired
    public CurtidaService(CurtidaRepository curtidaRepository, PostagemRepository postagemRepository) {
        this.curtidaRepository = curtidaRepository;
        this.postagemRepository = postagemRepository;
    }
/*--------------------------------------------------------------------------------------------------------------------*/
/*parte de DTOs*/
    private CurtidaResponseDTO toResponseDTO(Curtida curtida) {
        return new CurtidaResponseDTO(
                curtida.getId(),
                curtida.getAutor().getId(),
                curtida.getAutor().getNomeUsuario(),
                curtida.getPostagem().getId()
        );
    }



/*--------------------------------------------------------------------------------------------------------------------*/

    @Transactional
    public CurtidaResponseDTO criarCurtida(CurtidaRequestDTO dto, Usuario autorLogado) {
        // Busca a postagem que será curtida
        Postagem postagem = postagemRepository.findById(dto.postagemId())
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        // *** REGRA DE NEGÓCIO: Verifica se o usuário já curtiu este post ***
        if (curtidaRepository.findByPostagemAndAutor(postagem, autorLogado).isPresent()) {
            throw new IllegalStateException("Usuário já curtiu esta postagem.");
        }

        Curtida novaCurtida = new Curtida();
        novaCurtida.setPostagem(postagem);
        novaCurtida.setAutor(autorLogado); // O autor é o usuário do token!

        Curtida curtidaSalva = curtidaRepository.save(novaCurtida);
        return toResponseDTO(curtidaSalva);
    }


    /*Curtida não pode ser editada*/

    @Transactional
    public void excluirCurtida(Long postagemId, Usuario autorLogado) {
        // Busca a postagem para garantir que ela existe
        Postagem postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        // Busca a curtida específica deste usuário nesta postagem
        Curtida curtida = curtidaRepository.findByPostagemAndAutor(postagem, autorLogado)
                .orElseThrow(() -> new RuntimeException("Curtida não encontrada para este usuário e postagem."));

        // Deleta a curtida encontrada. A verificação de permissão é implícita,
        // pois só encontramos uma curtida se ela pertencer ao autorLogado.
        curtidaRepository.delete(curtida);
    }

    public List<CurtidaResponseDTO> getCurtidasPorPostagem(Long postagemId) {
        if (!postagemRepository.existsById(postagemId)) {
            throw new RuntimeException("Postagem não encontrada");
        }
        return curtidaRepository.findByPostagemId(postagemId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
