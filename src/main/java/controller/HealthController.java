package br.com.argus.ia.controller;

import br.com.argus.ia.dto.HealthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Tag(name = "Health Check", description = "Endpoint para verificar se o serviço de IA está em execução.")
public class HealthController {

    @GetMapping("/api/v1/ia/health")
    @Operation(summary = "Verificar status da API")
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                "Assistente IA Argus",
                "Serviço em execução com sucesso",
                LocalDateTime.now()
        );
    }
}