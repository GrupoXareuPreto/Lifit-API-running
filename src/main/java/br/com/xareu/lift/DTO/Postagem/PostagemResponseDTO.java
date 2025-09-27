package br.com.xareu.lift.DTO.Postagem;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardPostagemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemResponseDTO {

    private UsuarioResponseCardPostagemDTO cardUsuario;
    private String midia;
    private String titulo;
    private String descricao;
    private LocalDateTime dataPublicacao;
    private int numCurtidas;
    private int numComentaios;
    private int numCompartilhamentos;
}
