package br.com.alura.forumhub.infra.security;

import br.com.alura.forumhub.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsConfig {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> usuarioRepository.findByEmail(email)
                .map(usuario -> User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getSenha()) // hash do banco
                        .roles("USER")
                        .build()
                )
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}