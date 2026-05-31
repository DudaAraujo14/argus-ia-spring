package br.com.argus.ia.service;

import br.com.argus.ia.dto.ConsultaResponse;
import br.com.argus.ia.dto.GerarRelatorioRequest;
import br.com.argus.ia.dto.GerarRelatorioResponse;
import br.com.argus.ia.dto.RelatorioReemitidoResponse;
import br.com.argus.ia.model.RelatorioOcorrencia;
import br.com.argus.ia.rag.RagService;
import br.com.argus.ia.repository.RelatorioOcorrenciaRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IaService {

    private final RagService ragService;
    private final ChatClient chatClient;
    private final RelatorioOcorrenciaRepository relatorioRepository;

    public IaService(
            RagService ragService,
            ChatClient.Builder chatClientBuilder,
            RelatorioOcorrenciaRepository relatorioRepository
    ) {
        this.ragService = ragService;
        this.chatClient = chatClientBuilder.build();
        this.relatorioRepository = relatorioRepository;
    }

    public GerarRelatorioResponse gerarRelatorio(GerarRelatorioRequest request) {
        String textoRelatorio = gerarRelatorioEstruturado(request);

        RelatorioOcorrencia relatorio = new RelatorioOcorrencia(
                request.getLocalizacao(),
                request.getTipoVegetacao(),
                request.getTamanhoEstimado(),
                request.getAcoesTomadas(),
                request.getRecursosUtilizados(),
                request.getNumeroBrigadistas(),
                request.getNivelRisco(),
                textoRelatorio,
                LocalDateTime.now()
        );

        RelatorioOcorrencia salvo = relatorioRepository.save(relatorio);

        return new GerarRelatorioResponse(salvo.getId(), salvo.getRelatorio());
    }

    public RelatorioReemitidoResponse reemitirRelatorio(Long id) {
        RelatorioOcorrencia relatorio = relatorioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Relatório não encontrado com o ID: " + id));

        return new RelatorioReemitidoResponse(
                relatorio.getId(),
                relatorio.getLocalizacao(),
                relatorio.getTipoVegetacao(),
                relatorio.getTamanhoEstimado(),
                relatorio.getNivelRisco(),
                relatorio.getRelatorio(),
                relatorio.getCriadoEm()
        );
    }

    public ConsultaResponse consultarProcedimento(String pergunta) {
        String contexto = ragService.buscarContexto(pergunta);

        try {
            String resposta = chatClient
                    .prompt()
                    .options(ChatOptions.builder()
                            .temperature(0.1)
                            .maxTokens(350))
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

    private String gerarRelatorioEstruturado(GerarRelatorioRequest request) {
        return """
                RELATÓRIO TÉCNICO DE OCORRÊNCIA - ARGUS

                1. Identificação da ocorrência

                Foi registrada uma ocorrência de incêndio florestal na localidade: %s.

                2. Caracterização da área atingida

                A ocorrência envolveu área com %s. O tamanho estimado da área atingida foi de %s.

                3. Recursos empregados

                Foram utilizados os seguintes recursos: %s. A equipe mobilizada contou com %d brigadista(s).

                4. Ações registradas

                As ações informadas foram: %s.

                5. Avaliação do nível de risco

                O nível de risco informado para a ocorrência foi classificado como %s.

                6. Considerações finais

                A ocorrência foi registrada para fins de documentação técnica, acompanhamento operacional
                e apoio aos responsáveis pela gestão da situação.
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