package br.com.argus.ia.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o relatório técnico gerado.")
public class GerarRelatorioResponse {

    @Schema(
            description = "Relatório técnico formal gerado pela aplicação",
            example = "RELATÓRIO TÉCNICO DE OCORRÊNCIA - ARGUS..."
    )
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