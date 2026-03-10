package br.com.alura.forumhub.infra.security;

import br.com.alura.forumhub.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> autenticar(@RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getSenha()
                    )
            );

            String token = jwtService.gerarToken(authentication.getName());
            long expiraEm = System.currentTimeMillis() + jwtService.getExpiracao();

            JwtResponseDTO response = new JwtResponseDTO(token, "Bearer", expiraEm);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                    .body(new JwtResponseDTO(null, "Usuário ou senha inválidos", 0));
        }
    }
}