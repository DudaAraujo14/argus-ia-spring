package br.com.argus.ia.controller;

import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.dto.GerarRelatorioResponse;
import br.com.argus.ia.service.IaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ia")
public class IaController {

    private final IaService iaService;

    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    @PostMapping("/gerar-relatorio")
    public GerarRelatorioResponse gerarRelatorio(@Valid @RequestBody GerarRelatorioRequest request) {
        String relatorio = iaService.gerarRelatorio(request);
        return new GerarRelatorioResponse(relatorio);
    }
}