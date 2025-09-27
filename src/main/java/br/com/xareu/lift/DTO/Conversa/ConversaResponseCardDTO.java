package br.com.xareu.lift.DTO.Conversa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversaResponseCardDTO {

    private String fotoUsuario;
    private String nome;
    private String nomeUsuario;

}
