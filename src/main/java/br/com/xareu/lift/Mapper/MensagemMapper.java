package br.com.xareu.lift.Mapper;

import br.com.xareu.lift.DTO.Mensagem.MensagemResponseDTO;
import br.com.xareu.lift.Entity.Mensagem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class })
public interface MensagemMapper {

    MensagemResponseDTO toResponseDTO(Mensagem mensagem);
    List<MensagemResponseDTO> toResponseDTOList(List<Mensagem> mensagens);
}
