package br.com.alura.forumhub.dto;

import br.com.alura.forumhub.entity.Usuario;

/**
 * DTO para retorno de dados de usuário via API.
 * Exclui informações sensíveis como senha.
 */
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;

    public UsuarioResponseDTO() {}

    /**
     * Construtor que cria um DTO a partir da entidade Usuario.
     */
    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}