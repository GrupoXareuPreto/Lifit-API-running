package br.com.xareu.lift.DTO.Meta;

import br.com.xareu.lift.Enum.StatusMetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public record MetaResponsePerfilDTO(
         Long id,
         String nome,
         boolean publica,
         StatusMetaEnum status,
         LocalDate dataFim
) { }
