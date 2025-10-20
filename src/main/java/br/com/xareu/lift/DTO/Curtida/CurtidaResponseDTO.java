package br.com.xareu.lift.DTO.Curtida;

public record CurtidaResponseDTO(
        Long id,                // ID da própria curtida
        Long usuarioId,
        String nomeUsuarioAutor, // Útil para o front-end exibir quem curtiu
        Long postagemId
) {}
