// src/main/java/br/com/xareu/lift/DTO/Curtida/CurtidaRequestDTO.java
package br.com.xareu.lift.DTO.Curtida;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CurtidaRequestDTO {

    @NotNull(message = "O ID da postagem é obrigatório")
    private Long postagemId;
}