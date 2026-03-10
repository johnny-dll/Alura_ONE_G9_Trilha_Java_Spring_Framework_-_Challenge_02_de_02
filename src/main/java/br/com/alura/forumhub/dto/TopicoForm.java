package br.com.alura.forumhub.dto;

import br.com.alura.forumhub.entity.Topico;
import br.com.alura.forumhub.entity.Usuario;
import br.com.alura.forumhub.entity.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para criação de tópicos.
 * Contém os dados enviados pelo cliente para criar um novo tópico.
 */
public class TopicoForm {

    @NotBlank(message = "Título é obrigatório")
    private final String titulo;

    @NotBlank(message = "Mensagem é obrigatória")
    private final String mensagem;

    @NotNull(message = "ID do curso é obrigatório")
    private final Long cursoId;

    @NotNull(message = "ID do autor é obrigatório")
    private final Long autorId;

    public TopicoForm(String titulo, String mensagem, Long cursoId, Long autorId) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.cursoId = cursoId;
        this.autorId = autorId;
    }

    // =============================
    // Getters
    // =============================
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public Long getCursoId() { return cursoId; }
    public Long getAutorId() { return autorId; }

    /**
     * Converte o DTO em entidade Topico.
     * Recebe o Usuario e Curso já buscados no banco.
     */
    public Topico toTopico(Usuario autor, Curso curso) {
        Topico topico = new Topico();
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus("NAO_RESPONDIDO"); // substituir pelo enum se existir
        return topico;
    }
}