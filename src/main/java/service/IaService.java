package br.com.argus.ia.service;

import br.com.argus.ia.dto.ConsultaResponse;
import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.rag.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class IaService {

    private final RagService ragService;
    private final ChatClient chatClient;

    public IaService(RagService ragService, ChatClient.Builder chatClientBuilder) {
        this.ragService = ragService;
        this.chatClient = chatClientBuilder.build();
    }

    public String gerarRelatorio(GerarRelatorioRequest request) {
        try {
            return chatClient
                    .prompt()
                    .system("""
                            Você é um redator técnico do sistema Argus, especializado em relatórios
                            de ocorrências ambientais e incêndios florestais.

                            Sua função é transformar dados estruturados em um relatório formal.

                            Regras obrigatórias:
                            - Não ensine o brigadista a combater incêndio.
                            - Não substitua decisão operacional em campo.
                            - Não invente dados que não foram informados.
                            - Use linguagem técnica, objetiva e impessoal.
                            - Escreva em português do Brasil.
                            - Organize a resposta em seções.
                            """)
                    .user(montarPromptRelatorio(request))
                    .call()
                    .content();

        } catch (Exception exception) {
            return gerarRelatorioFallback(request);
        }
    }

    public ConsultaResponse consultarProcedimento(String pergunta) {
        String contexto = ragService.buscarContexto(pergunta);

        try {
            String resposta = chatClient
                    .prompt()
                    .system("""
                            Você é um assistente de procedimentos do sistema Argus,
                            voltado para apoio documental a brigadistas e coordenadores.

                            Use apenas o contexto fornecido para responder.
                            Se o contexto não tiver informação suficiente, diga claramente
                            que não há dados suficientes na base carregada.

                            Regras obrigatórias:
                            - Não invente protocolos.
                            - Não ensine técnicas de combate ao fogo.
                            - Não substitua treinamento profissional.
                            - Não substitua decisão operacional em campo.
                            - Responda em português do Brasil.
                            - Seja objetivo e seguro.
                            """)
                    .user("""
                            Contexto recuperado da base de conhecimento:

                            %s

                            Pergunta do usuário:

                            %s
                            """.formatted(contexto, pergunta))
                    .call()
                    .content();

            return new ConsultaResponse(resposta, "Base interna de procedimentos Argus + Spring AI Ollama");

        } catch (Exception exception) {
            String respostaFallback = """
                    Resposta do Assistente IA Argus:

                    A consulta ao modelo local não pôde ser concluída no momento.
                    Com base na base de procedimentos carregada, segue a orientação local:

                    %s

                    Observação: este assistente apoia a consulta documental e não substitui a decisão operacional do brigadista ou coordenador responsável.
                    """.formatted(contexto);

            return new ConsultaResponse(respostaFallback, "Fallback local por indisponibilidade do Ollama");
        }
    }

    private String montarPromptRelatorio(GerarRelatorioRequest request) {
        return """
                Gere um relatório técnico formal de ocorrência de incêndio florestal com base nos dados abaixo.

                Dados estruturados da ocorrência:

                - Localização: %s
                - Tipo de vegetação: %s
                - Tamanho estimado da área atingida: %s
                - Ações tomadas: %s
                - Recursos utilizados: %s
                - Número de brigadistas envolvidos: %d
                - Nível de risco: %s

                Estruture o relatório com:
                1. Identificação da ocorrência
                2. Caracterização da área
                3. Recursos empregados
                4. Ações registradas
                5. Avaliação do nível de risco
                6. Considerações finais

                Não invente informações além das fornecidas.
                """.formatted(
                request.getLocalizacao(),
                request.getTipoVegetacao(),
                request.getTamanhoEstimado(),
                request.getAcoesTomadas(),
                request.getRecursosUtilizados(),
                request.getNumeroBrigadistas(),
                request.getNivelRisco()
        );
    }

    private String gerarRelatorioFallback(GerarRelatorioRequest request) {
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
                A ocorrência foi registrada no sistema Argus para fins de acompanhamento,
                documentação técnica e apoio à tomada de decisão pelos responsáveis operacionais.

                Observação técnica:
                Este relatório foi gerado pelo fallback local porque o modelo local não respondeu.
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