// src/main/java/br/com/xareu/lift/DTO/Conversa/ConversaResponseDTO.java
package br.com.xareu.lift.DTO.Conversa;

import br.com.xareu.lift.DTO.Usuario.UsuarioResponseCardConversaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversaResponseDTO {
    private Long id;
    private String titulo;
    private String foto;
    private String descricao;
    private List<UsuarioResponseCardConversaDTO> integrantes;
}