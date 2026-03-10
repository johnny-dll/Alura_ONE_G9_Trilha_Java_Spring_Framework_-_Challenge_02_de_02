package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.dto.TopicoForm;
import br.com.alura.forumhub.dto.TopicoResponseDTO;
import br.com.alura.forumhub.dto.DadosAtualizacaoTopico;
import br.com.alura.forumhub.dto.ErroDTO;
import br.com.alura.forumhub.entity.Topico;
import br.com.alura.forumhub.entity.TopicoEdicao;
import br.com.alura.forumhub.entity.Usuario;
import br.com.alura.forumhub.entity.Curso;
import br.com.alura.forumhub.repository.TopicoRepository;
import br.com.alura.forumhub.repository.TopicoEdicaoRepository;
import br.com.alura.forumhub.repository.UsuarioRepository;
import br.com.alura.forumhub.repository.CursoRepository;

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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoEdicaoRepository edicaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // =============================
    // CADASTRAR TÓPICO
    // =============================
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid TopicoForm form,
                                       UriComponentsBuilder uriBuilder) {

        if (topicoRepository.existsByTituloAndMensagem(form.getTitulo(), form.getMensagem())) {
            return ResponseEntity.badRequest().body(new ErroDTO("Tópico duplicado"));
        }

        AutorCursoRef ref = getAutorCurso(form.getAutorId(), form.getCursoId());
        if (ref == null) {
            return ResponseEntity.badRequest().body(new ErroDTO("Autor ou curso inválido."));
        }

        Topico topico = form.toTopico(ref.autor(), ref.curso());
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponseDTO(topico));
    }

    // =============================
    // LISTAR TÓPICOS (somente ativos)
    // =============================
    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<TopicoResponseDTO> page = topicoRepository.findAllByAtivoTrue(pageable)
                .map(TopicoResponseDTO::new);

        return ResponseEntity.ok(page);
    }

    // =============================
    // DETALHAR TÓPICO
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findByIdAndAtivoTrue(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(404).body(new ErroDTO("Tópico não encontrado: " + id));
        }
        return ResponseEntity.ok(new TopicoResponseDTO(optionalTopico.get()));
    }

    // =============================
    // ATUALIZAR TÓPICO
    // =============================
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid DadosAtualizacaoTopico dados) {

        Optional<Topico> optionalTopico = topicoRepository.findByIdAndAtivoTrue(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(404).body(new ErroDTO("Tópico não encontrado: " + id));
        }

        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.badRequest().body(new ErroDTO("Tópico duplicado"));
        }

        Topico topico = optionalTopico.get();

        // Salva histórico
        edicaoRepository.save(new TopicoEdicao(topico));

        AutorCursoRef ref = getAutorCurso(dados.autorId(), dados.cursoId());
        if (ref == null) {
            return ResponseEntity.badRequest().body(new ErroDTO("Autor ou curso inválido."));
        }

        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setAutor(ref.autor());
        topico.setCurso(ref.curso());

        topicoRepository.save(topico);
        return ResponseEntity.ok(new TopicoResponseDTO(topico));
    }

    // =============================
    // EXCLUIR TÓPICO (Soft Delete)
    // =============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findByIdAndAtivoTrue(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(404).body(new ErroDTO("Tópico não encontrado: " + id));
        }

        Topico topico = optionalTopico.get();
        topico.excluir();
        topicoRepository.save(topico);

        List<TopicoEdicao> edicoes = edicaoRepository.findByTopicoIdAndSoftDeleteFalse(id);
        for (TopicoEdicao edicao : edicoes) {
            edicao.marcarComoInativo();
            edicaoRepository.save(edicao);
        }

        return ResponseEntity.ok(Map.of(
                "mensagem", "Seu tópico foi apagado com sucesso.",
                "observacao", "Devido à LGPD, seu tópico só poderá ser recuperado em até 30 dias corridos da data de exclusão."
        ));
    }

    // =============================
    // MÉTODO AUXILIAR
    // =============================
    private AutorCursoRef getAutorCurso(Long autorId, Long cursoId) {
        try {
            Usuario autor = usuarioRepository.getReferenceById(autorId);
            Curso curso = cursoRepository.getReferenceById(cursoId);
            return new AutorCursoRef(autor, curso);
        } catch (Exception e) {
            return null;
        }
    }

    // =============================
    // RECORD INTERNO
    // =============================
    private record AutorCursoRef(Usuario autor, Curso curso) {}
}