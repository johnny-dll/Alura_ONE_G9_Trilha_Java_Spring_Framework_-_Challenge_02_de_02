package br.com.alura.forumhub.infra.security;

public record JwtResponseDTO(
        String token,
        String tipo,
        long expiraEm
) {}