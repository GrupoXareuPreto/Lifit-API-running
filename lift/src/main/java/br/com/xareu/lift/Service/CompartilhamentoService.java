package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Compartilhamento;
import br.com.xareu.lift.Repository.CompartilhamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompartilhamentoService {

    private CompartilhamentoRepository repository;

    public CompartilhamentoService(CompartilhamentoRepository compartilhamentoRepository){
        this.repository = compartilhamentoRepository;
    }

    public List<Compartilhamento> getAll(){
        return repository.findAll();
    }

    public Compartilhamento criarCompartilhamento(Compartilhamento compartilhamentoNovo){
        return repository.save(compartilhamentoNovo);
    }

}
