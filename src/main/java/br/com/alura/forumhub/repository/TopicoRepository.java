package br.com.alura.forumhub.repository;

import br.com.alura.forumhub.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repositório para a entidade Topico.
 * Fornece métodos para CRUD e consultas específicas.
 */
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    /**
     * Verifica se já existe um tópico com mesmo título e mensagem.
     *
     * @param titulo  título do tópico
     * @param mensagem mensagem do tópico
     * @return true se existir, false caso contrário
     */
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    /**
     * Busca tópicos por curso e ano da data de criação.
     *
     * @param curso  nome do curso
     * @param ano    ano da criação
     * @param pageable paginação
     * @return página de tópicos filtrados
     */
    @Query("""
            SELECT t
            FROM Topico t
            WHERE t.curso = :curso
              AND FUNCTION('YEAR', t.dataCriacao) = :ano
            """)
    Page<Topico> findByCursoAndDataCriacaoYear(
            @Param("curso") String curso,
            @Param("ano") int ano,
            Pageable pageable
    );

    // ================================
    // MÉTODOS PARA SOFT DELETE
    // ================================

    /**
     * Lista todos os tópicos que estão ativos.
     */
    Page<Topico> findAllByAtivoTrue(Pageable pageable);

    /**
     * Busca um tópico ativo pelo id.
     */
    Optional<Topico> findByIdAndAtivoTrue(Long id);
}