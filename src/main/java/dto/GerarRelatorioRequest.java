package br.com.argus.ia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GerarRelatorioRequest {

    @NotBlank
    private String localizacao;

    @NotBlank
    private String tipoVegetacao;

    @NotBlank
    private String tamanhoEstimado;

    @NotBlank
    private String acoesTomadas;

    @NotBlank
    private String recursosUtilizados;

    @NotNull
    private Integer numeroBrigadistas;

    @NotBlank
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