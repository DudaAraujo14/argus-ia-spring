package br.com.argus.ia.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResponse {

    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private List<String> mensagens;

    public ErroResponse() {
    }

    public ErroResponse(LocalDateTime timestamp, int status, String erro, List<String> mensagens) {
        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.mensagens = mensagens;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }
}