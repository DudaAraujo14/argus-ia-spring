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

                            Sua função é transformar dados estruturados em um relatório formal para registro interno.

                            Regras obrigatórias:
                            - Não ensine o brigadista a combater incêndio.
                            - Não substitua decisão operacional em campo.
                            - Não invente dados que não foram informados.
                            - Não invente data, hora, órgão responsável, protocolo, fonte, citação ou referência bibliográfica.
                            - Não cite IBAMA, ICMBio, INMET, Ministério do Meio Ambiente ou qualquer órgão se isso não estiver nos dados recebidos.
                            - Não crie classificação adicional além do nível de risco informado.
                            - Não use markdown.
                            - Não use asteriscos.
                            - Não coloque seção de referências.
                            - Não coloque fontes no final.
                            - Use linguagem técnica, objetiva e impessoal.
                            - Escreva em português do Brasil.
                            - Organize a resposta exatamente nas seções solicitadas pelo usuário.
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
                Gere um relatório técnico formal de ocorrência de incêndio florestal com base exclusivamente nos dados abaixo.

                Dados estruturados da ocorrência:

                - Localização: %s
                - Tipo de vegetação: %s
                - Tamanho estimado da área atingida: %s
                - Ações tomadas: %s
                - Recursos utilizados: %s
                - Número de brigadistas envolvidos: %d
                - Nível de risco: %s

                Regras obrigatórias:
                - Não invente data.
                - Não invente hora.
                - Não invente órgão responsável.
                - Não invente protocolo.
                - Não invente fonte.
                - Não invente citação.
                - Não invente referência bibliográfica.
                - Não cite IBAMA, ICMBio, INMET, Ministério do Meio Ambiente ou qualquer órgão se isso não estiver nos dados recebidos.
                - Não crie informações não fornecidas pelo usuário.
                - Não ensine técnicas de combate ao fogo.
                - Não dê ordens operacionais.
                - Não use markdown.
                - Não use asteriscos.
                - Não coloque seção de referências.
                - Não coloque fontes no final.
                - Não inclua observações que não estejam baseadas nos dados recebidos.
                - Use linguagem técnica, objetiva e impessoal.
                - Escreva em português do Brasil.

                Estruture o relatório exatamente com estas seções:

                1. Identificação da ocorrência
                2. Caracterização da área atingida
                3. Recursos empregados
                4. Ações registradas
                5. Avaliação do nível de risco
                6. Considerações finais

                O relatório deve ser curto, formal e adequado para registro interno.
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
}