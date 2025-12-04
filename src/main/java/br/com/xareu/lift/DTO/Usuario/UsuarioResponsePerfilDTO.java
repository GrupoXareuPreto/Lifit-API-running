package br.com.xareu.lift.DTO.Usuario;

import br.com.xareu.lift.DTO.Evento.EventoResponsePerfilDTO;
import br.com.xareu.lift.DTO.Meta.MetaResponsePerfilDTO;
import br.com.xareu.lift.DTO.Postagem.PostagemResponsePerfilDTO;

import java.util.List;


public record UsuarioResponsePerfilDTO(
        Long id,
        String nome,
        String fotoPerfil,
        String biografia,
        String email,
        String nomeUsuario,
        List<MetaResponsePerfilDTO> metas,
        List<EventoResponsePerfilDTO> eventos,
        List<PostagemResponsePerfilDTO> postagens
){}
