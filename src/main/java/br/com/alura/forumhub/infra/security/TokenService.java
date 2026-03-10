package br.com.alura.forumhub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final String secret;
    private final long expiration;

    public TokenService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secret = secret;
        this.expiration = expiration;
    }

    // Gera token JWT
    public String gerarToken(String subject) {
        Date agora = new Date();
        Date validade = new Date(agora.getTime() + expiration);

        return JWT.create()
                .withSubject(subject)      // normalmente email ou username
                .withIssuedAt(agora)
                .withExpiresAt(validade)
                .sign(Algorithm.HMAC256(secret));
    }

    // Valida token e retorna subject
    public String validarToken(String token) {
        try {
            DecodedJWT decodedJWT = getVerifier().verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new TokenValidationException("Token inválido ou expirado", e);
        }
    }

    // Cria e retorna o JWTVerifier
    private JWTVerifier getVerifier() {
        return JWT.require(Algorithm.HMAC256(secret)).build();
    }

    // Exceção customizada para token inválido
    public static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}