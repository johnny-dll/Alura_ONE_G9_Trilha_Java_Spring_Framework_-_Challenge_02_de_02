package br.com.alura.forumhub.repository;

import br.com.alura.forumhub.entity.TopicoEdicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositório para a entidade TopicoEdicao.
 * Permite CRUD completo e consultas customizadas.
 */
public interface TopicoEdicaoRepository extends JpaRepository<TopicoEdicao, Long> {

    /**
     * Busca todas as edições de um tópico específico.
     *
     * @param topicoId ID do tópico
     * @return lista de edições
     */
    List<TopicoEdicao> findByTopicoId(@Param("topicoId") Long topicoId);

    /**
     * Busca todas as edições ativas de um tópico (softDelete = false)
     *
     * @param topicoId ID do tópico
     * @return lista de edições ativas
     */
    List<TopicoEdicao> findByTopicoIdAndSoftDeleteFalse(@Param("topicoId") Long topicoId);
}