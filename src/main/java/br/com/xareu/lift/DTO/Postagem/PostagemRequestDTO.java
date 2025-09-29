package br.com.xareu.lift.DTO.Postagem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemRequestDTO {

    //@NotBlank(message = "A publicacao precisa de um autor")
    private Long idAutor;

    @NotBlank(message = "A publicacao precisa de uma midia")
    private String midia;

    @NotBlank(message = "A publicacao deve ter obrigatoriamente um titulo")
    private String titulo;

    @NotBlank(message = "A publicacao deve ter obrigatoriamente uma descricao")
    private String descricao;


    private LocalDateTime dataPublicacao;


}
