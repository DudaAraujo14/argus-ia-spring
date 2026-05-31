# Roteiro do Vídeo - Argus IA Spring

## Objetivo do vídeo

Demonstrar o funcionamento do módulo de Inteligência Artificial do projeto Argus, desenvolvido para a Global Solution 2026/1 da FIAP.

Tempo máximo recomendado: 3 minutos.

---

## 1. Abertura - 20 segundos

Olá, meu nome é Maria Eduarda e este é o módulo de Inteligência Artificial do projeto Argus.

O Argus é uma solução voltada ao apoio operacional de brigadistas no combate a incêndios florestais, conectando dados, automação e IA para melhorar a documentação de ocorrências e a consulta a procedimentos.

Este repositório representa a entrega da disciplina de Disruptive Architectures, com foco em IA Generativa e RAG.

---

## 2. Explicação rápida da proposta - 30 segundos

A IA do Argus não substitui o brigadista e não toma decisões em campo.

Ela atua como apoio documental e burocrático, com duas funcionalidades principais:

1. Gerar relatórios técnicos de ocorrência a partir de dados estruturados.
2. Consultar procedimentos usando uma base de conhecimento interna, em uma abordagem RAG MVP.

A ideia é reduzir tempo de documentação e padronizar respostas consultivas.

---

## 3. Mostrar arquitetura no README - 30 segundos

Aqui no README temos a arquitetura da solução.

O usuário acessa a API pelo Swagger ou futuramente pelo app mobile.

As requisições chegam no IaController, passam pelo IaService e seguem para duas partes:

- geração de relatório técnico;
- consulta ao RagService, que recupera contexto da base interna.

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

A API retorna um relatório técnico estruturado.

Agora vou testar a consulta de procedimento:

POST /api/v1/ia/consultar

Com a pergunta: "Qual procedimento em caso de ocorrência com vítima?"

A API busca contexto na base interna e retorna uma resposta orientativa com a fonte utilizada.

---

## 5. Mostrar tratamento de erros - 20 segundos

A API também possui validação.

Se eu enviar uma pergunta vazia, ela retorna erro 400 com mensagem organizada, indicando que o campo é obrigatório.

Isso melhora a qualidade da API e facilita o uso por outros sistemas.

---

## 6. Fechamento - 20 segundos

Com isso, o módulo Argus IA entrega um MVP funcional com Spring Boot, Swagger, validação, geração de relatório, consulta RAG em memória e documentação dos prompts.

Como evolução futura, o sistema pode integrar documentos oficiais em PDF, embeddings, vector store e chamada real a modelos generativos via Spring AI.

Obrigado.