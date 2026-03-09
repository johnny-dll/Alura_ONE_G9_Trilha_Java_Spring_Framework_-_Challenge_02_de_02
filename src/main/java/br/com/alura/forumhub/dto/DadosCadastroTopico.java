package br.com.alura.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record DadosCadastroTopico(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotBlank String autor,
        @NotBlank String curso
) implements Serializable {}