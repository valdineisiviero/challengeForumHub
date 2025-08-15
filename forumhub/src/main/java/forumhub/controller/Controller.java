
package forumhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// DTOs (Data Transfer Objects) - Usados para transferir dados entre o cliente e o servidor.
// Usar 'record' é a forma moderna e concisa em Java.

// Dados recebidos para criar um novo tópico
record DadosCadastroTopico(String titulo, String mensagem, String autor, String curso) {}

// Dados retornados na listagem de tópicos
record DadosListagemTopico(Long id, String titulo, String autor, String curso, String status) {}

// Dados retornados no detalhamento de um tópico específico
record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String status, String autor, String curso) {}

// Dados recebidos para atualizar um tópico
record DadosAtualizacaoTopico(String titulo, String mensagem, String status) {}


// Entidade principal que representa um Tópico (nosso "banco de dados" em memória)
class Topico {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private String status = "NAO_RESPONDIDO";
    private String autor;
    private String curso;

    // Construtor usado para criar um novo tópico a partir dos dados de cadastro
    public Topico(DadosCadastroTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.autor = dados.autor();
        this.curso = dados.curso();
    }

    // Getters para acessar os dados
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String getStatus() { return status; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
    public void setId(Long id) { this.id = id; }

    // Método para atualizar as informações do tópico
    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (dados.status() != null) {
            this.status = dados.status();
        }
    }
}


@RestController
@RequestMapping("/topico")
public class Controller { // <--- CLASSE RENOMEADA

    // --- SIMULAÇÃO DO BANCO DE DADOS EM MEMÓRIA ---
    // Uma lista para guardar os tópicos e um contador para gerar IDs únicos.
    private final List<Topico> topicos = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);
    // ---------------------------------------------

    // 1. CADASTRAR NOVO TÓPICO
    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        Topico novoTopico = new Topico(dados);
        novoTopico.setId(proximoId.getAndIncrement());
        topicos.add(novoTopico);

        System.out.println("Tópico cadastrado: " + novoTopico.getTitulo());

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();

        // Retorna status 201 Created com o location do novo recurso e os dados do tópico criado
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(
                novoTopico.getId(), novoTopico.getTitulo(), novoTopico.getMensagem(), novoTopico.getDataCriacao(),
                novoTopico.getStatus(), novoTopico.getAutor(), novoTopico.getCurso()
        ));
    }

    // 2. LISTAGEM DE TÓPICOS
    @GetMapping
    public ResponseEntity<List<DadosListagemTopico>> listar() {
        // Converte a lista de 'Topico' para uma lista de 'DadosListagemTopico'
        var listaDeTopicos = topicos.stream()
                .map(t -> new DadosListagemTopico(t.getId(), t.getTitulo(), t.getAutor(), t.getCurso(), t.getStatus()))
                .toList();

        return ResponseEntity.ok(listaDeTopicos);
    }

    // 3. DETALHAMENTO DE TÓPICO (POR ID)
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        // Procura o tópico na lista pelo ID
        var topico = topicos.stream().filter(t -> t.getId().equals(id)).findFirst();

        // Se não encontrar, retorna 404 Not Found. Se encontrar, retorna 200 OK com os detalhes.
        return topico.map(t -> ResponseEntity.ok(new DadosDetalhamentoTopico(t.getId(), t.getTitulo(), t.getMensagem(), t.getDataCriacao(), t.getStatus(), t.getAutor(), t.getCurso())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. ATUALIZAÇÃO DE TÓPICO (POR ID)
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados) {
        var topicoOptional = topicos.stream().filter(t -> t.getId().equals(id)).findFirst();

        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.atualizarInformacoes(dados);
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor(), topico.getCurso()));
        }

        return ResponseEntity.notFound().build();
    }

    // 5. EXCLUSÃO DE TÓPICO (POR ID)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean removeu = topicos.removeIf(t -> t.getId().equals(id));

        if (removeu) {
            // Retorna 204 No Content, o padrão para exclusão bem-sucedida
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}