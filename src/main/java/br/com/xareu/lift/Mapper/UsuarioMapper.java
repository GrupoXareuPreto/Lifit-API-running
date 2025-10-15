package br.com.xareu.lift.Mapper;

import br.com.xareu.lift.DTO.Usuario.*;
import br.com.xareu.lift.Entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioResponsePerfilDTO toResponsePerfilDTO(Usuario usuario);

    UsuarioResponseCardPostagemEventoDTO toUsuarioCardPostagemEventoDTO(Usuario usuario);

    UsuarioResponseCardConversaDTO toUsuarioCardConversaDTO(Usuario usuario);

    UsuarioResponseComentarioDTO toUsuarioResponseComentarioDTO(Usuario usuario);

    Usuario toEntity(UsuarioRequestDTO dto);

    List<UsuarioResponsePerfilDTO> toPerfilResponseList(List<Usuario> usuarios);

}
