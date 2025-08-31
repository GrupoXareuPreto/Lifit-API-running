package br.com.xareu.lift.Service;

import br.com.xareu.lift.Entity.Evento;
import br.com.xareu.lift.Entity.Mensagem;
import br.com.xareu.lift.Repository.EventoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {
    private EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository){this.eventoRepository = eventoRepository;}

    public List<Evento> getall(Long id){
        return eventoRepository.findAll();
    }

    public Object  deletarEvento(Long id){
        if (eventoRepository.existsById(id)){
            eventoRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound();
    }
}
