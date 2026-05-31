package br.com.argus.ia.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RELATORIO_OCORRENCIA")
public class RelatorioOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RELATORIO")
    private Long id;

    @Column(name = "LOCALIZACAO", nullable = false, length = 255)
    private String localizacao;

    @Column(name = "TIPO_VEGETACAO", nullable = false, length = 255)
    private String tipoVegetacao;

    @Column(name = "TAMANHO_ESTIMADO", nullable = false, length = 100)
    private String tamanhoEstimado;

    @Column(name = "ACOES_TOMADAS", nullable = false, length = 1000)
    private String acoesTomadas;

    @Column(name = "RECURSOS_UTILIZADOS", nullable = false, length = 1000)
    private String recursosUtilizados;

    @Column(name = "NUMERO_BRIGADISTAS", nullable = false)
    private Integer numeroBrigadistas;

    @Column(name = "NIVEL_RISCO", nullable = false, length = 50)
    private String nivelRisco;

    @Lob
    @Column(name = "RELATORIO", nullable = false)
    private String relatorio;

    @Column(name = "CRIADO_EM", nullable = false)
    private LocalDateTime criadoEm;

    public RelatorioOcorrencia() {
    }

    public RelatorioOcorrencia(
            String localizacao,
            String tipoVegetacao,
            String tamanhoEstimado,
            String acoesTomadas,
            String recursosUtilizados,
            Integer numeroBrigadistas,
            String nivelRisco,
            String relatorio,
            LocalDateTime criadoEm
    ) {
        this.localizacao = localizacao;
        this.tipoVegetacao = tipoVegetacao;
        this.tamanhoEstimado = tamanhoEstimado;
        this.acoesTomadas = acoesTomadas;
        this.recursosUtilizados = recursosUtilizados;
        this.numeroBrigadistas = numeroBrigadistas;
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

    public String getAcoesTomadas() {
        return acoesTomadas;
    }

    public String getRecursosUtilizados() {
        return recursosUtilizados;
    }

    public Integer getNumeroBrigadistas() {
        return numeroBrigadistas;
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