package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Meta;
import br.com.xareu.lift.Repository.MetaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private MetaRepository metaRepository;

    public MetaService(MetaRepository metaRepository){
        this.metaRepository = metaRepository;
    }

    public Meta criarMeta(Meta metaNova){
        return metaRepository.save(metaNova);
    }

    public List<Meta> getAll(){
        return metaRepository.findAll();
    }

    public Optional<Meta> atualizarMeta(Meta metaAtualizada, Long id){
        return metaRepository.findById(id).map(metaBanco -> {
            metaBanco.setNome(metaAtualizada.getNome());
            metaBanco.setStatus(metaAtualizada.getStatus());
            metaBanco.setDescricao(metaAtualizada.getDescricao());

            return metaRepository.save(metaBanco);
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
