package br.com.alura.forumhub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String curso;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private String status;

    public Topico() {}

    public Topico(String titulo, String mensagem, String autor, String curso,
                  LocalDateTime dataCriacao, String status) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setCurso(String curso) { this.curso = curso; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public void setStatus(String status) { this.status = status; }
}