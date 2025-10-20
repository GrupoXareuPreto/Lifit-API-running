package br.com.xareu.lift.DTO.Evento;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemEventoDTO;

import java.time.LocalDateTime;



public record EventoResponseFeedDTO (
         UsuarioResponseCardPostagemEventoDTO autor,
         String midia,
         LocalDateTime dataInicio,
         int numCurtidas,
         int numComentarios,
         int numCompartilhamentos
)
{}

