package br.com.argus.ia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Health Check",
        description = "Endpoint para verificar se o serviço de IA está em execução."
)
public class HealthController {

    @GetMapping("/api/v1/ia/health")
    @Operation(
            summary = "Verificar status da API",
            description = "Retorna uma mensagem simples indicando que o Assistente IA Argus está rodando."
    )
    public String health() {
        return "Assistente IA Argus rodando com sucesso!";
    }
}