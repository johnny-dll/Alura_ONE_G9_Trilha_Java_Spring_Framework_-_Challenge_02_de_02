package br.com.alura.forumhub.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa o histórico de edições de um Tópico.
 * Armazena os dados antigos antes de uma atualização.
 */
@Entity
@Table(name = "topico_edicoes")
public class TopicoEdicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do tópico que foi editado.
     */
    @Column(name = "topico_id", nullable = false)
    private Long topicoId;

    @Column(name = "titulo_antigo", nullable = false)
    private String tituloAntigo;

    @Column(name = "mensagem_antiga", nullable = false, columnDefinition = "TEXT")
    private String mensagemAntiga;

    /**
     * Autor antigo do tópico.
     */
    @ManyToOne
    @JoinColumn(name = "autor_antigo_id", nullable = false)
    private Usuario autorAntigo;

    /**
     * Curso antigo do tópico (referência à entidade Curso)
     */
    @ManyToOne
    @JoinColumn(name = "curso_antigo_id", nullable = false)
    private Curso cursoAntigo;

    @Column(name = "data_edicao", nullable = false)
    private LocalDateTime dataEdicao;

    @Column(name = "soft_delete", nullable = false)
    private boolean softDelete = false; // Soft delete da edição

    // ==============================
    // Construtores
    // ==============================
    public TopicoEdicao() {}

    /**
     * Cria um histórico de edição a partir de um Tópico existente.
     * @param topico Tópico a ser registrado como histórico
     */
    public TopicoEdicao(Topico topico) {
        this.topicoId = topico.getId();
        this.tituloAntigo = topico.getTitulo();
        this.mensagemAntiga = topico.getMensagem();
        this.autorAntigo = topico.getAutor();
        this.cursoAntigo = topico.getCurso();
        this.dataEdicao = LocalDateTime.now();
        this.softDelete = false;
    }

    // ==============================
    // Getters
    // ==============================
    public Long getId() { return id; }
    public Long getTopicoId() { return topicoId; }
    public String getTituloAntigo() { return tituloAntigo; }
    public String getMensagemAntiga() { return mensagemAntiga; }
    public Usuario getAutorAntigo() { return autorAntigo; }
    public Curso getCursoAntigo() { return cursoAntigo; }
    public LocalDateTime getDataEdicao() { return dataEdicao; }
    public boolean isSoftDelete() { return softDelete; }

    // ==============================
    // Setters
    // ==============================
    public void setTopicoId(Long topicoId) { this.topicoId = topicoId; }
    public void setTituloAntigo(String tituloAntigo) { this.tituloAntigo = tituloAntigo; }
    public void setMensagemAntiga(String mensagemAntiga) { this.mensagemAntiga = mensagemAntiga; }
    public void setAutorAntigo(Usuario autorAntigo) { this.autorAntigo = autorAntigo; }
    public void setCursoAntigo(Curso cursoAntigo) { this.cursoAntigo = cursoAntigo; }
    public void setDataEdicao(LocalDateTime dataEdicao) { this.dataEdicao = dataEdicao; }
    public void setSoftDelete(boolean softDelete) { this.softDelete = softDelete; }

    // ==============================
    // equals, hashCode e toString
    // ==============================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicoEdicao)) return false;
        TopicoEdicao that = (TopicoEdicao) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "TopicoEdicao{" +
                "id=" + id +
                ", topicoId=" + topicoId +
                ", tituloAntigo='" + tituloAntigo + '\'' +
                ", dataEdicao=" + dataEdicao +
                ", softDelete=" + softDelete +
                '}';
    }

    // ==============================
    // Método utilitário para soft delete
    // ==============================
    public void marcarComoInativo() {
        this.softDelete = true;
    }
}