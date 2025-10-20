package br.com.xareu.lift.Mapper;

import br.com.xareu.lift.DTO.Meta.MetaResponsePerfilDTO;
import br.com.xareu.lift.Entity.Meta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MetaMapper {
    MetaMapper INSTANCE = Mappers.getMapper(MetaMapper.class);

    MetaResponsePerfilDTO toResponsePerfilDTO(Meta meta);

    List<MetaResponsePerfilDTO> toResponsePerfilDTOList(List<Meta> meta);
}
