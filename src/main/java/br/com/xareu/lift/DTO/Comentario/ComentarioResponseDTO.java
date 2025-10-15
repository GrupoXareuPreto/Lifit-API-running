// src/main/java/br/com/xareu/lift/DTO/Comentario/ComentarioResponseDTO.java
package br.com.xareu.lift.DTO.Comentario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioResponseDTO {
    private Long id;
    private String conteudo;
    private LocalDateTime dataCriacao;
    private Long autorId;
    private String autorNomeUsuario;
    private Long postagemId;
}