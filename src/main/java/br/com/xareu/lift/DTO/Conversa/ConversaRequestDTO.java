package br.com.xareu.lift.DTO.Conversa;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ConversaRequestDTO {

    @NotEmpty(message = "A lista de integrantes não pode ser vazia.")
    private List<Long> integranteIds;


    private String titulo;
    private String foto;
    private String descricao;
}