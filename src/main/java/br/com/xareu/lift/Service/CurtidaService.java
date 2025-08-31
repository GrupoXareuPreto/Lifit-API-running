package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Curtida;
import br.com.xareu.lift.Repository.CurtidaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurtidaService {
    private CurtidaRepository curtidaRepository;

    public CurtidaService(CurtidaRepository curtidaRepository){
        this.curtidaRepository = curtidaRepository;
    }

    public Curtida criarcurtida(Curtida curtidanova){
        return curtidaRepository.save(curtidanova);
    }

    public List<Curtida> getAll(){
        return curtidaRepository.findAll();
    }

    /*Curtida não pode ser editada*/

    public Object excluirCurtida(Long id){
        if(curtidaRepository.existsById(id)){
            curtidaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound();
        }
    }
}
