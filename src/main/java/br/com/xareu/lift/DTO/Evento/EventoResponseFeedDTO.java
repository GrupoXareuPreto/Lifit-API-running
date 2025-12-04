package br.com.xareu.lift.DTO.Evento;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemEventoDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseSimples;

import java.time.LocalDateTime;
import java.util.List;

public record EventoResponseFeedDTO (
         Long id,
         String titulo,
         String descricao,
         String localizacao,
         LocalDateTime dataInicio,
         LocalDateTime dataFim,
         UsuarioResponseCardPostagemEventoDTO autor,
         String midia,
         int numCurtidas,
         int numComentarios,
         int numCompartilhamentos,
         int numParticipantes,
         List<UsuarioResponseSimples> participantes,
         boolean usuarioConfirmado,
         boolean usuarioCurtiu
)
{}

