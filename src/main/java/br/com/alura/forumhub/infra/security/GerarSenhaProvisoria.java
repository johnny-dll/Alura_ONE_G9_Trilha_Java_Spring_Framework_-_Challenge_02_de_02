package br.com.alura.forumhub.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenhaProvisoria {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("MinhaSenha123"); // senha desejada
        System.out.println(hash);
    }
}