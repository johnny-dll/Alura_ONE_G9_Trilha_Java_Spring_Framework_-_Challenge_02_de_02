package br.com.alura.forumhub.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um Usuário do fórum.
 * Um usuário pode criar múltiplos tópicos.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();

    public Usuario() {}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // ================================
    // Getters e Setters
    // ================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<Topico> getTopicos() { return topicos; }
    public void setTopicos(List<Topico> topicos) { this.topicos = topicos; }

    // ================================
    // Métodos utilitários
    // ================================

    /**
     * Adiciona um tópico ao usuário e seta o autor do tópico.
     * @param topico tópico a ser adicionado
     */
    public void adicionarTopico(Topico topico) {
        topicos.add(topico);
        topico.setAutor(this);
    }

    /**
     * Remove um tópico do usuário e limpa o autor do tópico.
     * @param topico tópico a ser removido
     */
    public void removerTopico(Topico topico) {
        topicos.remove(topico);
        topico.setAutor(null);
    }

    // ================================
    // Overrides úteis (opcional)
    // ================================

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nome='" + nome + '\'' +
                ", email='" + email + '\'' + '}';
    }

}