package br.com.xareu.lift.Mapper;

import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponsePerfilDTO;
import br.com.xareu.lift.Entity.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventoMapper {
    EventoMapper INSTANCE = Mappers.getMapper(EventoMapper.class);

    EventoResponseFeedDTO toResponseFeedDTO(Evento evento);

    EventoResponsePerfilDTO toEventoResponsePerfilDTO(Evento evento);

    List<EventoResponseFeedDTO> toResponseFeedDTOList(List<Evento> eventos);
}
