package br.com.alura.forumhub.repository;

import br.com.alura.forumhub.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Permite buscar curso pelo nome, útil para validar duplicidade
    Optional<Curso> findByNome(String nome);
}