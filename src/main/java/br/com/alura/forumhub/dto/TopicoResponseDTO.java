package br.com.alura.forumhub.dto;

import br.com.alura.forumhub.entity.Topico;

import java.time.LocalDateTime;

/**
 * DTO para retorno de tópicos via API.
 * Converte a entidade Topico em um objeto de resposta simplificado,
 * expondo apenas informações relevantes ao cliente.
 */
public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String status,
        String autor,  // nome do usuário
        String curso   // nome do curso
) {

    /**
     * Construtor que converte uma entidade Topico em DTO.
     * Garante que autor e curso não sejam nulos.
     */
    public TopicoResponseDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getNome() : null,
                topico.getCurso() != null ? topico.getCurso().getNome() : null
        );
    }
}