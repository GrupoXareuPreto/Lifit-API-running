package br.com.xareu.lift.Mapper;

import br.com.xareu.lift.DTO.Evento.EventoResponseFeedDTO;
import br.com.xareu.lift.DTO.Evento.EventoResponsePerfilDTO;
import br.com.xareu.lift.Entity.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface EventoMapper {
    EventoMapper INSTANCE = Mappers.getMapper(EventoMapper.class);

    //essa brincadeira faz "Usuario autor" virar "UsuarioResponseCardPostagemEventoDTO autor" junto do uses = { UsuarioMapper.class } ali em cima
    @Mapping(source = "autor", target = "autor")
    EventoResponseFeedDTO toResponseFeedDTO(Evento evento);

    List<EventoResponsePerfilDTO> toEventoResponsePerfilDTOList(List<Evento> eventos);

    List<EventoResponseFeedDTO> toResponseFeedDTOList(List<Evento> eventos);
}
