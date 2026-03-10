package br.com.alura.forumhub.dto;

/**
 * DTO para retorno de mensagens de erro na API.
 * Pode ser usado em ResponseEntity.badRequest() ou outros retornos de erro.
 */
public class ErroDTO {

    // Mensagem de erro a ser exibida
    private String mensagem;

    // Construtor vazio (necessário para frameworks que usam reflexão)
    public ErroDTO() {}

    // Construtor com mensagem
    public ErroDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    // Getter
    public String getMensagem() {
        return mensagem;
    }

    // Setter
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}