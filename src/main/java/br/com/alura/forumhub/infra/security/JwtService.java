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
public class JwtService {

    private final String segredo;
    private final long expiracao;

    public JwtService(
            @Value("${jwt.secret}") String segredo,
            @Value("${jwt.expiration}") long expiracao
    ) {
        this.segredo = segredo;
        this.expiracao = expiracao;
    }

    /**
     * Gera um token JWT para o subject informado (email, username etc.)
     */
    public String gerarToken(String subject) {
        Date agora = new Date();
        Date validade = new Date(agora.getTime() + expiracao);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(agora)
                .withExpiresAt(validade)
                .sign(getAlgoritmo());
    }

    /**
     * Valida um token JWT e retorna o subject (email, username etc.)
     */
    public String validarToken(String token) {
        try {
            DecodedJWT decodedJWT = getVerifier().verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new JwtValidationException("Token inválido ou expirado", e);
        }
    }

    /**
     * Retorna o tempo de expiração configurado (ms)
     */
    public long getExpiracao() {
        return expiracao;
    }

    // Cria o algoritmo HMAC256 com o segredo
    private Algorithm getAlgoritmo() {
        return Algorithm.HMAC256(segredo);
    }

    // Cria e retorna o JWTVerifier
    private JWTVerifier getVerifier() {
        return JWT.require(getAlgoritmo()).build();
    }

    // Exceção customizada para tokens inválidos
    public static class JwtValidationException extends RuntimeException {
        public JwtValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}