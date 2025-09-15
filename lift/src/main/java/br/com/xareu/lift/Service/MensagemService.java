package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Mensagem;
import br.com.xareu.lift.Repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensagemService {
    private MensagemRepository repository;

    public MensagemService(MensagemRepository mensagemRepository){this.repository = mensagemRepository;}

    public Mensagem criarMensagem(Mensagem mensagemNova){
        return repository.save(mensagemNova);
    }

    public List<Mensagem> getAll(){
        return repository.findAll();
    }

    public boolean  deletarMensagem(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
