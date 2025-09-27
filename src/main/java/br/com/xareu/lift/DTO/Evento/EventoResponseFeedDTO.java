package br.com.xareu.lift.DTO.Evento;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventoResponseFeedDTO
{
    private String midia;
    private UsuarioResponseCardPostagemDTO autor;
    private LocalDateTime dataInicio;
    private Long Compartilhamentos;
}

