package br.com.argus.ia.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da consulta de procedimento com fonte utilizada.")
public class ConsultaResponse {

    @Schema(
            description = "Resposta orientativa gerada com base no contexto encontrado",
            example = "Com base na base de procedimentos carregada, a equipe deve priorizar a segurança da área e acionar o serviço médico de emergência."
    )
    private String resposta;

    @Schema(
            description = "Fonte utilizada para compor a resposta",
            example = "Base interna de procedimentos Argus"
    )
    private String fonte;

    public ConsultaResponse() {
    }

    public ConsultaResponse(String resposta, String fonte) {
        this.resposta = resposta;
        this.fonte = fonte;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }
}