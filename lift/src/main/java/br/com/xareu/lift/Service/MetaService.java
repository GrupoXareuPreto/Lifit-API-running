package br.com.xareu.lift.Service;

import br.com.xareu.lift.DTO.MetaRequestDTO;
import br.com.xareu.lift.DTO.MetaResponseDTO;
import br.com.xareu.lift.Entity.Meta;
import br.com.xareu.lift.Entity.Usuario;
import br.com.xareu.lift.Repository.MetaRepository;
import br.com.xareu.lift.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    public MetaService(MetaRepository metaRepository, UsuarioRepository usuarioRepository){
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private MetaResponseDTO toResponseDTO(Meta meta){
        if(meta == null){
            return null;
        }
        else {
            return new MetaResponseDTO(
                    meta.getNome(),
                    meta.isPublica(),
                    meta.getStatus(),
                    meta.getDataFim()
            );
        }
    }

    private Meta toEntity(MetaRequestDTO dto){
        Meta meta = new Meta();
        meta.setNome(dto.getNome());
        meta.setPublica(dto.isPublica());
        meta.setStatus(dto.getStatus());
        meta.setDataInicio(dto.getDataInicio());
        meta.setDataFim(dto.getDataFim());

        return meta;
    }

    public MetaResponseDTO criarMeta(MetaRequestDTO metaDTO, Long autorId){
        Usuario autor = usuarioRepository.findById(autorId).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"  + autorId));

        Meta meta = new Meta();
        meta.setNome(metaDTO.getNome());
        meta.setPublica(metaDTO.isPublica());
        meta.setAutor(autor);

        Meta savedMeta = metaRepository.save(meta);
        return toResponseDTO(savedMeta);
    }

    public List<MetaResponseDTO> getAll()
    {
        return metaRepository.findAll().stream().map(this ::toResponseDTO).collect(Collectors.toList());
    }

    public Optional<MetaResponseDTO> atualizarMeta(MetaRequestDTO metaDTO, Long id){
        return metaRepository.findById(id).map(meta -> {
           meta.setNome(metaDTO.getNome());
           meta.setPublica(metaDTO.isPublica());
           meta.setStatus(metaDTO.getStatus());
           meta.setDataInicio(metaDTO.getDataInicio());
           meta.setDataFim(metaDTO.getDataFim());

           Meta metaAtualizada = metaRepository.save(meta);
           return toResponseDTO(metaAtualizada);
        });
    }

    public boolean deletarMeta(Long id){
        if(metaRepository.existsById(id)){
            metaRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
