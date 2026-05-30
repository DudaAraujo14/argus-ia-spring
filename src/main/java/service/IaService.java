package br.com.argus.ia.service;

import br.com.argus.ia.dto.ConsultaResponse;
import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.rag.RagService;
import org.springframework.stereotype.Service;

@Service
public class IaService {

    private final RagService ragService;

    public IaService(RagService ragService) {
        this.ragService = ragService;
    }

    public String gerarRelatorio(GerarRelatorioRequest request) {
        return """
                RELATÓRIO TÉCNICO DE OCORRÊNCIA - ARGUS

                1. Localização da ocorrência:
                %s

                2. Tipo de vegetação afetada:
                %s

                3. Tamanho estimado da área atingida:
                %s

                4. Nível de risco:
                %s

                5. Recursos utilizados:
                %s

                6. Equipe mobilizada:
                A ocorrência contou com a atuação de %d brigadista(s).

                7. Ações tomadas:
                %s

                8. Considerações finais:
                A ocorrência foi registrada no sistema Argus para fins de acompanhamento, documentação técnica e apoio à tomada de decisão pelos responsáveis operacionais.
                """.formatted(
                request.getLocalizacao(),
                request.getTipoVegetacao(),
                request.getTamanhoEstimado(),
                request.getNivelRisco(),
                request.getRecursosUtilizados(),
                request.getNumeroBrigadistas(),
                request.getAcoesTomadas()
        );
    }

    public ConsultaResponse consultarProcedimento(String pergunta) {
        String contexto = ragService.buscarContexto(pergunta);

        String resposta = """
                Resposta do Assistente IA Argus:

                Com base na base de procedimentos carregada, segue a orientação:

                %s

                Observação: este assistente apoia a consulta documental e não substitui a decisão operacional do brigadista ou coordenador responsável.
                """.formatted(contexto);

        return new ConsultaResponse(resposta, "Base interna de procedimentos Argus MVP");
    }
}