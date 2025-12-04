package br.com.xareu.lift.DTO.Seguidor;

public record SeguidorContaDTO(
        long seguidores,
        long seguindo,
        boolean estaSeguindo
) {
}
