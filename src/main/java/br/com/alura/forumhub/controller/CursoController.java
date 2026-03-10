package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.dto.CursoCadastroDTO;
import br.com.alura.forumhub.dto.CursoResponseDTO;
import br.com.alura.forumhub.dto.ErroDTO;
import br.com.alura.forumhub.entity.Curso;
import br.com.alura.forumhub.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // =============================
    // LISTAR TODOS OS CURSOS
    // =============================
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listar() {
        List<CursoResponseDTO> cursos = cursoRepository.findAll()
                .stream()
                .map(CursoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cursos);
    }

    // =============================
    // CRIAR UM NOVO CURSO
    // =============================
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CursoCadastroDTO dto) {

        // Verifica duplicidade de nome
        if (cursoRepository.findByNome(dto.getNome()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new ErroDTO("Curso com este nome já existe."));
        }

        // Criar a entidade a partir do DTO
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        curso.setCategoria(dto.getCategoria());

        // Salvar no banco
        cursoRepository.save(curso);

        // Retornar response
        CursoResponseDTO response = new CursoResponseDTO(curso);
        return ResponseEntity.ok(response);
    }

    // =============================
    // BUSCAR UM CURSO POR ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .<ResponseEntity<?>>map(c -> ResponseEntity.ok(new CursoResponseDTO(c)))
                .orElseGet(() -> ResponseEntity.badRequest()
                        .body(new ErroDTO("Curso não encontrado com ID: " + id)));
    }
}