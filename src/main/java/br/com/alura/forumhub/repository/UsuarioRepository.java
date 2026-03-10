package br.com.alura.forumhub.repository;

import br.com.alura.forumhub.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para a entidade Usuario.
 * Fornece métodos CRUD e buscas customizadas por nome ou email.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo nome.
     *
     * @param nome Nome do usuário
     * @return Optional contendo o usuário se encontrado, ou vazio caso contrário
     */
    Optional<Usuario> findByNome(String nome);

    /**
     * Busca um usuário pelo email.
     *
     * @param email Email do usuário
     * @return Optional contendo o usuário se encontrado, ou vazio caso contrário
     */
    Optional<Usuario> findByEmail(String email);
}