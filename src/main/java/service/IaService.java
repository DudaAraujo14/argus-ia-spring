package br.com.argus.ia.service;

import br.com.argus.ia.dto.ConsultaResponse;
import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.rag.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
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
            String resposta = chatClient
                    .prompt()
                    .options(ChatOptions.builder()
                            .temperature(0.1)
                            .maxTokens(350))
                    .system("""
                            Você é um redator técnico do sistema Argus.

                            Sua função é transformar dados estruturados em um relatório formal para registro interno.

                            Regras obrigatórias:
                            - Não ensine o brigadista a combater incêndio.
                            - Não substitua decisão operacional em campo.
                            - Não invente dados que não foram informados.
                            - Não invente data, hora, órgão responsável, protocolo, fonte, citação ou referência bibliográfica.
                            - Não cite IBAMA, ICMBio, INMET, Ministério do Meio Ambiente ou qualquer órgão se isso não estiver nos dados recebidos.
                            - Não use markdown.
                            - Não use asteriscos.
                            - Não coloque seção de referências.
                            - Use linguagem técnica, objetiva e impessoal.
                            - Escreva em português do Brasil.
                            """)
                    .user(montarPromptRelatorio(request))
                    .call()
                    .content();

            return limparResposta(resposta);

        } catch (Exception exception) {
            return gerarRelatorioFallback(request);
        }
    }

    public ConsultaResponse consultarProcedimento(String pergunta) {
        String contexto = ragService.buscarContexto(pergunta);

        try {
            String resposta = chatClient
                    .prompt()
                    .options(ChatOptions.builder()
                            .temperature(0.1)
                            .maxTokens(300))
                    .system("""
                            Você é um assistente de procedimentos do sistema Argus,
                            voltado para apoio documental a brigadistas e coordenadores.

                            Use apenas o contexto fornecido para responder.

                            Regras obrigatórias:
                            - Não invente protocolos.
                            - Não invente órgãos, normas, fontes ou referências.
                            - Não ensine técnicas de combate ao fogo.
                            - Não substitua treinamento profissional.
                            - Não substitua decisão operacional em campo.
                            - Responda em português do Brasil.
                            - Seja objetivo, técnico e seguro.
                            - Não use markdown.
                            - Não use asteriscos.
                            """)
                    .user("""
                            Contexto recuperado da base de conhecimento:

                            %s

                            Pergunta do usuário:

                            %s

                            Responda apenas com base no contexto recuperado.
                            Se o contexto indicar ausência de informação suficiente, informe isso claramente.
                            """.formatted(contexto, pergunta))
                    .call()
                    .content();

            return new ConsultaResponse(
                    limparResposta(resposta),
                    "Base interna de procedimentos Argus + Spring AI Ollama"
            );

        } catch (Exception exception) {
            String respostaFallback = """
                    Resposta do Assistente IA Argus:

                    A consulta ao modelo local não pôde ser concluída no momento.
                    Com base na base de procedimentos carregada, segue a orientação local:

                    %s

                    Observação: este assistente apoia a consulta documental e não substitui a decisão operacional do brigadista ou coordenador responsável.
                    """.formatted(contexto);

            return new ConsultaResponse(
                    limparResposta(respostaFallback),
                    "Fallback local por indisponibilidade do Ollama"
            );
        }
    }

    private String montarPromptRelatorio(GerarRelatorioRequest request) {
        return """
                Gere um relatório técnico curto para registro interno usando exclusivamente estes dados:

                Localização: %s
                Tipo de vegetação: %s
                Área estimada: %s
                Ações tomadas: %s
                Recursos utilizados: %s
                Brigadistas envolvidos: %d
                Nível de risco: %s

                Regras:
                - Não invente data, hora, órgão, fonte, protocolo ou referência.
                - Não ensine combate ao fogo.
                - Não use markdown.
                - Escreva em português formal e impessoal.

                Use exatamente estas seções:
                1. Identificação da ocorrência
                2. Caracterização da área atingida
                3. Recursos empregados
                4. Ações registradas
                5. Avaliação do nível de risco
                6. Considerações finais
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

                1. Identificação da ocorrência

                Foi registrada ocorrência de incêndio florestal na seguinte localização:
                %s

                2. Caracterização da área atingida

                A área atingida apresenta o seguinte tipo de vegetação:
                %s

                O tamanho estimado da área atingida foi informado como:
                %s

                3. Recursos empregados

                Foram utilizados os seguintes recursos:
                %s

                A equipe mobilizada contou com a atuação de %d brigadista(s).

                4. Ações registradas

                As ações tomadas durante a ocorrência foram:
                %s

                5. Avaliação do nível de risco

                O nível de risco informado para a ocorrência foi:
                %s

                6. Considerações finais

                A ocorrência foi registrada para fins de documentação técnica, acompanhamento operacional
                e apoio aos responsáveis pela gestão da situação.

                Observação técnica:
                Este relatório foi gerado pelo fallback local porque o modelo local não respondeu.
                """.formatted(
                request.getLocalizacao(),
                request.getTipoVegetacao(),
                request.getTamanhoEstimado(),
                request.getRecursosUtilizados(),
                request.getNumeroBrigadistas(),
                request.getAcoesTomadas(),
                request.getNivelRisco()
        );
    }

    private String limparResposta(String resposta) {
        if (resposta == null) {
            return "";
        }

        return resposta
                .replace("**", "")
                .replace("*", "")
                .replace("#", "")
                .replace("`", "")
                .trim();
    }
}