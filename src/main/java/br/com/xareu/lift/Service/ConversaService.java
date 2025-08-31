package br.com.xareu.lift.Service;

import br.com.xareu.lift.Repository.ConversaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConversaService {
    private ConversaRepository conversaRepository;

    public ConversaService(ConversaRepository conversaRepository){
        this.conversaRepository = conversaRepository;
    }

    public Object excluirConversa(Long id){
        if(conversaRepository.existsById(id)){
            conversaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound();
        }
    }
}
