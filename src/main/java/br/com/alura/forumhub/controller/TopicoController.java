package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.dto.DadosCadastroTopico;
import br.com.alura.forumhub.entity.Topico;
import br.com.alura.forumhub.repository.TopicoRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroTopico dados,
                                       UriComponentsBuilder uriBuilder) {

        // Verifica duplicidade
        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity
                    .badRequest()
                    .body("Já existe um tópico cadastrado com este título e mensagem.");
        }

        // Cria o tópico
        Topico topico = new Topico(
                dados.titulo(),
                dados.mensagem(),
                dados.autor(),
                dados.curso(),
                LocalDateTime.now(),
                "NAO_RESPONDIDO"
        );

        repository.save(topico);

        // Cria URI do recurso recém-criado
        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(topico);
    }
}