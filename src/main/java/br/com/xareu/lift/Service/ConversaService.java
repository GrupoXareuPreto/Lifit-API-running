package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Conversa;
import br.com.xareu.lift.Repository.ConversaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversaService {
    private ConversaRepository repository;

    public ConversaService(ConversaRepository conversaRepository){
        this.repository = conversaRepository;
    }

    public Conversa criarconversa(Conversa conversaNova){
        return repository.save(conversaNova);
    }

    public List<Conversa> getAll(){
        return repository.findAll();
    }



    public Optional<Conversa> atualizarConversa(Conversa conversaAtualizada, Long id){
        return repository.findById(id).map(conversa -> {
            conversa.setDescricao(conversaAtualizada.getDescricao());
            conversa.setFoto(conversaAtualizada.getFoto());
            conversa.setIntegrantes(conversaAtualizada.getIntegrantes());

            return repository.save(conversa);
        });
    }


    public boolean excluirConversa(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
