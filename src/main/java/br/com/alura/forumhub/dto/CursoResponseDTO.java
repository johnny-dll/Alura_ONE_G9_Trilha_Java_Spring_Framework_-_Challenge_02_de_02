package br.com.alura.forumhub.dto;

import br.com.alura.forumhub.entity.Curso;

/**
 * DTO para retorno de cursos via API.
 * Expõe apenas os dados relevantes do curso.
 */
public class CursoResponseDTO {

    private Long id;
    private String nome;

    public CursoResponseDTO() {}

    /**
     * Construtor que cria o DTO a partir da entidade Curso.
     */
    public CursoResponseDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}