package br.com.xareu.lift.DTO.Postagem;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemEventoDTO;
import java.time.LocalDateTime;

public record PostagemResponseFeedDTO(
        Long id,
        UsuarioResponseCardPostagemEventoDTO autor,
        String midia,
        String titulo,
        LocalDateTime dataPublicacao,
        int numCurtidas,
        int numComentaios,
        int numCompartilhamentos,
        String descricao,
        boolean usuarioCurtiu
) {}
