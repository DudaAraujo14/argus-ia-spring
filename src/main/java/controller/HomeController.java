package br.com.argus.ia.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<HomeResponse> home() {
        return ResponseEntity.ok(new HomeResponse(
                "Argus IA Spring",
                "ONLINE",
                "API REST publicada com sucesso na Azure",
                "/status",
                "/swagger-ui/index.html",
                "/api/v1/ia/health",
                "/api/v1/ia/consultar",
                "/api/v1/ia/gerar-relatorio"
        ));
    }

    public record HomeResponse(
            String aplicacao,
            String status,
            String mensagem,
            String statusVisual,
            String swagger,
            String health,
            String consultarProcedimento,
            String gerarRelatorio
    ) {
    }
}