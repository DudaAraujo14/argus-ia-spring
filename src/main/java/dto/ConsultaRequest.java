package br.com.argus.ia.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Pergunta enviada pelo usuário para consulta de procedimentos.")
public class ConsultaRequest {

    @NotBlank
    @Schema(
            description = "Pergunta do usuário sobre procedimento operacional",
            example = "Qual procedimento em caso de ocorrência com vítima?"
    )
    private String pergunta;

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}