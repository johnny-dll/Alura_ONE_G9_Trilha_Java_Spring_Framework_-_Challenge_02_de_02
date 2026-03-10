package br.com.alura.forumhub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa um Tópico no fórum.
 */
@Entity
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String mensagem;

    /**
     * Relacionamento com o usuário que criou o tópico.
     * Muitos tópicos podem ter o mesmo autor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    /**
     * Relacionamento com o curso do tópico.
     * Muitos tópicos podem pertencer ao mesmo curso.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private String status; // futuramente pode virar enum

    @Column(nullable = false)
    private boolean ativo = true; // Soft delete

    // =============================
    // Construtores
    // =============================
    public Topico() {}

    public Topico(String titulo, String mensagem, Usuario autor, Curso curso,
                  LocalDateTime dataCriacao, String status) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.ativo = true;
    }

    // =============================
    // Getters
    // =============================
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public Usuario getAutor() { return autor; }
    public Curso getCurso() { return curso; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }
    public boolean isAtivo() { return ativo; }

    // =============================
    // Setters
    // =============================
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public void setAutor(Usuario autor) {
        if (autor == null) throw new IllegalArgumentException("Autor não pode ser nulo");
        this.autor = autor;
    }
    public void setCurso(Curso curso) {
        if (curso == null) throw new IllegalArgumentException("Curso não pode ser nulo");
        this.curso = curso;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public void setStatus(String status) { this.status = status; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    // =============================
    // Método utilitário para soft delete
    // =============================
    public void excluir() {
        this.ativo = false;
        this.status = "FECHADO"; // marca como fechado
    }
}