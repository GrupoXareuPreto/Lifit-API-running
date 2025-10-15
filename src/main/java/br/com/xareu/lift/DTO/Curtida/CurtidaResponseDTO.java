// src/main/java/br/com/xareu/lift/DTO/Curtida/CurtidaResponseDTO.java
package br.com.xareu.lift.DTO.Curtida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurtidaResponseDTO {
    private Long id; // ID da própria curtida
    private Long usuarioId;
    private String nomeUsuarioAutor; // Útil para o front-end exibir quem curtiu
    private Long postagemId;
}