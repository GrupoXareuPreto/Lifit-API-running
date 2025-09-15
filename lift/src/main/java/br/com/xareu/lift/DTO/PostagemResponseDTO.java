package br.com.xareu.lift.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemResponseDTO {

    private UsuarioResumoDTO cardUsuario;
    private String midia;
    private String titulo;
    private String descricao;
    private LocalDateTime dataPublicacao;
    private int numCurtidas;
    private int numComentaios;
    private int numCompartilhamentos;
}
