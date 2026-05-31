package br.com.argus.ia.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resposta contendo um relatório técnico salvo e reemitido.")
public class RelatorioReemitidoResponse {

    @Schema(description = "Identificador do relatório", example = "1")
    private Long id;

    @Schema(description = "Localização da ocorrência", example = "Parque Nacional da Chapada dos Veadeiros - GO")
    private String localizacao;

    @Schema(description = "Tipo de vegetação afetada", example = "Cerrado com vegetação seca")
    private String tipoVegetacao;

    @Schema(description = "Tamanho estimado da área atingida", example = "Aproximadamente 12 hectares")
    private String tamanhoEstimado;

    @Schema(description = "Nível de risco informado", example = "Alto")
    private String nivelRisco;

    @Schema(description = "Relatório técnico salvo")
    private String relatorio;

    @Schema(description = "Data e hora de criação do relatório")
    private LocalDateTime criadoEm;

    public RelatorioReemitidoResponse() {
    }

    public RelatorioReemitidoResponse(
            Long id,
            String localizacao,
            String tipoVegetacao,
            String tamanhoEstimado,
            String nivelRisco,
            String relatorio,
            LocalDateTime criadoEm
    ) {
        this.id = id;
        this.localizacao = localizacao;
        this.tipoVegetacao = tipoVegetacao;
        this.tamanhoEstimado = tamanhoEstimado;
        this.nivelRisco = nivelRisco;
        this.relatorio = relatorio;
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getTipoVegetacao() {
        return tipoVegetacao;
    }

    public String getTamanhoEstimado() {
        return tamanhoEstimado;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}