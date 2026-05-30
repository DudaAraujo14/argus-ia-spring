package br.com.argus.ia.dto;

public class GerarRelatorioResponse {

    private String relatorio;

    public GerarRelatorioResponse() {
    }

    public GerarRelatorioResponse(String relatorio) {
        this.relatorio = relatorio;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }
}