package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.dto.DadosCadastroTopico;
import br.com.alura.forumhub.entity.Topico;
import br.com.alura.forumhub.repository.TopicoRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {

        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity
                    .badRequest()
                    .body("Já existe um tópico cadastrado com este título e mensagem.");
        }

        Topico topico = new Topico();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus("NAO_RESPONDIDO");

        repository.save(topico);

        return ResponseEntity.ok(topico); // você pode retornar o tópico salvo ou um DTO
    }
}