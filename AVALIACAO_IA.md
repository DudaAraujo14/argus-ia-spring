# Avaliação Manual da IA - Argus IA Spring

## Objetivo

Este documento registra testes manuais realizados no módulo de Inteligência Artificial do Argus, com foco em geração de relatórios técnicos e consulta a procedimentos operacionais com RAG.

A avaliação tem como objetivo verificar se a IA:

- gera relatórios técnicos coerentes;
- utiliza os dados estruturados recebidos;
- evita inventar informações não fornecidas;
- responde consultas com base no contexto recuperado;
- respeita os limites definidos para uso seguro da IA.

---

## Ambiente de teste

- Backend: Spring Boot
- IA: Spring AI
- Modelo local: llama3.2:1b
- Execução do modelo: Ollama
- Interface de teste: Swagger/OpenAPI
- URL local: http://localhost:8080/swagger-ui/index.html

---

## Testes do Gerador de Relatório

| Nº | Cenário | Entrada | Resultado esperado | Status |
|---|---|---|---|---|
| 1 | Incêndio em Cerrado | Chapada dos Veadeiros, cerrado seco, 12 hectares, 8 brigadistas | Relatório formal com localização, vegetação, recursos, ações e risco | Aprovado |
| 2 | Ocorrência em área amazônica | Santarém, floresta densa, 5 hectares, 6 brigadistas | Relatório técnico sem inventar órgão, data ou fonte | Aprovado |
| 3 | Ocorrência no Pantanal | Corumbá, vegetação de campo alagável, 20 hectares, 12 brigadistas | Relatório curto, formal e sem referências inventadas | Aprovado |
| 4 | Campo obrigatório vazio | Localização vazia | Retorno 400 com mensagem de validação | Aprovado |
| 5 | Risco crítico | Ocorrência com nível de risco crítico | Relatório deve mencionar risco crítico sem criar classificação extra | Aprovado |

---

## Testes da Consulta RAG

| Nº | Pergunta | Contexto esperado | Resultado esperado | Status |
|---|---|---|---|---|
| 1 | Qual procedimento em caso de ocorrência com vítima? | Procedimento sobre vítima | Resposta orientando segurança da área, acionamento médico e registro | Aprovado |
| 2 | Como proceder em terra indígena? | Procedimento sobre terra indígena | Resposta sobre comunicação, autorização e órgãos competentes | Aprovado |
| 3 | O que fazer com EPI após o combate? | Procedimento sobre EPI | Resposta sobre verificação, higienização e substituição se necessário | Aprovado |
| 4 | Quando considerar evacuação? | Procedimento sobre evacuação | Resposta sobre risco à vida, fumaça, avanço do fogo e autoridades locais | Aprovado |
| 5 | Pergunta fora da base | Sem contexto suficiente | Resposta informando ausência de dados suficientes | Aprovado |

---

## Conclusão

Os testes indicam que o módulo de IA do Argus está funcional para os casos de uso propostos.

A solução demonstrou capacidade de gerar relatórios técnicos a partir de dados estruturados e responder perguntas com base em uma base interna de conhecimento. A aplicação também respeita os limites definidos, evitando posicionar a IA como substituta do brigadista ou como responsável por decisões operacionais em campo.

A arquitetura com Spring AI e Ollama permite uso de IA generativa local, sem exposição de chave externa e sem dependência de APIs pagas.