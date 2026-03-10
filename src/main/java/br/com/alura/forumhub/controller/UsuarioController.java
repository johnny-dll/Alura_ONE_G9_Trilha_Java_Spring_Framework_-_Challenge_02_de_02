package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.dto.UsuarioCadastroDTO;
import br.com.alura.forumhub.dto.UsuarioResponseDTO;
import br.com.alura.forumhub.dto.ErroDTO;
import br.com.alura.forumhub.entity.Usuario;
import br.com.alura.forumhub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // =============================
    // CRIAR USUÁRIO
    // =============================
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody @Valid UsuarioCadastroDTO dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErroDTO("Email já cadastrado."));
        }

        if (usuarioRepository.findByNome(dto.getNome()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErroDTO("Nome já cadastrado."));
        }

        Usuario usuario = dto.toUsuario();
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    // =============================
    // BUSCAR USUÁRIO POR ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponseDTO::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =============================
    // LISTAR TODOS OS USUÁRIOS
    // =============================
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // =============================
    // BUSCAR POR EMAIL OU NOME
    // =============================
    @GetMapping("/search")
    public ResponseEntity<?> buscarUsuario(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome) {

        if (email != null) {
            return usuarioRepository.findByEmail(email)
                    .map(UsuarioResponseDTO::new)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        if (nome != null) {
            return usuarioRepository.findByNome(nome)
                    .map(UsuarioResponseDTO::new)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.badRequest().body(new ErroDTO("Informe email ou nome para a pesquisa."));
    }
}