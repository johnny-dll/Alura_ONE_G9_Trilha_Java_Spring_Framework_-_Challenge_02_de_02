package br.com.alura.forumhub.entity;

import jakarta.persistence.*;

/**
 * Entidade que representa um Curso no fórum.
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String categoria;  // Campo obrigatório

    // ==============================
    // Construtores
    // ==============================
    public Curso() {}

    /**
     * Construtor completo, obrigatório fornecer categoria.
     */
    public Curso(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    // ==============================
    // Getters e Setters
    // ==============================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // ==============================
    // equals, hashCode e toString
    // ==============================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso)) return false;
        Curso curso = (Curso) o;
        return id != null && id.equals(curso.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Curso{id=" + id + ", nome='" + nome + "', categoria='" + categoria + "'}";
    }
}