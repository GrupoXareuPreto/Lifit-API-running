package br.com.xareu.lift.DTO.Evento;

import br.com.xareu.lift.DTO.Comentario.ComentarioResponseDTO;
import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemEventoDTO;
import br.com.xareu.lift.Entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventoResponseFeedDTO
{
    private UsuarioResponseCardPostagemEventoDTO autor;
    private String midia;
    private LocalDateTime dataInicio;
    private int numCurtidas;
    private int numComentarios;
    private int numCompartilhamentos;
}

