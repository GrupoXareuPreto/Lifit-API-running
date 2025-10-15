package br.com.xareu.lift.Mapper;


import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseImagemDTO;
import br.com.xareu.lift.Entity.Postagem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostagemMapper {
    PostagemMapper INSTANCE = Mappers.getMapper(PostagemMapper.class);

    PostagemResponseFeedDTO toResponseFeedDTO(Postagem postagem);

    List<PostagemResponseFeedDTO> toResponseFeedDTOList(List<Postagem> postagens);

    PostagemResponseImagemDTO toPostagemResponseImagemDTO(Postagem postagem);
}
