package br.com.argus.ia.service;

import br.com.argus.ia.dto.GerarRelatorioRequest;
import org.springframework.stereotype.Service;

@Service
public class IaService {

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
}