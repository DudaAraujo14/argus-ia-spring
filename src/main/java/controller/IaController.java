package br.com.argus.ia.controller;

import br.com.argus.ia.dto.ConsultaRequest;
import br.com.argus.ia.dto.ConsultaResponse;
import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.dto.GerarRelatorioResponse;
import br.com.argus.ia.service.IaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ia")
@Tag(
        name = "Assistente IA Argus",
        description = "Endpoints de Inteligência Artificial para geração de relatórios técnicos e consulta a procedimentos operacionais."
)
public class IaController {

    private final IaService iaService;

    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    @PostMapping("/gerar-relatorio")
    @Operation(
            summary = "Gerar relatório técnico de ocorrência",
            description = """
                    Recebe dados estruturados de uma ocorrência de incêndio florestal e gera um relatório técnico formal.
                    A funcionalidade apoia a documentação operacional do brigadista, sem substituir sua decisão técnica em campo.
                    """
    )
    public GerarRelatorioResponse gerarRelatorio(@Valid @RequestBody GerarRelatorioRequest request) {
        String relatorio = iaService.gerarRelatorio(request);
        return new GerarRelatorioResponse(relatorio);
    }

    @PostMapping("/consultar")
    @Operation(
            summary = "Consultar procedimentos operacionais com RAG",
            description = """
                    Recebe uma pergunta do usuário e busca contexto em uma base interna de procedimentos.
                    Esta versão utiliza uma base em memória para demonstrar o conceito de RAG.
                    """
    )
    public ConsultaResponse consultar(@Valid @RequestBody ConsultaRequest request) {
        return iaService.consultarProcedimento(request.getPergunta());
    }
}