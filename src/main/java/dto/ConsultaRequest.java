package br.com.argus.ia.dto;

import jakarta.validation.constraints.NotBlank;

public class ConsultaRequest {

    @NotBlank
    private String pergunta;

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}