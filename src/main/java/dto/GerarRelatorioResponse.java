package br.com.argus.ia.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o relatório técnico gerado e salvo.")
public class GerarRelatorioResponse {

    @Schema(description = "Identificador do relatório salvo no banco de dados", example = "1")
    private Long id;

    @Schema(
            description = "Relatório técnico formal gerado pela aplicação",
            example = "RELATÓRIO TÉCNICO DE OCORRÊNCIA - ARGUS..."
    )
    private String relatorio;

    public GerarRelatorioResponse() {
    }

    public GerarRelatorioResponse(Long id, String relatorio) {
        this.id = id;
        this.relatorio = relatorio;
    }

    public Long getId() {
        return id;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }
}