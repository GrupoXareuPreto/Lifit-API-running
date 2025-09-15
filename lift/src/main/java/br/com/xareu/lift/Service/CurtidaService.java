package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Repository.CurtidaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurtidaService {

    private CurtidaRepository repository;

    public CurtidaService(CurtidaRepository curtidaRepository){
        this.repository = curtidaRepository;
    }

    public Curtida criarcurtida(Curtida curtidanova){
        return repository.save(curtidanova);
    }

    public List<Curtida> getAll(){
        return repository.findAll();
    }

    /*Curtida não pode ser editada*/

    public boolean excluirCurtida(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
