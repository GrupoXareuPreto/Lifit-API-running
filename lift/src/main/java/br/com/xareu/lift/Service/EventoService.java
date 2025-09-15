package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {
    private EventoRepository repository;

    public EventoService(EventoRepository eventoRepository){this.repository = eventoRepository;}

    public Evento criarEvento(Evento eventoNovo){
        return repository.save(eventoNovo);
    }

    public List<Evento> getAll(){
        return repository.findAll();
    }

    public boolean  deletarEvento(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Evento> atualizarEvento(Evento atualizacao, Long id){
        return repository.findById(id).map(eventoAtualizado -> {
            eventoAtualizado.setAtividade(atualizacao.getAtividade());
            eventoAtualizado.setDescricao(atualizacao.getDescricao());
            eventoAtualizado.setLocalizacao(atualizacao.getLocalizacao());
            eventoAtualizado.setDataInicio(atualizacao.getDataInicio());
            eventoAtualizado.setDataFim(atualizacao.getDataFim());
            eventoAtualizado.setTitulo(atualizacao.getTitulo());

            return repository.save(eventoAtualizado);
        });
    }
}
