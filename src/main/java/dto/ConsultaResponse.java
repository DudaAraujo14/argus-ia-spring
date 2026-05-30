package br.com.argus.ia.dto;

public class ConsultaResponse {

    private String resposta;
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