package br.com.alura.forumhub.infra.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record AutenticacaoDTO(
        @NotBlank String email,
        @NotBlank String senha
) {}