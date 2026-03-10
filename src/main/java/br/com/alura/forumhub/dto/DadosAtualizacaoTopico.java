package br.com.alura.forumhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para atualização de tópicos existentes.
 * Contém os campos que podem ser alterados de um tópico.
 * Os campos autorId e cursoId devem referenciar entidades existentes no banco.
 */
public record DadosAtualizacaoTopico(

        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotBlank(message = "Mensagem é obrigatória")
        String mensagem,

        @NotNull(message = "ID do autor é obrigatório")
        Long autorId,

        @NotNull(message = "ID do curso é obrigatório")
        Long cursoId

) {

        /**
         * Atualiza a entidade Topico com os dados deste DTO.
         * Recebe as entidades já buscadas no banco.
         */
        public void atualizarTopico(br.com.alura.forumhub.entity.Topico topico,
                                    br.com.alura.forumhub.entity.Usuario autor,
                                    br.com.alura.forumhub.entity.Curso curso) {
                topico.setTitulo(this.titulo);
                topico.setMensagem(this.mensagem);
                topico.setAutor(autor);
                topico.setCurso(curso);
        }
}