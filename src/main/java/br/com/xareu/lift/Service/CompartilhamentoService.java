package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Compartilhamento;
import br.com.xareu.lift.Repository.ComentarioRepository;
import br.com.xareu.lift.Repository.CompartilhamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompartilhamentoService {
    private CompartilhamentoRepository compartilhamentoRepository;

    public CompartilhamentoService(CompartilhamentoRepository compartilhamentoRepository){
        this.compartilhamentoRepository = compartilhamentoRepository;
    }


}
