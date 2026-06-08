package br.com.argus.ia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GroqService {

    private final RestClient restClient;
    private final String apiKey;
    private final String apiUrl;
    private final String model;

    public GroqService(
            @Value("${groq.api.key}") String apiKey,
            @Value("${groq.api.url}") String apiUrl,
            @Value("${groq.api.model}") String model
    ) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.model = model;
        this.restClient = RestClient.builder().build();
    }

    public String consultar(String contexto, String pergunta) {
        GroqRequest request = new GroqRequest(
                model,
                0.1,
                350,
                List.of(
                        new GroqMessage(
                                "system",
                                """
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
                                """
                        ),
                        new GroqMessage(
                                "user",
                                """
                                Contexto recuperado da base de conhecimento:

                                %s

                                Pergunta do usuário:

                                %s

                                Responda apenas com base no contexto recuperado.
                                Se o contexto indicar ausência de informação suficiente, informe isso claramente.
                                """.formatted(contexto, pergunta)
                        )
                )
        );

        GroqResponse response = restClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GroqResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            throw new IllegalStateException("A Groq API não retornou uma resposta válida.");
        }

        GroqMessage message = response.choices().get(0).message();

        if (message == null || message.content() == null || message.content().isBlank()) {
            throw new IllegalStateException("A Groq API retornou uma mensagem vazia.");
        }

        return message.content();
    }

    private record GroqRequest(
            String model,
            Double temperature,
            Integer max_tokens,
            List<GroqMessage> messages
    ) {
    }

    private record GroqMessage(
            String role,
            String content
    ) {
    }

    private record GroqResponse(
            List<GroqChoice> choices
    ) {
    }

    private record GroqChoice(
            GroqMessage message
    ) {
    }
}