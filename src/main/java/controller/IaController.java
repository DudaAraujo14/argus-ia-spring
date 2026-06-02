package br.com.argus.ia.controller;

import br.com.argus.ia.dto.ConsultaRequest;
import br.com.argus.ia.dto.ConsultaResponse;
import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.dto.GerarRelatorioResponse;
import br.com.argus.ia.dto.RelatorioReemitidoResponse;
import br.com.argus.ia.service.IaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ia")
@Tag(
        name = "Assistente IA Argus",
        description = "Endpoints de Inteligência Artificial para geração de relatórios técnicos, reemissão de relatórios, exportação em PDF e consulta a procedimentos operacionais."
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
                    Recebe dados estruturados de uma ocorrência de incêndio florestal, gera um relatório técnico formal
                    e salva o resultado no banco de dados Oracle para futura reemissão.
                    """
    )
    public GerarRelatorioResponse gerarRelatorio(@Valid @RequestBody GerarRelatorioRequest request) {
        return iaService.gerarRelatorio(request);
    }

    @GetMapping("/relatorios/{id}")
    @Operation(
            summary = "Reemitir relatório técnico salvo",
            description = "Busca um relatório técnico já gerado e salvo no banco de dados Oracle a partir do seu ID."
    )
    public RelatorioReemitidoResponse reemitirRelatorio(@PathVariable Long id) {
        return iaService.reemitirRelatorio(id);
    }

    @GetMapping(
            value = "/relatorios/{id}/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    @Operation(
            summary = "Exportar relatório técnico em PDF",
            description = "Busca um relatório salvo no Oracle e retorna o arquivo PDF para download."
    )
    public ResponseEntity<byte[]> exportarRelatorioPdf(@PathVariable Long id) {
        byte[] pdf = iaService.exportarRelatorioPdf(id);

        String nomeArquivo = "relatorio-argus-" + id + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/consultar")
    @Operation(
            summary = "Consultar procedimentos operacionais com RAG",
            description = """
                    Recebe uma pergunta do usuário e busca contexto em uma base interna de procedimentos.
                    A resposta é gerada com apoio do modelo local via Spring AI e Ollama.
                    """
    )
    public ConsultaResponse consultar(@Valid @RequestBody ConsultaRequest request) {
        return iaService.consultarProcedimento(request.getPergunta());
    }
}