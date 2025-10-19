package br.com.xareu.lift.Mapper;


import br.com.xareu.lift.DTO.Postagem.PostagemResponseFeedDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponseImagemDTO;
import br.com.xareu.lift.Entity.Postagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface PostagemMapper {
    PostagemMapper INSTANCE = Mappers.getMapper(PostagemMapper.class);

    //essa brincadeira faz "Usuario autor" virar "UsuarioResponseCardPostagemEventoDTO autor" junto do uses = { UsuarioMapper.class } ali em cima
    @Mapping(source = "autor", target = "autor")
    PostagemResponseFeedDTO toResponseFeedDTO(Postagem postagem);

    List<PostagemResponseFeedDTO> toResponseFeedDTOList(List<Postagem> postagens);

    PostagemResponseImagemDTO toPostagemResponseImagemDTO(Postagem postagem);
}
