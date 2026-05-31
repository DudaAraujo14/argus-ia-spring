# Roteiro do Vídeo - Argus IA Spring

## Objetivo do vídeo

Demonstrar o funcionamento do módulo de Inteligência Artificial do projeto Argus, desenvolvido para a Global Solution 2026/1 da FIAP.

Tempo máximo recomendado: 3 minutos.

---

## 1. Abertura - 20 segundos

Olá, meu nome é Maria Eduarda e este é o módulo de Inteligência Artificial do projeto Argus.

O Argus é uma solução voltada ao apoio operacional de brigadistas no combate a incêndios florestais, conectando dados, automação e Inteligência Artificial para melhorar a documentação de ocorrências e a consulta a procedimentos.

Este repositório representa a entrega da disciplina de Disruptive Architectures: IoT, IoB & Generative IA, com foco em IA Generativa e RAG.

---

## 2. Explicação rápida da proposta - 30 segundos

A IA do Argus não substitui o brigadista, não ensina técnicas de combate ao fogo e não toma decisões operacionais em campo.

Ela atua como apoio documental e burocrático, com duas funcionalidades principais:

1. Gerar relatórios técnicos de ocorrência a partir de dados estruturados.
2. Consultar procedimentos usando uma base interna de conhecimento, em uma abordagem RAG.

A ideia é reduzir o tempo de documentação, padronizar relatórios e apoiar consultas protocolares de forma segura.

---

## 3. Mostrar arquitetura no README - 30 segundos

Aqui no README temos a arquitetura da solução.

O usuário acessa a API pelo Swagger ou futuramente pelo aplicativo mobile.

As requisições chegam ao `IaController`, passam pelo `IaService` e seguem para duas frentes:

- geração de relatório técnico com apoio de IA generativa local via Spring AI e Ollama;
- consulta ao `RagService`, que recupera contexto da base interna de procedimentos.

A resposta é devolvida em JSON para o usuário.

---

## 4. Demonstrar Swagger - 60 segundos

Agora vou demonstrar os endpoints no Swagger.

Primeiro, o endpoint de health check:

GET /api/v1/ia/health

Ele confirma que a API está rodando.

Agora vou testar o endpoint de geração de relatório:

POST /api/v1/ia/gerar-relatorio

Eu envio dados como localização, tipo de vegetação, tamanho estimado, ações tomadas, recursos utilizados, número de brigadistas e nível de risco.

A API processa esses dados e retorna um relatório técnico estruturado, com linguagem formal e objetiva.

Agora vou testar a consulta de procedimento:

POST /api/v1/ia/consultar

Com a pergunta: "Qual procedimento em caso de ocorrência com vítima?"

A API recupera contexto na base interna de procedimentos e utiliza o modelo local, via Spring AI e Ollama, para gerar uma resposta orientativa com indicação da fonte utilizada.

---

## 5. Mostrar tratamento de erros - 20 segundos

A API também possui validação.

Se eu enviar uma pergunta vazia, ela retorna erro 400 com uma mensagem organizada, indicando que o campo é obrigatório.

Isso melhora a qualidade da API, facilita a integração com outros sistemas e evita respostas inconsistentes.

---

## 6. Fechamento - 20 segundos

Com isso, o módulo Argus IA entrega um projeto funcional com Spring Boot, Swagger, validação, tratamento global de erros, geração de relatório técnico, consulta RAG com base interna, documentação dos prompts e integração com IA generativa local por meio de Spring AI e Ollama.

A solução evita o uso de chaves externas pagas, mantém a chamada ao modelo no backend e respeita os limites da IA, atuando apenas como apoio documental e consultivo.

Obrigado.