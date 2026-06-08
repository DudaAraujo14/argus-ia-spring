# Roteiro do Vídeo - Argus IA Spring

## Objetivo do vídeo

Demonstrar o funcionamento do módulo de Inteligência Artificial do projeto Argus IA Spring, desenvolvido para a Global Solution 2026/1 da FIAP.

Tempo máximo recomendado: 3 minutos.

---

## 1. Abertura - 20 segundos

Olá, meu nome é Maria Eduarda e este é o módulo de Inteligência Artificial do projeto Argus IA Spring.

O Argus é uma solução voltada ao apoio documental e consultivo em ocorrências ambientais e incêndios florestais, conectando dados, automação, banco de dados, Inteligência Artificial e deploy em nuvem.

Este repositório representa a entrega da disciplina de Disruptive Architectures: IoT, IoB & Generative IA, com foco em IA Generativa, RAG, API REST, persistência em banco de dados e publicação em ambiente cloud.

---

## 2. Explicação rápida da proposta - 30 segundos

A IA do Argus não substitui o brigadista, não ensina técnicas de combate ao fogo e não toma decisões operacionais em campo.

Ela atua como apoio documental e consultivo, com funcionalidades principais:

1. Gerar relatórios técnicos de ocorrência a partir de dados estruturados.
2. Consultar procedimentos usando uma base interna de conhecimento com RAG.
3. Salvar relatórios no banco Oracle.
4. Reemitir relatórios já cadastrados.
5. Exportar relatórios em PDF.
6. Disponibilizar a API publicada na Azure com documentação pelo Swagger.

A ideia é reduzir o tempo de documentação, padronizar registros técnicos e apoiar consultas protocolares de forma segura.

---

## 3. Mostrar arquitetura no README - 30 segundos

Aqui no README temos a arquitetura da solução.

O usuário acessa a API pelo Swagger ou por uma futura aplicação front-end/mobile.

As requisições chegam ao `IaController`, passam pelo `IaService` e seguem para diferentes fluxos:

* geração de relatório técnico estruturado;
* persistência dos relatórios no banco Oracle;
* consulta ao `RagService`, que recupera contexto da base interna de procedimentos;
* envio do contexto para o `GroqService`, que integra a aplicação com a Groq API em nuvem;
* reemissão de relatórios salvos;
* exportação de relatórios em PDF por meio do `PdfService`.

A resposta é devolvida em JSON para o usuário, e no caso do PDF, a API retorna o arquivo para download ou visualização.

---

## 4. Demonstrar Swagger - 60 segundos

Agora vou demonstrar os endpoints no Swagger.

Primeiro, o endpoint de health check:

```http
GET /api/v1/ia/health
```

Ele confirma que a API está rodando corretamente.

Agora também temos uma página visual de status:

```http
GET /status
```

Ela mostra que o backend está online, usando Spring Boot, Groq API, Oracle, RAG, reemissão e exportação em PDF.

Agora vou testar o endpoint de geração de relatório:

```http
POST /api/v1/ia/gerar-relatorio
```

Eu envio dados como localização, tipo de vegetação, tamanho estimado, ações tomadas, recursos utilizados, número de brigadistas e nível de risco.

A API processa esses dados, gera um relatório técnico estruturado, salva o registro no Oracle e retorna um identificador junto com o conteúdo do relatório.

Em seguida, posso usar esse identificador para reemitir o relatório salvo:

```http
GET /api/v1/ia/relatorios/{id}
```

Esse endpoint recupera os dados persistidos no banco Oracle.

Também posso exportar o relatório em PDF:

```http
GET /api/v1/ia/relatorios/{id}/pdf
```

Esse recurso gera um documento formal para arquivamento, apresentação ou compartilhamento.

Agora vou testar a consulta de procedimento:

```http
POST /api/v1/ia/consultar
```

Com a pergunta:

```json
{
  "pergunta": "Qual procedimento em caso de ocorrência com vítima?"
}
```

A API recupera contexto na base interna de procedimentos, envia esse contexto para a Groq API e retorna uma resposta orientativa com a fonte:

```txt
Base interna de procedimentos Argus + Groq API
```

---

## 5. Mostrar RAG e segurança da IA - 30 segundos

O RAG do projeto foi ampliado para cobrir diferentes contextos, como ocorrência com vítima, comunicação à Defesa Civil, elaboração de relatório, isolamento de área, classificação de risco, vegetação, recursos utilizados, exportação em PDF, reemissão de relatório, Oracle, Swagger, Azure e ausência de informação suficiente.

Isso permite que a IA responda com base em uma base interna controlada.

Se a pergunta estiver fora da base, a IA não deve inventar uma resposta. Ela informa que não há dados suficientes e orienta a consulta a manuais oficiais, protocolos institucionais ou órgãos responsáveis.

---

## 6. Mostrar tratamento de erros - 20 segundos

A API também possui validação e tratamento de erros.

Se eu enviar uma pergunta vazia ou dados inválidos, a API retorna uma resposta organizada, indicando o problema.

Além disso, caso a IA externa esteja indisponível, o sistema possui fallback. Ele utiliza o contexto recuperado internamente e informa que a consulta ao serviço de IA não pôde ser concluída naquele momento.

Isso melhora a confiabilidade da aplicação e facilita a integração com outros sistemas.

---

## 7. Mostrar deploy na Azure - 20 segundos

O projeto também está publicado em nuvem usando Azure Web App.

O deploy é realizado por pipeline no Azure DevOps, que executa o build Maven, publica o artefato e atualiza a aplicação.

As informações sensíveis, como senha do banco Oracle e chave da Groq API, são configuradas por variáveis de ambiente, e não ficam expostas no código nem no GitHub.

---

## 8. Fechamento - 20 segundos

Com isso, o módulo Argus IA Spring entrega um projeto funcional com Spring Boot, Swagger, validação, tratamento global de erros, geração de relatórios técnicos, persistência em Oracle, reemissão de relatórios, exportação em PDF, consulta RAG com base interna, integração com IA em nuvem via Groq API, deploy na Azure e pipeline no Azure DevOps.

A solução respeita os limites da IA, atuando apenas como apoio documental e consultivo, sem substituir treinamento profissional, protocolos oficiais ou decisão operacional em campo.

Obrigado.
