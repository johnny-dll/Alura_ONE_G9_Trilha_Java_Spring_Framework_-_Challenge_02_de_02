package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.dto.DadosCadastroTopico;
import br.com.alura.forumhub.dto.TopicoResponseDTO;
import br.com.alura.forumhub.entity.Topico;
import br.com.alura.forumhub.repository.TopicoRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    // =============================
    // CADASTRAR TÓPICO
    // =============================

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder) {

        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.badRequest().build();
        }

        Topico topico = new Topico(
                dados.titulo(),
                dados.mensagem(),
                dados.autor(),
                dados.curso(),
                LocalDateTime.now(),
                "NAO_RESPONDIDO"
        );

        repository.save(topico);

        URI uri = uriBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new TopicoResponseDTO(topico));
    }


    // =============================
    // LISTAR TÓPICOS
    // =============================

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(

            @PageableDefault(
                    size = 10,
                    sort = "dataCriacao",
                    direction = Sort.Direction.ASC
            ) Pageable pageable

    ) {

        Page<TopicoResponseDTO> page = repository
                .findAll(pageable)
                .map(TopicoResponseDTO::new);

        return ResponseEntity.ok(page);
    }


    // =============================
    // DETALHAR TÓPICO
    // =============================

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> detalhar(@PathVariable Long id) {

        Optional<Topico> topico = repository.findById(id);

        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new TopicoResponseDTO(topico.get()));
    }

}