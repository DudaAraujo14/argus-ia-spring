package br.com.argus.ia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados estruturados de uma ocorrência para geração de relatório técnico.")
public class GerarRelatorioRequest {

    @NotBlank
    @Schema(description = "Localização da ocorrência", example = "Parque Nacional da Chapada dos Veadeiros - GO")
    private String localizacao;

    @NotBlank
    @Schema(description = "Tipo de vegetação afetada", example = "Cerrado com vegetação seca")
    private String tipoVegetacao;

    @NotBlank
    @Schema(description = "Tamanho estimado da área atingida", example = "Aproximadamente 12 hectares")
    private String tamanhoEstimado;

    @NotBlank
    @Schema(description = "Ações tomadas pela equipe", example = "Isolamento da área, combate direto com abafadores e acionamento de equipe de apoio")
    private String acoesTomadas;

    @NotBlank
    @Schema(description = "Recursos utilizados na ocorrência", example = "Abafadores, bomba costal, caminhão-pipa e rádio comunicador")
    private String recursosUtilizados;

    @NotNull
    @Schema(description = "Número de brigadistas envolvidos", example = "8")
    private Integer numeroBrigadistas;

    @NotBlank
    @Schema(description = "Nível de risco da ocorrência", example = "Alto")
    private String nivelRisco;

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipoVegetacao() {
        return tipoVegetacao;
    }

    public void setTipoVegetacao(String tipoVegetacao) {
        this.tipoVegetacao = tipoVegetacao;
    }

    public String getTamanhoEstimado() {
        return tamanhoEstimado;
    }

    public void setTamanhoEstimado(String tamanhoEstimado) {
        this.tamanhoEstimado = tamanhoEstimado;
    }

    public String getAcoesTomadas() {
        return acoesTomadas;
    }

    public void setAcoesTomadas(String acoesTomadas) {
        this.acoesTomadas = acoesTomadas;
    }

    public String getRecursosUtilizados() {
        return recursosUtilizados;
    }

    public void setRecursosUtilizados(String recursosUtilizados) {
        this.recursosUtilizados = recursosUtilizados;
    }

    public Integer getNumeroBrigadistas() {
        return numeroBrigadistas;
    }

    public void setNumeroBrigadistas(Integer numeroBrigadistas) {
        this.numeroBrigadistas = numeroBrigadistas;
    }

    public String getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(String nivelRisco) {
        this.nivelRisco = nivelRisco;
    }
}