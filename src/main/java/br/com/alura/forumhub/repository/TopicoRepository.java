package br.com.alura.forumhub.repository;

import br.com.alura.forumhub.entity.Topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verifica se já existe um tópico com mesmo título e mensagem
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    // Busca tópicos por curso e ano da data de criação
    @Query("""
            SELECT t
            FROM Topico t
            WHERE t.curso = :curso
            AND YEAR(t.dataCriacao) = :ano
            """)
    Page<Topico> findByCursoAndDataCriacaoYear(
            @Param("curso") String curso,
            @Param("ano") int ano,
            Pageable pageable
    );

}