package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.Meta.MetaRequestCriarDTO;
import br.com.xareu.lift.DTO.Meta.MetaResponsePerfilDTO;
import br.com.xareu.lift.Entity.Meta;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Mapper.MetaMapper;
import br.com.xareu.lift.Repository.MetaRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private MetaMapper metaMapper;


    @Transactional
    public MetaResponsePerfilDTO criarMeta(MetaRequestCriarDTO metaDTO, Usuario autor) {
        Meta meta = new Meta();
        meta.setNome(metaDTO.getNome());
        meta.setPublica(metaDTO.isPublica());
        meta.setDataFim(metaDTO.getDataFim());
        meta.setAutor(autor);

        Meta savedMeta = metaRepository.save(meta);
        return metaMapper.toResponsePerfilDTO(savedMeta);
    }

    public List<MetaResponsePerfilDTO> getAll()
    {
        return metaMapper.toResponsePerfilDTOList(metaRepository.findAll());
    }

    @Transactional
    public Optional<MetaResponsePerfilDTO> atualizarMeta(Long metaId, MetaRequestCriarDTO metaDTO, Usuario usuarioLogado) throws IllegalAccessException {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

        // *** A VERIFICAÇÃO DE AUTORIZAÇÃO CRUCIAL ***
        if (!meta.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para editar esta meta.");
        }

        // Atualiza os campos da meta existente
        meta.setNome(metaDTO.getNome());
        meta.setPublica(metaDTO.isPublica());
        meta.setStatus(metaDTO.getStatus());
        meta.setDataFim(metaDTO.getDataFim());
        // A data de início geralmente não é alterada, mas se for, adicione: meta.setDataInicio(metaDTO.getDataInicio());

        Meta metaAtualizada = metaRepository.save(meta);
        return Optional.of(metaMapper.toResponsePerfilDTO(metaAtualizada));
    }

    @Transactional
    public void deletarMeta(Long metaId, Usuario usuarioLogado) throws IllegalAccessException {
        Meta meta = metaRepository.findById(metaId)
                .orElseThrow(() -> new RuntimeException("Meta não encontrada"));

        // *** A VERIFICAÇÃO DE AUTORIZAÇÃO CRUCIAL ***
        if (!meta.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new IllegalAccessException("Você não tem permissão para deletar esta meta.");
        }

        metaRepository.delete(meta);
    }

    public List<MetaResponsePerfilDTO> getMetasPorAutor(Usuario autor) {
        return metaMapper.toResponsePerfilDTOList(metaRepository.findByAutor(autor));
    }

    public List<MetaResponsePerfilDTO> getAllPublicas() {
        List<Meta> metasPublicas = metaRepository.findByPublicaTrue();

        return metaMapper.toResponsePerfilDTOList(metasPublicas);
    }

}
