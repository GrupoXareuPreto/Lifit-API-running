package br.com.xareu.lift.DTO.Postagem;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemEventoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemResponseFeedDTO {

    private UsuarioResponseCardPostagemEventoDTO cardUsuario;
    private String midia;
    private String titulo;
    private LocalDateTime dataPublicacao;
    private int numCurtidas;
    private int numComentaios;
    private int numCompartilhamentos;
}
